package com.softwareverde.utopia.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.softwareverde.util.Dialog;
import com.softwareverde.util.StringUtil;
import com.softwareverde.utopia.Util;
import com.softwareverde.utopia.AndroidUtil;
import com.softwareverde.utopia.Building;
import com.softwareverde.utopia.Province;
import com.softwareverde.utopia.R;
import com.softwareverde.utopia.Session;
import com.softwareverde.utopia.Spell;
import com.softwareverde.utopia.bundle.SpellResultBundle;
import com.softwareverde.utopia.ui.adapter.SpellListAdapter;
import com.softwareverde.utopia.ui.dialog.CastDefensiveSpellDialog;

public class SpellListFragment extends Fragment {
    public static final String SPELL_LIST_CALLBACK_IDENTIFIER = "SpellListFragment_SpellListUpdateCallbackIdentifier";
    public static final String ACTIVE_SPELLS_CALLBACK_IDENTIFIER = "SpellListFragment_ActiveSpellsUpdateCallbackIdentifier";
    public static final String THRONE_CALLBACK_IDENTIFIER = "SpellListFragment_ThroneUpdateCallbackIdentifier";

    private Activity _activity;
    private Session _session;
    private View _view;
    private SpellListAdapter _adapter;

    public SpellListFragment() { }

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

        Province province = _session.getProvince();
        ((TextView) _view.findViewById(R.id.spells_list_rune_count)).setText(StringUtil.formatNumberString(province.getRunes()));
        ((TextView) _view.findViewById(R.id.spells_list_mana)).setText(StringUtil.formatNumberString(province.getMana()) + "%");
        ((TextView) _view.findViewById(R.id.spells_list_wpa)).setText(Util.formatPercentString((float) Util.coalesce(province.getWizards()) / Util.coalesce(province.getAcres(), 1)));
        TextView percentGuildsView = ((TextView) _view.findViewById(R.id.spells_list_guild_percent));
        Building guilds = province.getBuilding(Building.Type.GUILDS);
        if (guilds != null) {
            percentGuildsView.setText(Util.formatPercentString(guilds.getPercent() * 100.0f) + "%");
        }
        else {
            percentGuildsView.setText("");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDetach() {
        _session.removeSpellListCallback(SPELL_LIST_CALLBACK_IDENTIFIER);
        _session.removeActiveSpellsCallback(ACTIVE_SPELLS_CALLBACK_IDENTIFIER);
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.spell_list, container, false);
        _view = rootView;

        _showLoadingScreen();

        ListView listView = (ListView) _view.findViewById(R.id.spells_list_list_view);
        listView.setAdapter(_adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Spell spell = _adapter.getItem(i);

                final CastDefensiveSpellDialog castSpellDialog = new CastDefensiveSpellDialog();
                castSpellDialog.setActivity(_activity);
                castSpellDialog.setTitle(spell.getName());
                castSpellDialog.setCurrentRuneCount(_session.getProvince().getRunes());
                castSpellDialog.setRuneCost(spell.getRuneCost());
                castSpellDialog.setCallback(new CastDefensiveSpellDialog.Callback() {
                    @Override
                    public void run() {
                        _showLoadingScreen();

                        _session.castSpell(spell, new Session.CastSpellCallback() {
                            @Override
                            public void run(final Session.CastSpellResponse response) {
                                final TextView resultArea = (TextView) _view.findViewById(R.id.cast_spell_result);

                                if (response.getWasSuccess()) {
                                    _session.downloadActiveSpells(null);
                                    _session.downloadThrone(null);

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
                castSpellDialog.show(_activity.getFragmentManager(), "CAST_DEFENSIVE_SPELL_DIALOG");
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
        _adapter = new SpellListAdapter(_activity, _session.getProvince());

        _adapter.addAll(_session.getAvailableSpells(Spell.SpellType.DEFENSIVE));
        _adapter.notifyDataSetChanged();

        _session.addSpellListCallback(SPELL_LIST_CALLBACK_IDENTIFIER, new Runnable() {
            public void run() {
                _activity.runOnUiThread(new Runnable() {
                    public void run() {
                        _adapter.clear();
                        _adapter.addAll(_session.getAvailableSpells(Spell.SpellType.DEFENSIVE));
                        _adapter.notifyDataSetChanged();
                    }
                });
            }
        });
        _session.addActiveSpellsCallback(ACTIVE_SPELLS_CALLBACK_IDENTIFIER, new Runnable() {
            public void run() {
                _activity.runOnUiThread(new Runnable() {
                    public void run() {
                        _drawData();
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
                if (response.getWasSuccess()) {
                    _session.downloadActiveSpells(null);
                }
                else {
                    Dialog.setActivity(_activity);
                    Dialog.alert("Download Spells", "Error downloading spell list:\n\n    "+ response.getErrorMessage() +"\n", null);
                }
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
    }
}