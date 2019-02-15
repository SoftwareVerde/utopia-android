package com.softwareverde.utopia.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.softwareverde.util.Dialog;
import com.softwareverde.util.StringUtil;
import com.softwareverde.utopia.Util;
import com.softwareverde.utopia.ActiveSpell;
import com.softwareverde.utopia.AndroidUtil;
import com.softwareverde.utopia.Building;
import com.softwareverde.utopia.NonCastableSpell;
import com.softwareverde.utopia.Province;
import com.softwareverde.utopia.R;
import com.softwareverde.utopia.Session;
import com.softwareverde.utopia.Spell;
import com.softwareverde.utopia.bundle.SpellResultBundle;
import com.softwareverde.utopia.ui.adapter.SpellListAdapter;
import com.softwareverde.utopia.ui.dialog.CastOffensiveSpellDialog;

import java.util.ArrayList;
import java.util.List;

public class OffensiveSpellListFragment extends Fragment {
    public static final String SPELL_LIST_CALLBACK_IDENTIFIER = "SpellListFragment_SpellListUpdateCallbackIdentifier";
    public static final String THRONE_CALLBACK_IDENTIFIER = "SpellListFragment_ThroneUpdateCallbackIdentifier";

    private Province _targetProvince;
    private Activity _activity;
    private Session _session;
    private View _view;
    private SpellListAdapter _adapter;

    public OffensiveSpellListFragment() { }
    public void setTargetProvince(Province province) {
        _setTargetProvince(province);
    }
    private void _setTargetProvince(Province province) {
        _targetProvince = province;

        if (_targetProvince != null) {
            if (_targetProvince.getKingdomIdentifier() == null) {
                System.out.println("WARNING: Using a province without an identifier within MagicOpsFragment.");
            }

            if (_session != null) {
                _session.downloadAvailableSpells(_targetProvince.getKingdomIdentifier(), null);
            }
        }
    }
    public Province getTargetProvince() {
        return _targetProvince;
    }

    private void _showLoadingScreen() {
        AndroidUtil.showLoadingScreen(_activity);
    }
    private void _hideLoadingScreen() {
        _activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AndroidUtil.hideLoadingScreen();
            }
        });
    }

    private void _drawData() {
        if (_view == null) { return; }

        _adapter.notifyDataSetChanged();

        final Province province = _session.getProvince();
        ((TextView) _view.findViewById(R.id.spells_list_rune_count)).setText(StringUtil.formatNumberString(province.getRunes()));
        ((TextView) _view.findViewById(R.id.spells_list_mana)).setText(StringUtil.formatNumberString(province.getMana()) + "%");
        ((TextView) _view.findViewById(R.id.spells_list_wpa)).setText(Util.formatPercentString((float) Util.coalesce(province.getWizards()) / Util.coalesce(province.getAcres(), 1)));
        final TextView percentGuildsView = ((TextView) _view.findViewById(R.id.spells_list_guild_percent));
        final Building guilds = province.getBuilding(Building.Type.GUILDS);
        if (guilds != null) {
            percentGuildsView.setText(Util.formatPercentString(guilds.getPercent() * 100.0f) + "%");
        }
        else {
            percentGuildsView.setText("??");
        }

        final TextView provinceNameView = ((TextView) _view.findViewById(R.id.spell_list_province_name));
        provinceNameView.setText(_targetProvince.getName());
        provinceNameView.setVisibility(View.VISIBLE);

        ((TextView) _view.findViewById(R.id.spells_list_title_label)).setText("Offensive Spells");

        final View backButton = _view.findViewById(R.id.spells_list_back_button);
        backButton.setVisibility(View.VISIBLE);

        _view.findViewById(R.id.magic_tab_layout).setVisibility(View.VISIBLE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDetach() {
        _session.removeSpellListCallback(SPELL_LIST_CALLBACK_IDENTIFIER);
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.spell_list, container, false);
        _view = rootView;

        _showLoadingScreen();

        _view.findViewById(R.id.magic_tab_province).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        _showTargetProvince();
                    }
                });
            }
        });
        _view.findViewById(R.id.magic_tab_thievery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        _showTargetThievery();
                    }
                });
            }
        });
        _view.findViewById(R.id.magic_tab_survey).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        _showTargetSurvey();
                    }
                });
            }
        });

        final ListView listView = (ListView) _view.findViewById(R.id.spells_list_list_view);
        listView.setAdapter(_adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Spell spell = _adapter.getItem(i);
                if (spell.getIdentifier() == null) { return; } // NonCastableSpell...

                final CastOffensiveSpellDialog castSpellDialog = new CastOffensiveSpellDialog();
                castSpellDialog.setActivity(_activity);
                castSpellDialog.setTitle(spell.getName());
                castSpellDialog.setCurrentRuneCount(_session.getProvince().getRunes());
                castSpellDialog.setRuneCost(spell.getRuneCost());
                castSpellDialog.setCallback(new CastOffensiveSpellDialog.Callback() {
                    @Override
                    public void run() {
                        _showLoadingScreen();

                        _session.castSpell(spell, _targetProvince, new Session.CastSpellCallback() {
                            @Override
                            public void run(final Session.CastSpellResponse response) {
                                final TextView resultArea = (TextView) _view.findViewById(R.id.cast_spell_result);

                                if (response.getWasSuccess()) {
                                    _session.downloadThrone(new Session.Callback() {
                                        @Override
                                        public void run(Session.SessionResponse response) {
                                            _session.downloadActiveSpells(_targetProvince, new Session.Callback() {
                                                @Override
                                                public void run(Session.SessionResponse response) {
                                                    _activity.runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            _drawData();
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    });

                                    final SpellResultBundle spellResultBundle = response.getSpellResultBundle();

                                    _activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            resultArea.setText(spellResultBundle.get(SpellResultBundle.Keys.RESULT_TEXT));
                                            _drawData();
                                        }
                                    });
                                }
                                else {
                                    _activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            resultArea.setText(response.getErrorMessage());
                                            _drawData();
                                        }
                                    });
                                }

                                _hideLoadingScreen();
                            }
                        });
                    }
                });
                castSpellDialog.show(_activity.getFragmentManager(), "CAST_OFFENSIVE_SPELL_DIALOG");
            }
        });

        final View backButton = _view.findViewById(R.id.spells_list_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _back();
            }
        });

        _drawData();
        _hideLoadingScreen();

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _activity = activity;

        _session = Session.getInstance();

        _adapter = new SpellListAdapter(_activity, _targetProvince);

         _session.addSpellListCallback(SPELL_LIST_CALLBACK_IDENTIFIER, new Runnable() {
             public void run() {
                 _activity.runOnUiThread(new Runnable() {
                     public void run() {
                         final List<Spell> availableSpells = _session.getAvailableSpells(Spell.SpellType.OFFENSIVE);
                         final List<String> availableSpellNames = new ArrayList<String>();
                         for (final Spell spell : availableSpells) {
                             availableSpellNames.add(spell.getName());
                         }

                         _adapter.clear();
                         _adapter.addAll(availableSpells);

                         { // Add Active Defensive Spells...
                             final List<ActiveSpell> activeSpells = _targetProvince.getActiveSpells();
                             for (final ActiveSpell activeSpell : activeSpells) {
                                 if (! availableSpellNames.contains(activeSpell.getSpellName())) {
                                     final Spell nonCastableSpell = new NonCastableSpell(activeSpell.getSpellName());
                                     _adapter.add(nonCastableSpell);
                                 }
                             }
                         }

                         _adapter.notifyDataSetChanged();
                     }
                 });
             }
         });
        _session.addThroneCallback(THRONE_CALLBACK_IDENTIFIER, new Runnable() {
            @Override
            public void run() {
                _activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        _drawData();
                    }
                });
            }
        });

        _session.downloadAvailableSpells(new Session.Callback() {
            @Override
            public void run(Session.SessionResponse response) {
                if (! response.getWasSuccess()) {
                    Dialog.setActivity(_activity);
                    Dialog.alert("Download Spells", "Error downloading spell list:\n\n    " + response.getErrorMessage() + "\n", null);
                    return;
                }

                _activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        _drawData();
                    }
                });
            }
        });

        _session.downloadBuildingsCouncil(new Session.Callback() {
            @Override
            public void run(Session.SessionResponse response) {
                _activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        _drawData();
                    }
                });
            }
        });

        _updateProvinceActiveSpells();
    }

    private void _updateProvinceActiveSpells() {
        _session.downloadActiveSpells(_targetProvince, new Session.Callback() {
            @Override
            public void run(Session.SessionResponse response) {
                _activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        _drawData();
                    }
                });
            }
        });
    }

    private void _back() {
        final FragmentTransaction transaction = ((AppCompatActivity) _activity).getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        final KingdomFragment kingdomFragment = new KingdomFragment();
        kingdomFragment.setKingdomIdentifier(_targetProvince.getKingdomIdentifier());
        kingdomFragment.setShouldUseCachedKingdomData(true);
        KingdomFragment fragment = kingdomFragment;

        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    private void _showTargetProvince() {
        final FragmentTransaction transaction = ((AppCompatActivity) _activity).getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);

        final TabbedThroneFragment throneFragment = new TabbedThroneFragment();
        throneFragment.setProvince(_targetProvince);

        transaction.replace(R.id.container, throneFragment);
        transaction.commit();
    }

    private void _showTargetThievery() {
        final FragmentTransaction transaction = ((AppCompatActivity) _activity).getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);

        final ThieveryOpsFragment thieveryOpsFragment = new ThieveryOpsFragment();
        thieveryOpsFragment.setTargetProvince(_targetProvince);

        transaction.replace(R.id.container, thieveryOpsFragment);
        transaction.commit();
    }

    private void _showTargetSurvey() {
        final FragmentTransaction transaction = ((AppCompatActivity) _activity).getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);

        final TabbedBuildingsCouncilFragment tabbedBuildingsCouncilFragment = new TabbedBuildingsCouncilFragment();
        tabbedBuildingsCouncilFragment.setProvince(_targetProvince);

        transaction.replace(R.id.container, tabbedBuildingsCouncilFragment);
        transaction.commit();
    }
}