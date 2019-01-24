package com.softwareverde.utopia.ui.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softwareverde.util.Dialog;
import com.softwareverde.util.Util;
import com.softwareverde.utopia.AndroidUtil;
import com.softwareverde.utopia.HostilityMeter;
import com.softwareverde.utopia.Kingdom;
import com.softwareverde.utopia.Province;
import com.softwareverde.utopia.R;
import com.softwareverde.utopia.Session;
import com.softwareverde.utopia.ui.adapter.Kingdom2Adapter;

public class Kingdom2Fragment extends Fragment {
    private static String ON_KINGDOM_CHANGE_CALLBACK_IDENTIFIER = "KingdomOnKingdomChangeId";

    public static void drawKingdomNavigation(final Session session, final View view, final Kingdom.Identifier kingdomIdentifier) {
        final EditText kingdomIdView = ((EditText) view.findViewById(R.id.kingdom_kingdom_id));
        final EditText islandIdView = ((EditText) view.findViewById(R.id.kingdom_island_id));

        String kingdomId = "00";
        String islandId = "00";

        if (kingdomIdentifier != null && session.hasKingdom(kingdomIdentifier)) {
            kingdomId = kingdomIdentifier.getKingdomId().toString();
            if (kingdomId.length() == 1) kingdomId = "0" + kingdomId;

            islandId = kingdomIdentifier.getIslandId().toString();
            if (islandId.length() == 1) islandId = "0" + islandId;
        }

        final String currentKingdomIdText = kingdomIdView.getText().toString();
        final String currentIslandIdText = islandIdView.getText().toString();
        if (! currentKingdomIdText.equals(kingdomId)) {
            kingdomIdView.setText(kingdomId);
        }
        if (! currentIslandIdText.equals(islandId)) {
            islandIdView.setText(islandId);
        }
    }

    static public void bindKingdomNavigation(Activity activity, Session session, View view, Kingdom.Identifier selectedKingdomIdentifier, Runnable drawData) {
        _bindKingdomNavigation(activity, session, view, selectedKingdomIdentifier, drawData);
    }
    static private void _bindKingdomNavigation(final Activity activity, final Session session, final View view, final Kingdom.Identifier selectedKingdomIdentifier, Runnable updateViews) {
        final Runnable drawData;
        if (updateViews == null) {
            drawData = new Runnable() {
                @Override
                public void run() {
                    // Nothing.
                }
            };
        }
        else {
            drawData = updateViews;
        }

        final Runnable updateKingdomInputViews = new Runnable() {
            @Override
            public void run() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        KingdomFragment.drawKingdomNavigation(session, view, selectedKingdomIdentifier);

                        AndroidUtil.closeKeyboard(activity);
                        AndroidUtil.hideLoadingScreen();
                    }
                });
            }
        };

        view.findViewById(R.id.kingdom_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AndroidUtil.showLoadingScreen(activity);

                session.downloadNextKingdom(selectedKingdomIdentifier, new Session.DownloadKingdomCallback() {
                    @Override
                    public void run(Session.SessionResponse response, Kingdom.Identifier kingdomIdentifier) {
                        if (response.getWasSuccess()) {
                            selectedKingdomIdentifier.update(kingdomIdentifier);

                            updateKingdomInputViews.run();
                            activity.runOnUiThread(drawData);
                        }
                        else {
                            Dialog.setActivity(activity);
                            Dialog.alert("Download Kingdom", response.getErrorMessage(), new Runnable() {
                                @Override
                                public void run() {
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            AndroidUtil.hideLoadingScreen();
                                        }
                                    });
                                }
                            });
                        }
                    }
                });
            }
        });
        view.findViewById(R.id.kingdom_previous).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AndroidUtil.showLoadingScreen(activity);

                session.downloadPreviousKingdom(selectedKingdomIdentifier, new Session.DownloadKingdomCallback() {
                    @Override
                    public void run(Session.SessionResponse response, Kingdom.Identifier kingdomIdentifier) {
                        if (response.getWasSuccess()) {
                            selectedKingdomIdentifier.update(kingdomIdentifier);

                            updateKingdomInputViews.run();
                            activity.runOnUiThread(drawData);
                        }
                        else {
                            Dialog.setActivity(activity);
                            Dialog.alert("Download Kingdom", response.getErrorMessage(), new Runnable() {
                                @Override
                                public void run() {
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            AndroidUtil.hideLoadingScreen();
                                        }
                                    });
                                }
                            });
                        }
                    }
                });
            }
        });

        final EditText kingdomIdView = (EditText) view.findViewById(R.id.kingdom_kingdom_id);
        final EditText islandIdView = (EditText) view.findViewById(R.id.kingdom_island_id);
        TextView.OnEditorActionListener onEditorActionListener = new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Integer newKingdomId = Util.parseInt(kingdomIdView.getText().toString());
                    Integer newIslandId = Util.parseInt(islandIdView.getText().toString());
                    if (newKingdomId > 0 && newIslandId > 0 && ! (selectedKingdomIdentifier.getKingdomId().equals(newKingdomId) && selectedKingdomIdentifier.getIslandId().equals(newIslandId))) {
                        Kingdom.Identifier newKingdomIdentifier = new Kingdom.Identifier(newKingdomId, newIslandId);

                        AndroidUtil.showLoadingScreen(activity);
                        session.downloadKingdom(newKingdomIdentifier, new Session.DownloadKingdomCallback() {
                            @Override
                            public void run(Session.SessionResponse response, Kingdom.Identifier kingdomIdentifier) {
                                if (response.getWasSuccess()) {
                                    selectedKingdomIdentifier.update(kingdomIdentifier);
                                }
                                else {
                                    Dialog.setActivity(activity);
                                    Dialog.alert("Download Kingdom", response.getErrorMessage(), new Runnable() {
                                        @Override
                                        public void run() {
                                            activity.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    AndroidUtil.hideLoadingScreen();
                                                }
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }

                    textView.clearFocus();
                    AndroidUtil.closeKeyboard(activity);

                    return true;
                }

                return false;
            }
        };
        islandIdView.setOnEditorActionListener(onEditorActionListener);
        kingdomIdView.setOnEditorActionListener(onEditorActionListener);

        kingdomIdView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    kingdomIdView.setSelection(kingdomIdView.getText().length());
                }
            }
        });
        islandIdView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    islandIdView.setSelection(islandIdView.getText().length());
                }
            }
        });
    }

    private Activity _activity;
    private Session _session;
    private View _view;
    private Kingdom2Adapter _adapter;
    private Kingdom.Identifier _selectedKingdomIdentifier = new Kingdom.Identifier(0, 0);
    private Boolean _useCachedKingdomData = false;

    public Kingdom2Fragment() { }
    public void setKingdomIdentifier(Kingdom.Identifier kingdomIdentifier) {
        _selectedKingdomIdentifier.update(kingdomIdentifier);
    }
    public void setShouldUseCachedKingdomData(final Boolean shouldUseCache) {
        _useCachedKingdomData = shouldUseCache;
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

    private void _setViewWidth(final View view, final Integer width) {
        final ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = width;
        view.setLayoutParams(layoutParams);
    }

    private void _drawDataOnUiThread() {
        _activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _drawData();
            }
        });
    }

    private void _drawData() {
        _adapter.clear();

        final TextView warBanner = ((TextView) _view.findViewById(R.id.kingdom_war_banner));
        final View hostilityMeterContainer = _view.findViewById(R.id.kingdom_hostility_meter_container);

        String kingdomName = "";
        String stance = "";
        String networth = "";

        if (_selectedKingdomIdentifier != null && _session.hasKingdom(_selectedKingdomIdentifier)) {
            final Kingdom kingdom = _session.getKingdom(_selectedKingdomIdentifier);

            _adapter.addAll(kingdom.getProvinces());

            if (kingdom.isAtWar()) {
                warBanner.setVisibility(View.VISIBLE);
                warBanner.setText("At war with "+ kingdom.getWarringKingdomIdentifier().toString());
            }
            else {
                warBanner.setVisibility(View.GONE);
            }

            stance = kingdom.getStance();
            kingdomName = kingdom.getName();
            networth = Util.formatNumberString(kingdom.getNetworth()) + " NW";

            { // Draw Hostility Meter
                if (kingdom.hasHostilityMeter()) {
                    final Integer containerWidth = hostilityMeterContainer.getWidth();
                    final HostilityMeter hostilityMeter = kingdom.getHostilityMeter();
                    final TextView attitudeTextView = (TextView) _view.findViewById(R.id.kingdom_hostility_meter_relation);
                    final TextView enemyAttitudeTextView = (TextView) _view.findViewById(R.id.kingdom_enemy_hostility_meter_relation);
                    final View meterValue = _view.findViewById(R.id.kingdom_hostility_meter_value);
                    final View enemyMeterValue = _view.findViewById(R.id.kingdom_enemy_hostility_meter_value);

                    attitudeTextView.setText(kingdom.getRelation());
                    enemyAttitudeTextView.setText(kingdom.getEnemyRelation());

                    _setViewWidth(meterValue, (int) (containerWidth * hostilityMeter.getPercentageWithinHostilityLevel()));
                    _setViewWidth(enemyMeterValue, (int) (containerWidth * hostilityMeter.getEnemyPercentageWithinHostilityLevel()));

                    meterValue.setBackgroundColor(Color.parseColor(hostilityMeter.getHostilityColor()));
                    enemyMeterValue.setBackgroundColor(Color.parseColor(hostilityMeter.getEnemyHostilityColor()));
                    hostilityMeterContainer.setVisibility(View.VISIBLE);
                }
                else {
                    hostilityMeterContainer.setVisibility(View.GONE);
                }
            }
        }
        else {
            hostilityMeterContainer.setVisibility(View.GONE);
            warBanner.setVisibility(View.GONE);
        }

        ((TextView) _view.findViewById(R.id.kingdom_stance)).setText(stance);
        ((TextView) _view.findViewById(R.id.kingdom_name)).setText(kingdomName);
        ((TextView) _view.findViewById(R.id.kingdom_networth)).setText(networth);

        _adapter.notifyDataSetChanged();

        Kingdom2Fragment.drawKingdomNavigation(_session, _view, _selectedKingdomIdentifier);

        { // Draw User's Kingdom Identifier
            final Kingdom kingdom = _session.getKingdom();
            if (kingdom != null) {
                TextView provinceKingdom = (TextView) _view.findViewById(R.id.kingdom_province_kingdom);
                provinceKingdom.setText("My Kingdom: " + _session.getKingdom().getIdentifier());
            }
        }

        _hideLoadingScreen();
    }

    private void _showAidProvinceFragment(Province province) {
        FragmentManager fragmentManager = ((AppCompatActivity) _activity).getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);

        final AidFragment aidFragment = new AidFragment();
        aidFragment.setProvince(province);
        transaction.replace(R.id.container, aidFragment);

        AndroidUtil.closeKeyboard(_activity);
        transaction.commitAllowingStateLoss();
    }

    private void _showViewProvinceFragment(Province province) {
        FragmentManager fragmentManager = ((AppCompatActivity) _activity).getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);

        ThroneFragment throneFragment = new TabbedThroneFragment();
        throneFragment.setProvince(province);
        transaction.replace(R.id.container, throneFragment);

        AndroidUtil.closeKeyboard(_activity);
        transaction.commitAllowingStateLoss();
    }

    private void _showThieveryFragment(Province province) {
        FragmentManager fragmentManager = ((AppCompatActivity) _activity).getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);

        ThieveryOpsFragment thieveryOpsFragment = new ThieveryOpsFragment();
        thieveryOpsFragment.setTargetProvince(province);
        transaction.replace(R.id.container, thieveryOpsFragment);

        AndroidUtil.closeKeyboard(_activity);
        transaction.commitAllowingStateLoss();
    }

    private void _showMagicFragment(Province province) {
        FragmentManager fragmentManager = ((AppCompatActivity) _activity).getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);

        OffensiveSpellListFragment offensiveSpellListFragment = new OffensiveSpellListFragment();
        offensiveSpellListFragment.setTargetProvince(province);
        transaction.replace(R.id.container, offensiveSpellListFragment);

        AndroidUtil.closeKeyboard(_activity);
        transaction.commitAllowingStateLoss();
    }

    private void _showAttackFragment(Province province) {
        FragmentManager fragmentManager = ((AppCompatActivity) _activity).getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);

        AttackFragment attackFragment = new AttackFragment();
        attackFragment.setTargetProvince(province);
        transaction.replace(R.id.container, attackFragment);

        AndroidUtil.closeKeyboard(_activity);
        transaction.commitAllowingStateLoss();
    }

    private void _blurFocusedItem() {
        final ViewGroup buttonsContainer = (ViewGroup) _view.findViewById(R.id.kingdom_focused_buttons);
        final RelativeLayout focusedItemOverlay = (RelativeLayout) _view.findViewById(R.id.kingdom_focused_item_overlay);

        final Animation fadeOutAnimation = AnimationUtils.loadAnimation(_activity, android.R.anim.fade_out);
        fadeOutAnimation.setDuration(300);
        focusedItemOverlay.setAnimation(fadeOutAnimation);
        buttonsContainer.setAnimation(fadeOutAnimation);

        buttonsContainer.setVisibility(View.GONE);
        focusedItemOverlay.setVisibility(View.GONE);
    }

    private void _focusItem(final View focusedItemView, final Province selectedProvince) {
        final ViewGroup buttonsContainer = (ViewGroup) _view.findViewById(R.id.kingdom_focused_buttons);
        final RelativeLayout focusedItemOverlay = (RelativeLayout) _view.findViewById(R.id.kingdom_focused_item_overlay);

        focusedItemOverlay.removeAllViews();

        focusedItemOverlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _blurFocusedItem();
                focusedItemOverlay.setOnClickListener(null);
            }
        });

        final Bitmap bitmap = AndroidUtil.viewToBitmap(focusedItemView);
        final ImageView imageView = new ImageView(_activity);
        imageView.setImageBitmap(bitmap);

        final Integer viewX;
        final Integer viewY;
        {
            final int[] position = new int[2];
            focusedItemView.getLocationOnScreen(position);

            final int[] parentPosition = new int[2];
            _view.getLocationOnScreen(parentPosition);

            final Integer x = position[0] - parentPosition[0];
            final Integer y = position[1] - parentPosition[1];

            viewX = (x < 0 ? 0 : x);
            viewY = (y < 0 ? 0 : y);
        }

        imageView.setX(viewX);
        imageView.setY(viewY);

        focusedItemOverlay.addView(imageView);

        imageView.setBackgroundResource(R.drawable.edit_text_background); // Particularly necessary for resources...

        final Animation fadeInAnimation = AnimationUtils.loadAnimation(_activity, android.R.anim.fade_in);
        fadeInAnimation.setDuration(500);

        final View aidFocusButton = _view.findViewById(R.id.kingdom_focused_button_aid);
        aidFocusButton.setVisibility(selectedProvince.getKingdomIdentifier().equals(_session.getKingdom().getIdentifier()) ? View.VISIBLE : View.GONE);

        focusedItemOverlay.setAnimation(fadeInAnimation);
        focusedItemOverlay.setVisibility(View.VISIBLE);
        buttonsContainer.setAnimation(fadeInAnimation);
        buttonsContainer.setVisibility(View.VISIBLE);

        { // Bind Focus Buttons
            _view.findViewById(R.id.kingdom_focused_button_aid).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _showAidProvinceFragment(selectedProvince);
                }
            });

            _view.findViewById(R.id.kingdom_focused_button_view).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _showViewProvinceFragment(selectedProvince);
                }
            });

            _view.findViewById(R.id.kingdom_focused_button_thievery).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _showThieveryFragment(selectedProvince);
                }
            });

            _view.findViewById(R.id.kingdom_focused_button_magic).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _showMagicFragment(selectedProvince);
                }
            });

            _view.findViewById(R.id.kingdom_focused_button_attack).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _showAttackFragment(selectedProvince);
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.kingdom2, container, false);
        _view = rootView;

        final View buttonsContainer = _view.findViewById(R.id.kingdom_focused_buttons);

        buttonsContainer.setVisibility(View.GONE);
        _view.findViewById(R.id.kingdom_focused_item_overlay).setVisibility(View.GONE);

        buttonsContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Nothing.
            }
        });

        final GridView gridView = (GridView) _view.findViewById(R.id.kingdom_listview);
        gridView.setAdapter(_adapter);

        gridView.setClickable(true);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (_adapter.getSelectedIndex() == i) {
                    _adapter.deselectIndex();
                }
                else {
                    _adapter.selectIndex(i);
                }
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _focusItem(view, _adapter.getItem(position));
            }
        });

        _drawDataOnUiThread();
        if (! _useCachedKingdomData) {
            _showLoadingScreen();
        }

        Kingdom2Fragment._bindKingdomNavigation(_activity, _session, _view, _selectedKingdomIdentifier, new Runnable() {
            @Override
            public void run() {
                _drawDataOnUiThread();
            }
        });

        _view.findViewById(R.id.kingdom_war_banner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Kingdom.Identifier warringKingdomIdentifier = _session.getKingdom(_selectedKingdomIdentifier).getWarringKingdomIdentifier();
                if (warringKingdomIdentifier.isValid()) {
                    _showLoadingScreen();
                    AndroidUtil.showLoadingScreen(_activity);
                    _session.downloadKingdom(warringKingdomIdentifier, new Session.DownloadKingdomCallback() {
                        @Override
                        public void run(Session.SessionResponse response, Kingdom.Identifier kingdomIdentifier) {
                            if (response.getWasSuccess()) {
                                _selectedKingdomIdentifier.update(kingdomIdentifier);
                                _drawDataOnUiThread();
                            }
                            else {
                                Dialog.setActivity(_activity);
                                Dialog.alert("Download Kingdom", response.getErrorMessage(), new Runnable() {
                                    @Override
                                    public void run() {
                                        _hideLoadingScreen();
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

        if (_session.getKingdom() == null) {
            _session.downloadKingdom(new Session.DownloadKingdomCallback() {
                @Override
                public void run(Session.SessionResponse response, Kingdom.Identifier kingdomIdentifier) {
                    _activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            _drawDataOnUiThread();
                        }
                    });
                }
            });
        }

        TextView provinceKingdom = (TextView) _view.findViewById(R.id.kingdom_province_kingdom);
        provinceKingdom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _showLoadingScreen();
                _session.downloadKingdom(new Session.DownloadKingdomCallback() {
                    @Override
                    public void run(Session.SessionResponse response, Kingdom.Identifier kingdomIdentifier) {
                        _drawDataOnUiThread();
                        _hideLoadingScreen();
                    }
                });
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _activity = activity;
        _session = Session.getInstance();
        _adapter = new Kingdom2Adapter(_activity);

        _session.addKingdomCallback(ON_KINGDOM_CHANGE_CALLBACK_IDENTIFIER, new Session.KingdomCallback() {
            @Override
            public void run(Kingdom.Identifier kingdomIdentifier) {
                _selectedKingdomIdentifier.update(kingdomIdentifier);
                _session.setFocusedKingdom(kingdomIdentifier);
                _drawDataOnUiThread();

                _session.downloadActiveSpells(_selectedKingdomIdentifier, new Session.Callback() {
                    @Override
                    public void run(Session.SessionResponse response) {
                        _drawDataOnUiThread();
                    }
                });

                _session.downloadAvailableVerdeIntelCounts(_selectedKingdomIdentifier, new Session.Callback() {
                    @Override
                    public void run(Session.SessionResponse response) {
                        _drawDataOnUiThread();
                    }
                });
            }
        });

        Kingdom.Identifier focusedKingdomIdentifier = _session.getFocusedKingdomIdentifier();
        if (focusedKingdomIdentifier != null && focusedKingdomIdentifier.isValid()) {
            _selectedKingdomIdentifier.update(focusedKingdomIdentifier);
        }

        if (!_useCachedKingdomData) {
            _downloadSelectedKingdom();
        }
    }

    @Override
    public void onDetach() {
        _session.removeKingdomCallback(ON_KINGDOM_CHANGE_CALLBACK_IDENTIFIER);
        super.onDetach();
    }

    private void _downloadSelectedKingdom() {
        final Session.DownloadKingdomCallback downloadKingdomCallback = new Session.DownloadKingdomCallback() {
            @Override
            public void run(Session.SessionResponse response, Kingdom.Identifier kingdomIdentifier) {
                if (response.getWasSuccess()) {
                    _selectedKingdomIdentifier.update(kingdomIdentifier);

                    // NOTE: _drawData must be called because the session.addCallback callbacks are invoked
                    //  before this callback, and _selectedKingdomIdentifier might not have been set yet...
                    _drawDataOnUiThread();

                    _session.downloadActiveSpells(_selectedKingdomIdentifier, new Session.Callback() {
                        @Override
                        public void run(Session.SessionResponse response) {
                            _drawDataOnUiThread();
                        }
                    });

                    _session.downloadAvailableVerdeIntelCounts(_selectedKingdomIdentifier, new Session.Callback() {
                        @Override
                        public void run(Session.SessionResponse response) {
                            _drawDataOnUiThread();
                        }
                    });
                }
                else {
                    Dialog.setActivity(_activity);
                    Dialog.alert("Download Kingdom", response.getErrorMessage(), new Runnable() {
                        @Override
                        public void run() {
                            _hideLoadingScreen();
                        }
                    });
                }
            }
        };

        if (! _selectedKingdomIdentifier.isValid()) {
            _session.downloadKingdom(downloadKingdomCallback);
        }
        else {
            _session.downloadKingdom(_selectedKingdomIdentifier, downloadKingdomCallback);
        }
    }
}