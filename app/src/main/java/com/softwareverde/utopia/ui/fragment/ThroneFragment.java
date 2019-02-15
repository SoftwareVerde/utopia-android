package com.softwareverde.utopia.ui.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softwareverde.util.GifView;
import com.softwareverde.util.StringUtil;
import com.softwareverde.utopia.Util;
import com.softwareverde.utopia.AndroidUtil;
import com.softwareverde.utopia.DraftRate;
import com.softwareverde.utopia.Dragon;
import com.softwareverde.utopia.Kingdom;
import com.softwareverde.utopia.Province;
import com.softwareverde.utopia.R;
import com.softwareverde.utopia.Session;
import com.softwareverde.utopia.ui.dialog.DeployedArmyDialog;

import java.util.List;

public class ThroneFragment extends Fragment {
    protected Activity _activity;
    protected View _view;
    protected Session _session;
    protected Province _province;

    protected static final Integer[] _modifierIconIds = new Integer[] {
        R.id.throne_peasants_plus_minus,
        R.id.throne_offensive_units_plus_minus,
        R.id.throne_defensive_units_plus_minus,
        R.id.throne_draft_rate_plus_minus,
        R.id.throne_wage_plus_minus,
        R.id.throne_elites_plus_minus,
        R.id.throne_draft_rate_plus_minus,
        R.id.throne_draft_target_plus_minus,
        R.id.throne_thieves_plus_minus,
        R.id.throne_army_settings_group
    };

    protected void _toggleModifierIcons(Integer visibility) {
        for (Integer viewId : _modifierIconIds) {
            View view = _view.findViewById(viewId);
            if (view == null) { continue; }

            if (visibility == null) {
                view.setVisibility(view.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            }
            else {
                view.setVisibility((visibility == View.VISIBLE ? View.VISIBLE : View.GONE));
            }
        }
    }

    public ThroneFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() { super.onDestroy(); }

    protected void _showLoadingScreen() {
        _activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AndroidUtil.showLoadingScreen(_activity);
            }
        });
    }
    protected void _hideLoadingScreen() {
        _activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AndroidUtil.hideLoadingScreen();
            }
        });
    }

    protected String _formatNumberStringForDisplay(Integer number) {
        if (number == null) { return "??"; }

        return StringUtil.formatNumberString(number);
    }

    protected void _drawData() {
        // if (_view == null || ! _view.isShown()) { return; } // Can happen if the menu is selected during this invocation.
        if (_view == null || _province == null || ! _province.isValid()) { return; }

        _view.findViewById(R.id.province_tab_layout).setVisibility(View.GONE);
        _toggleModifierIcons(View.GONE);

        ((TextView) _view.findViewById(R.id.throne_province_name)).setText(_province.getName());

        ((TextView) _view.findViewById(R.id.throne_land)).setText(_formatNumberStringForDisplay(_province.getAcres()));

        ((TextView) _view.findViewById(R.id.throne_total_offense)).setText(_formatNumberStringForDisplay(_province.getTotalOffenseAtHome()));
        ((TextView) _view.findViewById(R.id.throne_total_defense)).setText(_formatNumberStringForDisplay(_province.getTotalDefenseAtHome()));

        String buildingEfficiencyString = "??";
        if (_province.getBuildingEfficiency() != null) {
            buildingEfficiencyString = Integer.valueOf(Math.round(_province.getBuildingEfficiency() * 100)).toString() + "%";
        }
        ((TextView) _view.findViewById(R.id.throne_building_efficiency)).setText(buildingEfficiencyString);

        ((TextView) _view.findViewById(R.id.throne_networth)).setText(_formatNumberStringForDisplay(_province.getNetworth()));

        ((TextView) _view.findViewById(R.id.throne_peasants)).setText(_formatNumberStringForDisplay(_province.getPeasants()));
        ((TextView) _view.findViewById(R.id.throne_food)).setText(_formatNumberStringForDisplay(_province.getFood()));

        ((TextView) _view.findViewById(R.id.throne_money)).setText(_formatNumberStringForDisplay(_province.getMoney()));
        ((TextView) _view.findViewById(R.id.throne_runes)).setText(_formatNumberStringForDisplay(_province.getRunes()));

        String stealthString = "??";
        if (_province.getStealth() != null) {
            stealthString = _province.getStealth().toString() +"%";
        }
        ((TextView) _view.findViewById(R.id.throne_stealth)).setText(stealthString);

        String manaString = "??";
        if (_province.getMana() != null) {
            manaString = _province.getMana().toString() +"%";
        }
        ((TextView) _view.findViewById(R.id.throne_mana)).setText(manaString);

        ((TextView) _view.findViewById(R.id.throne_soldiers)).setText(_formatNumberStringForDisplay(_province.getSoldiers()));
        ((TextView) _view.findViewById(R.id.throne_elites)).setText(_formatNumberStringForDisplay(_province.getElites()));
        ((TextView) _view.findViewById(R.id.throne_offensive_units)).setText(_formatNumberStringForDisplay(_province.getOffensiveUnits()));
        ((TextView) _view.findViewById(R.id.throne_defensive_units)).setText(_formatNumberStringForDisplay(_province.getDefensiveUnits()));
        ((TextView) _view.findViewById(R.id.throne_horses)).setText(_formatNumberStringForDisplay(_province.getHorses()));
        ((TextView) _view.findViewById(R.id.throne_prisoners)).setText(_formatNumberStringForDisplay(_province.getPrisoners()));

        DraftRate draftRate = _province.getDraftRate();
        if (draftRate != null) {
            ((TextView) _view.findViewById(R.id.throne_draft_rate)).setText(draftRate.getName());
        }
        ((TextView) _view.findViewById(R.id.throne_draft_target)).setText(_province.getDraftTarget().toString() +"%");
        ((TextView) _view.findViewById(R.id.throne_wage)).setText(_province.getMilitaryWageRate().toString());

        ((TextView) _view.findViewById(R.id.throne_thieves)).setText(_formatNumberStringForDisplay(_province.getThieves()));
        ((TextView) _view.findViewById(R.id.throne_wizards)).setText(_formatNumberStringForDisplay(_province.getWizards()));

        LinearLayout deployedArmiesContainer = (LinearLayout) _view.findViewById(R.id.throne_deployed_armies);
        deployedArmiesContainer.removeAllViews();
        if (_province.hasArmiesDeployed()) {
            final List<Province.DeployedArmy> deployedArmies = _province.getDeployedArmies();
            for (final Province.DeployedArmy deployedArmy : deployedArmies) {
                LayoutInflater inflater = _activity.getLayoutInflater();
                View deployedArmyView = inflater.inflate(R.layout.deployed_army_status, null);
                ((TextView) deployedArmyView.findViewById(R.id.deployed_army_acres)).setText(_formatNumberStringForDisplay(deployedArmy.getAcres()));

                ((TextView) deployedArmyView.findViewById(R.id.deployed_army_generals)).setText(_formatNumberStringForDisplay(deployedArmy.getGenerals()));
                if (deployedArmy.getGenerals() != null && deployedArmy.getGenerals() > 1) {
                    ((TextView) deployedArmyView.findViewById(R.id.deployed_army_generals_label)).setText("generals deployed");
                }

                Integer returnTime = deployedArmy.getReturnTime();
                Integer hours = (int) (returnTime / 60.0f / 60.0f);
                Integer minutes = (int) (((returnTime / 60.0f / 60.0f) - hours) * 60.0f);
                ((TextView) deployedArmyView.findViewById(R.id.deployed_army_return_time)).setText(String.format("%sh %sm", hours, minutes));

                ProgressBar progressBar = ((ProgressBar) deployedArmyView.findViewById(R.id.deployed_army_progress_bar));
                if (returnTime < progressBar.getMax()) {
                    progressBar.setProgress(returnTime);
                }
                else {
                    progressBar.setProgress(progressBar.getMax());
                }

                deployedArmyView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DeployedArmyDialog deployedArmyDialog = new DeployedArmyDialog();
                        deployedArmyDialog.setActivity(_activity);
                        deployedArmyDialog.setDeployedArmy(deployedArmy);
                        deployedArmyDialog.show(_activity.getFragmentManager(), "DEPLOYED_ARMY_DETAILS");
                    }
                });

                if (returnTime >= 0) {
                    deployedArmiesContainer.addView(deployedArmyView);
                }
            }
        }

        Kingdom kingdom = _session.getKingdom(_province.getKingdomIdentifier());
        if ((kingdom != null && kingdom.hasDragon())) {
            Dragon dragon = kingdom.getDragon();
            View dragonBanner = _view.findViewById(R.id.throne_dragon_banner);
            dragonBanner.setVisibility(View.VISIBLE);
            dragonBanner.setBackgroundColor(Color.parseColor(dragon.getColorString()));
            ((TextView) _view.findViewById(R.id.throne_dragon_banner_text)).setText("A " + dragon.getNamedType() + " Dragon is attacking our kingdom!");

            // Set the Margin-Bottom
            LinearLayout mainContainer = (LinearLayout) _view.findViewById(R.id.throne_main_layout);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mainContainer.getLayoutParams();
            layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin, layoutParams.rightMargin, dragonBanner.getHeight());
            mainContainer.setLayoutParams(layoutParams);
        }
        else {
            _view.findViewById(R.id.throne_dragon_banner).setVisibility(View.GONE);

            // Clear the Margin-Bottom
            LinearLayout mainContainer = (LinearLayout) _view.findViewById(R.id.throne_main_layout);

            // Layout params are based on the parent type...
            //  Support either type, if the parent type changes.
            try {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mainContainer.getLayoutParams();
                layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin, layoutParams.rightMargin, 0);
                mainContainer.setLayoutParams(layoutParams);
            } catch (ClassCastException e) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mainContainer.getLayoutParams();
                layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin, layoutParams.rightMargin, 0);
                mainContainer.setLayoutParams(layoutParams);
            }
        }
    }

    protected void _showPremiumIcon() {
        GifView gifView = (GifView) _view.findViewById(R.id.throne_premium_icon);
        gifView.init(_activity, R.raw.nyan_cat);
        gifView.setWidth(100);
        gifView.setHeight(100);
        if (Build.VERSION.SDK_INT >= 11) {
            gifView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        gifView.setVisibility(View.VISIBLE);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.throne, container, false);
        _view = rootView;

        _view.findViewById(R.id.aid_icon).setVisibility(View.GONE);
        _view.findViewById(R.id.throne_premium_icon).setVisibility(View.GONE);

        _drawData();

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        _activity = activity;
        _session = Session.getInstance();

        _drawData();
    }

    public void setProvince(Province province) {
        _province = province;
        _drawData();
    }
}