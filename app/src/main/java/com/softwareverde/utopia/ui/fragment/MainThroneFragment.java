package com.softwareverde.utopia.ui.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.softwareverde.util.Dialog;
import com.softwareverde.util.StringUtil;
import com.softwareverde.utopia.Util;
import com.softwareverde.util.WebRequestSynchronizer;
import com.softwareverde.utopia.AndroidUtil;
import com.softwareverde.utopia.DraftRate;
import com.softwareverde.utopia.Dragon;
import com.softwareverde.utopia.Kingdom;
import com.softwareverde.utopia.Province;
import com.softwareverde.utopia.R;
import com.softwareverde.utopia.Session;
import com.softwareverde.utopia.TrainArmyData;
import com.softwareverde.utopia.Tutorial;
import com.softwareverde.utopia.ui.MainActivity;
import com.softwareverde.utopia.ui.UiTheme;
import com.softwareverde.utopia.ui.dialog.DeployedArmyDialog;
import com.softwareverde.utopia.ui.dialog.EditOptionDialog;
import com.softwareverde.utopia.ui.dialog.EditValueDialog;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import java.util.ArrayList;
import java.util.List;

public class MainThroneFragment extends Fragment {
    protected static final String THRONE_CALLBACK_IDENTIFIER = "MainThroneFragment_ThroneUpdateCallbackIdentifier";
    protected static final String MILITARY_SETTINGS_CALLBACK_IDENTIFIER = "MainThroneFragment_MilitarySettingsCallbackIdentifier";
    protected static final String UTOPIA_DATE_CALLBACK_IDENTIFIER = "MainThroneFragment_UtopiaDateCallbackIdentifier";

    private MainActivity _activity;
    private Session _session;
    private View _view;
    private View _header;
    private Tutorial _tutorial;

    private Integer _maxResourceHeight;

    private void _onBack() {
        _activity.onNavigationDrawerItemSelected(NavigationDrawerFragment.THRONE_TAB_ID);
    }

    private void _downloadProvinceData(final Runnable callback) {
        _showLoadingScreen();

        _session.downloadKingdom(new Session.DownloadKingdomCallback() {
            @Override
            public void run(Session.SessionResponse response, Kingdom.Identifier kingdomIdentifier) {
                _session.downloadThrone(new Session.Callback() {
                    @Override
                    public void run(Session.SessionResponse response) {
                        if (response.getWasSuccess()) {
                            final WebRequestSynchronizer webRequestSynchronizer = new WebRequestSynchronizer(5, new Runnable() {
                                @Override
                                public void run() {
                                    _activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            _drawData();
                                            _bindAllInputs(); // NOTE: Military Settings is required to set unit cost...
                                            _hideLoadingScreen();

                                            if (callback != null) {
                                                callback.run();
                                            }
                                        }
                                    });
                                }
                            });

                            _session.downloadMilitaryCouncil(new Session.Callback() {
                                @Override
                                public void run(Session.SessionResponse response) {
                                    webRequestSynchronizer.onComplete();
                                }
                            });
                            _session.downloadStateCouncil(new Session.Callback() {
                                @Override
                                public void run(Session.SessionResponse response) {
                                    webRequestSynchronizer.onComplete();
                                }
                            });
                            _session.downloadMilitarySettings(new Session.Callback() {
                                @Override
                                public void run(Session.SessionResponse response) {
                                    webRequestSynchronizer.onComplete();
                                }
                            });
                            _session.downloadAvailableSpells(new Session.Callback() {
                                @Override
                                public void run(Session.SessionResponse response) {
                                    webRequestSynchronizer.onComplete();
                                }
                            });
                            _session.downloadBuildingsCouncil(new Session.Callback() {
                                @Override
                                public void run(Session.SessionResponse response) {
                                    webRequestSynchronizer.onComplete();
                                }
                            });

                        }
                        else {
                            Dialog.setActivity(_activity);
                            Dialog.alert("Download Throne", "Error downloading Throne:\n\n  " + response.getErrorMessage() + "\n", null);
                            _hideLoadingScreen();
                        }
                    }
                });
            }
        });
    }

    private enum ResourceType { STEALTH, MANA }
    private void _setResourceViewPercent(final Float percent, final ResourceType resourceType) {
        final View resourceView = _view.findViewById(resourceType.equals(ResourceType.STEALTH) ? R.id.throne_stealth_image : R.id.throne_mana_image);
        final TextView resourceTextView = (TextView) _view.findViewById(resourceType.equals(ResourceType.STEALTH) ? R.id.throne_stealth : R.id.throne_mana);

        final ViewGroup.LayoutParams layoutParams = resourceView.getLayoutParams();
        layoutParams.height = (int) (_maxResourceHeight * (percent >= 0F ? percent : 0F));
        resourceView.setLayoutParams(layoutParams);

        resourceTextView.setText(((int) (percent * 100)) +"%");
    }

    protected String _formatNumberStringForDisplay(Integer number) {
        if (number == null) { return "??"; }

        return StringUtil.formatNumberString(number);
    }

    protected void _setViewWeight(final Integer viewId, final Float weight) {
        final View view = _view.findViewById(viewId);
        final LinearLayout.LayoutParams viewLayoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
        viewLayoutParams.weight = weight;
        view.setLayoutParams(viewLayoutParams);
    }

    protected void _setDisplayPercent(final Integer viewId, final Integer viewVoidId, final Float percent) {
        _setViewWeight(viewId, percent);
        _setViewWeight(viewVoidId, 1.0F - percent);
    }

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

    protected interface FieldBinding {
        void onSubmit(Integer providedValue, Boolean isExpedited);
    }

    protected class FieldBindingValueGetter {
        public Integer getCurrentValue() { return 0; }
        public Integer getInProgressValue() { return 0; }
        public Boolean getShouldDisplay() { return true; }
        public Integer[] getInProgress() { return null; }
    }

    @Nullable
    private TrainArmyData _getDefaultTrainArmyBundle() {
        final Province province = _session.getProvince();

        final Province.Race race = province.getRace();
        final DraftRate draftRate = province.getDraftRate();
        final Integer draftTarget = province.getDraftTarget();
        final Integer wageRate = province.getMilitaryWageRate();

        if (race == null || draftRate == null || draftTarget == null || wageRate == null) {
            return null;
        }

        final TrainArmyData trainArmyData = new TrainArmyData(race);
        trainArmyData.setDraftRate(draftRate);
        trainArmyData.setDraftTarget(draftTarget);
        trainArmyData.setWageRate(wageRate);
        return trainArmyData;
    }

    private final Session.Callback _defaultTrainArmyCallback = new Session.Callback() {
        @Override
        public void run(Session.SessionResponse response) {
            if (! response.getWasSuccess()) {
                Dialog.setActivity(_activity);
                Dialog.alert("Train Army", "Error training army.\n\n"+ response.getErrorMessage(), null);
                return;
            }

            _session.downloadThrone(new Session.Callback() {
                @Override
                public void run(Session.SessionResponse response) {
                    final WebRequestSynchronizer webRequestSynchronizer = new WebRequestSynchronizer(2, new Runnable() {
                        @Override
                        public void run() {
                            _activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    _drawData();
                                    _hideLoadingScreen();
                                }
                            });
                        }
                    });

                    _session.downloadMilitaryCouncil(new Session.Callback() {
                        @Override
                        public void run(Session.SessionResponse response) {
                            webRequestSynchronizer.onComplete();
                        }
                    });

                    _session.downloadMilitarySettings(new Session.Callback() {
                        @Override
                        public void run(Session.SessionResponse response) {
                            webRequestSynchronizer.onComplete();
                        }
                    });
                }
            });
        }
    };

    final EditValueDialog _createDefaultEditValueDialog(String title, String positiveButtonText, Integer cost, FieldBindingValueGetter fieldBindingValueGetter, final FieldBinding binding) {
        final EditValueDialog editValueDialog = new EditValueDialog();
        editValueDialog.setActivity(_activity);
        editValueDialog.setTitle(title);
        // editValueDialog.setContent(content);

        if (positiveButtonText != null) {
            editValueDialog.setPositiveButtonText(positiveButtonText);
        }

        editValueDialog.setExpediteText("Expedite Training");
        editValueDialog.setOnNegativeString("Releasing");
        editValueDialog.setOnPositiveString("Training");
        editValueDialog.setValueTypeString("Troops");
        editValueDialog.setCost(cost);

        String currentValueString = null;
        final Integer currentValue = fieldBindingValueGetter.getCurrentValue();
        if (currentValue != null) {
            currentValueString = StringUtil.formatNumberString(currentValue);
        }
        editValueDialog.setCurrentValue(currentValueString);

        String inProgressValueString = null;
        final Integer inProgressValue = fieldBindingValueGetter.getInProgressValue();
        if (inProgressValue != null) {
            inProgressValueString = StringUtil.formatNumberString(inProgressValue);
        }
        editValueDialog.setInProgressValue(inProgressValueString);

        final Integer[] valuesInProgress = fieldBindingValueGetter.getInProgress();
        if (valuesInProgress != null) {
            editValueDialog.setInProgress(valuesInProgress);
        }

        editValueDialog.setCallback(new EditValueDialog.Callback() {
            public void run(String newValue, Boolean isExpedited) {

                final Integer newValueInt = Util.parseInt(newValue);
                if (newValue == null || newValue.length() == 0 || newValueInt == 0) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(_activity, AlertDialog.THEME_HOLO_DARK);
                    dialogBuilder.setTitle("Invalid Value");
                    dialogBuilder.setMessage("Invalid value.");
                    dialogBuilder.setPositiveButton("Ok", null);
                    dialogBuilder.create().show();
                }
                else {
                    binding.onSubmit(newValueInt, isExpedited);
                }
            }
        });
        return editValueDialog;
    }

    private void _bindFieldInputs(Integer[] inputIds, String title, Integer cost, FieldBindingValueGetter fieldBindingValueGetter, FieldBinding binding, String positiveButtonText) {
        for (Integer inputId : inputIds) {
            _bindFieldInput(inputId, title, cost, fieldBindingValueGetter, binding, positiveButtonText);
        }
    }
    private void _bindFieldInput(final Integer inputId, final String title, final Integer cost, final FieldBindingValueGetter fieldBindingValueGetter, final FieldBinding binding, final String positiveButtonText) {
        _view.findViewById(inputId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fieldBindingValueGetter.getShouldDisplay()) {
                    EditValueDialog editValueDialog = _createDefaultEditValueDialog(
                            title,
                            positiveButtonText,
                            cost,
                            fieldBindingValueGetter,
                            binding
                    );

                    editValueDialog.show(_activity.getFragmentManager(), "EDITABLE_VALUE_FIELD_CLICKED");
                }
            }
        });
    }

    // NOTE: Requires that province has been downloaded.
    private void _setupTutorial() {
        final Province.Race race = _session.getProvince().getRace();

        _tutorial.addView(R.id.throne_money_button, "Gold Coins\n\nThis is the amount of money your province has in its coffers.");
        _tutorial.addView(R.id.throne_food_button, "Food\n\nThis is the amount of food your province has in its stores.");
        _tutorial.addView(R.id.throne_runes_button, "Runes\n\nThis is the number of runes your wizards have at their disposal.");
        _tutorial.addView(R.id.throne_draft_target_button, "Military Draft Percent\n\nDRAG this bar left or right to change your military draft percentage.\n\nThe bright bar part of the meter represents your currently drafted percent; the dim meter segment represents the percent scheduled to be drafted.");
        _tutorial.addView(R.id.throne_soldiers_button, "Current Soldiers\n\nClicking here will open a dialog for releasing soldiers.");
        _tutorial.addView(R.id.throne_defensive_units_button, "Current Defensive Units\n\nClicking here will open a dialog for training or releasing "+ Province.getDefensiveUnitKeyword(race) +".");
        _tutorial.addView(R.id.throne_offensive_units_button, "Current Offensive Units\n\nClicking here will open a dialog for training or releasing "+ Province.getOffensiveUnitKeyword(race) +".");
        _tutorial.addView(R.id.throne_elites_button, "Current Elites\n\nClicking here will open a dialog for training or releasing "+ Province.getEliteKeyword(race) +".");
        _tutorial.addView(R.id.throne_thieves_button, "Current Thieves\n\nClicking here will open a dialog for training or releasing thieves.");
        _tutorial.addView(R.id.throne_wizards_button, "Current Wizards\n\nThis is the number of wizards currently serving within your province.");
        _tutorial.addView(R.id.throne_wage_button, "Military Wages\n\nClicking this entry will open a dialog for changing your army's wages.");
        _tutorial.addView(R.id.throne_draft_rate_button, "Draft Rate\n\nClicking this entry will open a dialog for changing your population's draft rate.");
        _tutorial.addView(R.id.throne_stealth_button, "Current Stealth\n\nThis meter displays the amount of stealth your thieves have.");
        _tutorial.addView(R.id.throne_mana_button, "Current Mana\n\nThis meter displays the amount of mana your wizards have.");
        _tutorial.addView(R.id.throne_peasants_button, "Current Peasants\n\nThis meter displays the number of peasants currently alive within your lands.\n\nThe red background represents the percent alive.\n\nClicking this meter will open a dialog displaying a more detailed view of your population.");
        // _tutorial.addView(R.id.throne_building_efficiency_button, "Building Efficiency\n\nThis meter displays your province's buildings' efficiency.");
    }

    private void _drawDraftTarget() {
        final Province province = _session.getProvince();
        final Float desiredPercent = province.getDraftTarget() / 100F;
        _drawDraftTarget(desiredPercent);
    }
    private void _drawDraftTarget(final Float desiredPercent) {
        final Province province = _session.getProvince();
        if (province == null) { return; }

        final Integer maxPopulation = Util.coalesce(province.getMaxPopulation());
        final Integer militaryPopulation = province.getMilitaryPopulation() + province.getThieves() + province.getTotalThievesInProgress();
        final Float currentPercent = (maxPopulation > 0 ? (((float) militaryPopulation) / maxPopulation) : 1.0F);
        // final Float desiredPercent = province.getDraftTarget() / 100F;
        final Float desiredCivilianPercent = 1.0F - desiredPercent;

        final Float displayedMilitaryPercent = (currentPercent > desiredPercent ? desiredPercent : currentPercent);
        final Float displayedDesiredMilitaryPercent = (desiredPercent > currentPercent ? desiredPercent - currentPercent : 0F);
        final Float displayedCivilianPercent = 1.0F - displayedDesiredMilitaryPercent - displayedMilitaryPercent;

        _setViewWeight(R.id.throne_military_percent_view, displayedMilitaryPercent);
        _setViewWeight(R.id.throne_desired_military_percent_view, displayedDesiredMilitaryPercent);
        _setViewWeight(R.id.throne_military_percent_view_void, displayedCivilianPercent);

        ((TextView) _view.findViewById(R.id.throne_draft_actual)).setText(Math.round(currentPercent * 100F) + "%");
        ((TextView) _view.findViewById(R.id.throne_draft_target)).setText(((int) (desiredPercent * 100F)) + "%");
        ((TextView) _view.findViewById(R.id.throne_draft_civilian)).setText(((int) (desiredCivilianPercent * 100F)) + "%");
    }

    private void _drawData() {
        final Province province = _session.getProvince();

        if (_view == null || province == null || ! province.isValid()) { return; }

        final UiTheme uiTheme = UiTheme.getThemeForRace(province.getRace());

        final String utopianDate = _session.getCurrentUtopiaDate();
        if (utopianDate != null) {
            ((TextView) _header.findViewById(R.id.throne_header_date)).setText(utopianDate);
        }

        // Portrait
        ((ImageView) _header.findViewById(R.id.throne_race_portrait)).setImageResource(uiTheme.getIconId());

        { // Set Colors
            final Integer primaryColor = uiTheme.getPrimaryColor();
            final Integer secondaryColor = uiTheme.getSecondaryColor();

            _header.findViewById(R.id.throne_header).setBackgroundColor(primaryColor);

            _view.findViewById(R.id.throne_resource_divider).setBackgroundColor(primaryColor);

            _view.findViewById(R.id.throne_soldiers_button).setBackgroundColor(primaryColor);
            _view.findViewById(R.id.throne_soldiers_button_background).setBackgroundColor(secondaryColor);

            _view.findViewById(R.id.throne_defensive_units_button).setBackgroundColor(primaryColor);
            _view.findViewById(R.id.throne_defensive_units_button_background).setBackgroundColor(secondaryColor);

            _view.findViewById(R.id.throne_offensive_units_button).setBackgroundColor(primaryColor);
            _view.findViewById(R.id.throne_offensive_units_button_background).setBackgroundColor(secondaryColor);

            _view.findViewById(R.id.throne_elites_button).setBackgroundColor(primaryColor);
            _view.findViewById(R.id.throne_elites_button_background).setBackgroundColor(secondaryColor);

            _view.findViewById(R.id.throne_military_divider).setBackgroundColor(primaryColor);

            _view.findViewById(R.id.throne_horses_border).setBackgroundColor(primaryColor);
            _view.findViewById(R.id.throne_horses_percent).setBackgroundColor(secondaryColor);

            _view.findViewById(R.id.throne_prisoners_border).setBackgroundColor(primaryColor);
            _view.findViewById(R.id.throne_prisoners_percent).setBackgroundColor(secondaryColor);

            _view.findViewById(R.id.throne_military_percent_view).setBackgroundColor(primaryColor);
            _view.findViewById(R.id.throne_desired_military_percent_view).setBackgroundColor(secondaryColor);

        } // End Set Colors

        ((TextView) _header.findViewById(R.id.throne_province_name)).setText(province.getName());
        ((TextView) _header.findViewById(R.id.throne_ruler_name)).setText(province.getRulerName());

        ((TextView) _view.findViewById(R.id.throne_land)).setText(_formatNumberStringForDisplay(province.getAcres()));

        ((TextView) _view.findViewById(R.id.throne_total_offense)).setText(_formatNumberStringForDisplay(province.getTotalOffenseAtHome()));
        ((TextView) _view.findViewById(R.id.throne_total_defense)).setText(_formatNumberStringForDisplay(province.getTotalDefenseAtHome()));

        String buildingEfficiencyString = "??";
        final Float buildingEfficiency = province.getBuildingEfficiency();
        if (buildingEfficiency != null) {
            buildingEfficiencyString = Integer.valueOf(Math.round(buildingEfficiency * 100)).toString() + "%";
            _setDisplayPercent(R.id.throne_building_efficiency_percent_view, R.id.throne_building_efficiency_percent_view_void, buildingEfficiency);
        }
        ((TextView) _view.findViewById(R.id.throne_building_efficiency)).setText(buildingEfficiencyString);

        ((TextView) _view.findViewById(R.id.throne_networth)).setText(_formatNumberStringForDisplay(province.getNetworth()));

        { // Peasants
            final Integer maxPeasants = Util.coalesce(province.getMaxPeasants());
            final Float peasantsPercent = (maxPeasants > 0 ? ((float) province.getPeasants()) / maxPeasants : 1.0F);
            _setDisplayPercent(R.id.throne_peasants_percent, R.id.throne_peasants_percent_void, peasantsPercent);
            ((TextView) _view.findViewById(R.id.throne_peasants)).setText(_formatNumberStringForDisplay(province.getPeasants()));
        } // End Peasants

        ((TextView) _view.findViewById(R.id.throne_food)).setText(_formatNumberStringForDisplay(province.getFood()));

        ((TextView) _view.findViewById(R.id.throne_money)).setText(_formatNumberStringForDisplay(province.getMoney()));
        ((TextView) _view.findViewById(R.id.throne_runes)).setText(_formatNumberStringForDisplay(province.getRunes()));

        final Integer stealth = province.getStealth();
        if (stealth != null) {
            _setResourceViewPercent(stealth / 100F, ResourceType.STEALTH);
        }

        final Integer mana = province.getMana();
        if (mana != null) {
            _setResourceViewPercent(mana / 100F, ResourceType.MANA);
        }

        ((TextView) _view.findViewById(R.id.throne_soldiers)).setText(_formatNumberStringForDisplay(province.getSoldiers()));
        ((TextView) _view.findViewById(R.id.throne_elites)).setText(_formatNumberStringForDisplay(province.getElites()));
        ((TextView) _view.findViewById(R.id.throne_offensive_units)).setText(_formatNumberStringForDisplay(province.getOffensiveUnits()));
        ((TextView) _view.findViewById(R.id.throne_defensive_units)).setText(_formatNumberStringForDisplay(province.getDefensiveUnits()));

        { // Horses
            final Integer maxHorses = province.getMaxHorses();
            final Float horsesPercent = (maxHorses > 0 ? ((float) province.getHorses()) / maxHorses : 1.0F);
            _setDisplayPercent(R.id.throne_horses_percent, R.id.throne_horses_percent_void, horsesPercent);
            ((TextView) _view.findViewById(R.id.throne_horses)).setText(_formatNumberStringForDisplay(province.getHorses()));
        } // End Horses

        { // Prisoners
            final Integer maxPrisoners = province.getMaxPrisoners();
            final Float prisonersPercent = (maxPrisoners > 0 ? ((float) province.getPrisoners()) / maxPrisoners : 1.0F);
            _setDisplayPercent(R.id.throne_prisoners_percent, R.id.throne_prisoners_percent_void, prisonersPercent);
            ((TextView) _view.findViewById(R.id.throne_prisoners)).setText(_formatNumberStringForDisplay(province.getPrisoners()));
        } // End Prisoners

        final DraftRate draftRate = province.getDraftRate();
        if (draftRate != null) {
            ((TextView) _view.findViewById(R.id.throne_draft_rate)).setText(draftRate.getName());
        }

        _drawDraftTarget();

        ((TextView) _view.findViewById(R.id.throne_wage)).setText(province.getMilitaryWageRate() +"%");

        ((TextView) _view.findViewById(R.id.throne_thieves)).setText(_formatNumberStringForDisplay(province.getThieves()));
        ((TextView) _view.findViewById(R.id.throne_wizards)).setText(_formatNumberStringForDisplay(province.getWizards()));

        LinearLayout deployedArmiesContainer = (LinearLayout) _view.findViewById(R.id.throne_deployed_armies);
        deployedArmiesContainer.removeAllViews();
        if (province.hasArmiesDeployed()) {
            final List<Province.DeployedArmy> deployedArmies = province.getDeployedArmies();
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

        Kingdom kingdom = _session.getKingdom(province.getKingdomIdentifier());
        if ((kingdom != null && kingdom.hasDragon())) {
            Dragon dragon = kingdom.getDragon();
            View dragonBanner = _view.findViewById(R.id.throne_dragon_banner);
            dragonBanner.setVisibility(View.VISIBLE);
            dragonBanner.setBackgroundColor(Color.parseColor(dragon.getColorString()));
            ((TextView) _view.findViewById(R.id.throne_dragon_banner_text)).setText("A " + dragon.getNamedType() + " Dragon is attacking our kingdom!");
        }
        else {
            _view.findViewById(R.id.throne_dragon_banner).setVisibility(View.GONE);
        }

        final View plagueBanner = _view.findViewById(R.id.throne_plague_banner);
        plagueBanner.setVisibility(province.hasPlague() ? View.VISIBLE : View.GONE);
    }

    protected void _bindAllInputs() {
        final Province province = _session.getProvince();

        for (Integer inputId : new Integer[]{ R.id.throne_soldiers_button }) {
            _view.findViewById(inputId).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditValueDialog editValueDialog = _createDefaultEditValueDialog(
                            "Release Soldiers",
                            "Release",
                            null,
                            new FieldBindingValueGetter() {
                                @Override
                                public Integer getCurrentValue() {
                                    return _session.getProvince().getSoldiers();
                                }

                                @Override
                                public Integer getInProgressValue() {
                                    return null;
                                }
                            },
                            new FieldBinding() {
                                @Override
                                public void onSubmit(final Integer providedValue, Boolean isExpedited) {
                                    final TrainArmyData trainArmyData = _getDefaultTrainArmyBundle();
                                    trainArmyData.setSoldierCount(-1 * Math.abs(providedValue));

                                    _showLoadingScreen();
                                    _session.trainArmy(trainArmyData, isExpedited, _defaultTrainArmyCallback);
                                }
                            }
                    );
                    editValueDialog.setOnPositiveString("Releasing");
                    editValueDialog.setExpediteText(null);
                    editValueDialog.show(_activity.getFragmentManager(), "EDITABLE_VALUE_FIELD_CLICKED");
                }
            });
        }


        _bindFieldInputs(new Integer[]{ R.id.throne_offensive_units_button }, "Train/Release Offensive Units", province.getOffenseUnitCost(), new FieldBindingValueGetter() {
            @Override
            public Integer getCurrentValue() {
                return _session.getProvince().getOffensiveUnits();
            }

            @Override
            public Integer getInProgressValue() {
                return _session.getProvince().getTotalOffensiveUnitsInProgress();
            }

            @Override
            public Integer[] getInProgress() {
                return _session.getProvince().getOffensiveUnitsInProgress();
            }
        }, new FieldBinding() {
            @Override
            public void onSubmit(final Integer providedValue, Boolean isExpedited) {
                final TrainArmyData trainArmyData = _getDefaultTrainArmyBundle();
                trainArmyData.setOffensiveUnitCount(providedValue);

                _showLoadingScreen();
                _session.trainArmy(trainArmyData, isExpedited, _defaultTrainArmyCallback);
            }
        }, null);

        _bindFieldInputs(new Integer[]{ R.id.throne_defensive_units_button }, "Train/Release Defensive Units", province.getDefenseUnitCost(), new FieldBindingValueGetter() {
            @Override
            public Integer getCurrentValue() {
                return _session.getProvince().getDefensiveUnits();
            }

            @Override
            public Integer getInProgressValue() {
                return _session.getProvince().getTotalDefensiveUnitsInProgress();
            }

            @Override
            public Integer[] getInProgress() {
                return _session.getProvince().getDefensiveUnitsInProgress();
            }
        }, new FieldBinding() {
            @Override
            public void onSubmit(Integer providedValue, Boolean isExpedited) {
                final TrainArmyData trainArmyData = _getDefaultTrainArmyBundle();
                trainArmyData.setDefensiveUnitCount(providedValue);

                _showLoadingScreen();
                _session.trainArmy(trainArmyData, isExpedited, _defaultTrainArmyCallback);
            }
        }, null);

        _bindFieldInputs(new Integer[]{ R.id.throne_thieves_button }, "Train/Release Thieves", province.getThiefCost(), new FieldBindingValueGetter() {
            @Override
            public Integer getCurrentValue() {
                return _session.getProvince().getThieves();
            }

            @Override
            public Integer getInProgressValue() {
                return _session.getProvince().getTotalThievesInProgress();
            }

            @Override
            public Integer[] getInProgress() {
                return _session.getProvince().getThievesInProgress();
            }
        }, new FieldBinding() {
            @Override
            public void onSubmit(Integer providedValue, Boolean isExpedited) {
                final TrainArmyData trainArmyData = _getDefaultTrainArmyBundle();
                trainArmyData.setThiefCount(providedValue);

                _showLoadingScreen();
                _session.trainArmy(trainArmyData, isExpedited, _defaultTrainArmyCallback);
            }
        }, null);

        _bindFieldInputs(new Integer[]{ R.id.throne_elites_button }, "Train/Release Elites", province.getEliteCost(), new FieldBindingValueGetter() {
            @Override
            public Integer getCurrentValue() {
                return _session.getProvince().getElites();
            }

            @Override
            public Integer getInProgressValue() {
                return _session.getProvince().getTotalElitesInProgress();
            }

            @Override
            public Integer[] getInProgress() {
                return _session.getProvince().getElitesInProgress();
            }

            @Override
            public Boolean getShouldDisplay() {
                // return (_session.getProvince().getRace() != Province.Race.UNDEAD);
                return true;
            }
        }, new FieldBinding() {
            @Override
            public void onSubmit(Integer providedValue, Boolean isExpedited) {
                final TrainArmyData trainArmyData = _getDefaultTrainArmyBundle();
                trainArmyData.setEliteCount(providedValue);

                _showLoadingScreen();
                _session.trainArmy(trainArmyData, isExpedited, _defaultTrainArmyCallback);
            }
        }, null);

        for (Integer inputId : new Integer[]{ R.id.throne_wage }) {
            _view.findViewById(inputId).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditValueDialog editValueDialog = _createDefaultEditValueDialog(
                            "Military Wages",
                            null,
                            null,
                            new FieldBindingValueGetter() {
                                @Override
                                public Integer getCurrentValue() {
                                    return _session.getProvince().getMilitaryWageRate();
                                }
                                @Override
                                public Integer getInProgressValue() {
                                    return null;
                                }
                            },
                            new FieldBinding() {
                                @Override
                                public void onSubmit(Integer providedValue, Boolean isExpedited) {
                                    final TrainArmyData trainArmyData = _getDefaultTrainArmyBundle();
                                    trainArmyData.setWageRate(providedValue);

                                    _showLoadingScreen();
                                    _session.trainArmy(trainArmyData, false, _defaultTrainArmyCallback);
                                }
                            }
                    );

                    editValueDialog.setOnPositiveString(null);
                    editValueDialog.setExpediteText(null);
                    editValueDialog.setValueTypeString(null);
                    editValueDialog.setCost(null);
                    editValueDialog.setContent("");
                    editValueDialog.setPositiveButtonText("Update");
                    editValueDialog.show(_activity.getFragmentManager(), "EDITABLE_VALUE_FIELD_CLICKED");
                }
            });
        }

        {   // Draft Rate Binding
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final EditOptionDialog editOptionDialog = new EditOptionDialog();
                    editOptionDialog.setActivity(_activity);
                    editOptionDialog.setTitle("Draft Rate");
                    editOptionDialog.setContent("Set draft rate:");

                    editOptionDialog.setCurrentValue(_session.getProvince().getDraftRate().getName());
                    final List<String> draftRates = new ArrayList<String>();
                    final List<DraftRate> availableDraftRates = _session.getDraftRates();
                    for (DraftRate draftRate : availableDraftRates) {
                        draftRates.add(draftRate.getName());
                    }
                    editOptionDialog.setOptions(draftRates);

                    editOptionDialog.setCallback(new EditOptionDialog.Callback() {
                        @Override
                        public void run(String newValue) {
                            final List<DraftRate> availableDraftRates = _session.getDraftRates();

                            DraftRate selectedDraftRate = null;
                            Boolean isValid = false;
                            for (DraftRate draftRate : availableDraftRates) {
                                if (draftRate.getName().equals(newValue)) {
                                    isValid = true;
                                    selectedDraftRate = draftRate;
                                    break;
                                }
                            }

                            if (! isValid) {
                                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(_activity, AlertDialog.THEME_HOLO_DARK);
                                dialogBuilder.setTitle("Invalid Value");
                                dialogBuilder.setMessage("Invalid value.");
                                dialogBuilder.setPositiveButton("Ok", null);
                                dialogBuilder.create().show();
                            }
                            else {
                                final TrainArmyData trainArmyData = _getDefaultTrainArmyBundle();
                                trainArmyData.setDraftRate(selectedDraftRate);

                                _showLoadingScreen();
                                _session.trainArmy(trainArmyData, false, _defaultTrainArmyCallback);
                            }
                        }
                    });
                    editOptionDialog.show(_activity.getFragmentManager(), "EDITABLE_VALUE_FIELD_CLICKED");
                }
            };

            _view.findViewById(R.id.throne_draft_rate).setOnClickListener(onClickListener);
        }

        {   // Draft Target Binding
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Province province = _session.getProvince();
                    Integer militaryPopulation = province.getMilitaryPopulation() + province.getThieves() + province.getTotalThievesInProgress();

                    Float currentPercent = (militaryPopulation * 100.0f / province.getMaxPopulation());
                    // Float wizardsPercent = province.getWizards() * 100.0f / province.getMaxPopulation();
                    Integer currentDesiredValue = province.getDraftTarget();

                    final FieldBinding onSubmit = new FieldBinding() {
                        @Override
                        public void onSubmit(Integer providedValue, Boolean isExpedited) {
                            final TrainArmyData trainArmyData = _getDefaultTrainArmyBundle();
                            trainArmyData.setDraftTarget(providedValue);

                            _showLoadingScreen();
                            _session.trainArmy(trainArmyData, isExpedited, _defaultTrainArmyCallback);
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(_activity);
                    LayoutInflater inflater = _activity.getLayoutInflater();
                    final View draftTargetView = inflater.inflate(R.layout.edit_draft_target_dialog, null);
                    builder.setView(draftTargetView);
                    ((TextView) draftTargetView.findViewById(R.id.edit_draft_target_current_army_value)).setText(Util.formatPercentString(currentPercent) +"%");
                    ((TextView) draftTargetView.findViewById(R.id.edit_draft_target_current_civilian_value)).setText(Util.formatPercentString(100.0F - currentPercent) + "%");
                    ((TextView) draftTargetView.findViewById(R.id.edit_draft_target_desired_value)).setText(currentDesiredValue.toString() + "%");
                    ((TextView) draftTargetView.findViewById(R.id.edit_draft_target_input)).setHint(currentDesiredValue.toString());

                    ProgressBar progressBar = (ProgressBar) draftTargetView.findViewById(R.id.edit_draft_target_progress_bar);
                    progressBar.setMax(province.getMaxPopulation());
                    progressBar.setProgress(militaryPopulation);
                    progressBar.setSecondaryProgress((int) (currentDesiredValue / 100.0f * province.getMaxPopulation()));

                    builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            String setValue = ((TextView) draftTargetView.findViewById(R.id.edit_draft_target_input)).getText().toString().replaceAll("[^0-9]", "");
                            Boolean isValid = (setValue.length() > 0);
                            Integer setValueInt = Util.parseInt(setValue);
                            if (!isValid) {
                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(_activity, AlertDialog.THEME_HOLO_DARK);
                                dialogBuilder.setTitle("Invalid Value");
                                dialogBuilder.setMessage("Invalid value.");
                                dialogBuilder.setPositiveButton("Ok", null);
                                dialogBuilder.create().show();
                            }
                            else {
                                onSubmit.onSubmit(setValueInt, null);
                            }
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    builder.create().show();
                }
            };

            _view.findViewById(R.id.throne_draft_target_button).setOnClickListener(onClickListener);
        }

        {   // Peasants Binding
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Province province = _session.getProvince();
                    final Integer militaryPopulation = province.getMilitaryPopulation();
                    final Integer defMilitaryPopulation = province.getDefensiveUnits() + province.getTotalDefensiveUnitsInProgress();
                    final Integer offMilitaryPopulation = province.getOffensiveUnits() + province.getTotalOffensiveUnitsInProgress();
                    final Integer eliteMilitaryPopulation = province.getElites() + province.getTotalElitesInProgress();
                    final Integer thievesPopulation = province.getThieves() + province.getTotalThievesInProgress();
                    final Integer wizardsPopulation = province.getWizards();
                    final Integer soldiers = province.getSoldiers();
                    final Integer peasants = province.getPeasants();
                    final Integer maxPopulation = province.getMaxPopulation();
                    final Integer dead = maxPopulation - (defMilitaryPopulation + offMilitaryPopulation + eliteMilitaryPopulation + thievesPopulation + wizardsPopulation + peasants + soldiers);

                    // final Float militaryPercent = ((militaryPopulation + thievesPopulation + wizardsPopulation) * 100.0f / maxPopulation);
                    // final Float militaryPercent = ((militaryPopulation) * 100.0f / maxPopulation);
                    final Float soldiersPercent = ((soldiers) * 100.0f / maxPopulation);
                    final Float defMilitaryPercent = ((defMilitaryPopulation) * 100.0f / maxPopulation);
                    final Float offMilitaryPercent = ((offMilitaryPopulation) * 100.0f / maxPopulation);
                    final Float eliteMilitaryPercent = ((eliteMilitaryPopulation) * 100.0f / maxPopulation);
                    final Float peasantsPercent = (peasants * 100.0f / maxPopulation);
                    final Float thievesPercent = (thievesPopulation * 100.0f / maxPopulation);
                    final Float wizardPercent = (wizardsPopulation * 100.0f / maxPopulation);
                    final Float deadPercent = dead * 100.0f / maxPopulation;

                    // Integer currentDesiredValue = province.getDraftTarget();

                    final AlertDialog.Builder builder = new AlertDialog.Builder(_activity);
                    final LayoutInflater inflater = _activity.getLayoutInflater();
                    final View draftTargetView = inflater.inflate(R.layout.view_population_dialog, null);
                    builder.setView(draftTargetView);

                    ((TextView) draftTargetView.findViewById(R.id.view_population_soldiers_value)).setText(StringUtil.formatNumberString(soldiers));
                    ((TextView) draftTargetView.findViewById(R.id.view_population_defense_army_value)).setText(StringUtil.formatNumberString(defMilitaryPopulation));
                    ((TextView) draftTargetView.findViewById(R.id.view_population_offense_army_value)).setText(StringUtil.formatNumberString(offMilitaryPopulation));
                    ((TextView) draftTargetView.findViewById(R.id.view_population_elite_army_value)).setText(StringUtil.formatNumberString(eliteMilitaryPopulation));
                    ((TextView) draftTargetView.findViewById(R.id.view_population_thieves_value)).setText(StringUtil.formatNumberString(thievesPopulation));
                    ((TextView) draftTargetView.findViewById(R.id.view_population_wizards_value)).setText(StringUtil.formatNumberString(wizardsPopulation));
                    ((TextView) draftTargetView.findViewById(R.id.view_population_peasants_value)).setText(StringUtil.formatNumberString(peasants));
                    ((TextView) draftTargetView.findViewById(R.id.view_population_dead_value)).setText(StringUtil.formatNumberString(dead));

                    ((TextView) draftTargetView.findViewById(R.id.view_population_soldiers_percent)).setText(Util.formatPercentString(soldiersPercent) + "%");
                    ((TextView) draftTargetView.findViewById(R.id.view_population_defense_army_percent)).setText(Util.formatPercentString(defMilitaryPercent) + "%");
                    ((TextView) draftTargetView.findViewById(R.id.view_population_offense_army_percent)).setText(Util.formatPercentString(offMilitaryPercent) + "%");
                    ((TextView) draftTargetView.findViewById(R.id.view_population_elite_army_percent)).setText(Util.formatPercentString(eliteMilitaryPercent) + "%");
                    ((TextView) draftTargetView.findViewById(R.id.view_population_thieves_percent)).setText(Util.formatPercentString(thievesPercent) + "%");
                    ((TextView) draftTargetView.findViewById(R.id.view_population_wizards_percent)).setText(Util.formatPercentString(wizardPercent) + "%");
                    ((TextView) draftTargetView.findViewById(R.id.view_population_peasants_percent)).setText(Util.formatPercentString(peasantsPercent) + "%");
                    ((TextView) draftTargetView.findViewById(R.id.view_population_dead_percent)).setText(Util.formatPercentString(deadPercent) + "%");

                    final LinearLayout chartLayout = (LinearLayout) draftTargetView.findViewById(R.id.view_population_chart);

                    final CategorySeries dataSeries = new CategorySeries("");
                    final DefaultRenderer chartRenderer = new DefaultRenderer();

                    chartRenderer.setClickEnabled(false);
                    chartRenderer.setZoomEnabled(false);
                    chartRenderer.setPanEnabled(false);
                    chartRenderer.setShowLegend(false);
                    chartRenderer.setLabelsTextSize(25);

                    SimpleSeriesRenderer renderer;

                    if (deadPercent > 0F) {
                        renderer = new SimpleSeriesRenderer();
                        dataSeries.add("Dead", deadPercent);
                        renderer.setColor(Color.WHITE);
                        chartRenderer.addSeriesRenderer(renderer);
                    }

                    if (defMilitaryPercent > 0F) {
                        renderer = new SimpleSeriesRenderer();
                        dataSeries.add("Defense", defMilitaryPercent);
                        renderer.setColor(((ColorDrawable) draftTargetView.findViewById(R.id.view_population_defense_army_color).getBackground()).getColor());
                        chartRenderer.addSeriesRenderer(renderer);
                    }

                    if (offMilitaryPercent > 0F) {
                        renderer = new SimpleSeriesRenderer();
                        dataSeries.add("Offense", offMilitaryPercent);
                        renderer.setColor(((ColorDrawable) draftTargetView.findViewById(R.id.view_population_offense_army_color).getBackground()).getColor());
                        chartRenderer.addSeriesRenderer(renderer);
                    }

                    if (eliteMilitaryPercent > 0F) {
                        renderer = new SimpleSeriesRenderer();
                        dataSeries.add("Elite", eliteMilitaryPercent);
                        renderer.setColor(((ColorDrawable) draftTargetView.findViewById(R.id.view_population_elite_army_color).getBackground()).getColor());
                        chartRenderer.addSeriesRenderer(renderer);
                    }

                    if (soldiersPercent > 0F) {
                        renderer = new SimpleSeriesRenderer();
                        dataSeries.add("Soldiers", soldiersPercent);
                        renderer.setColor(((ColorDrawable) draftTargetView.findViewById(R.id.view_population_soldiers_color).getBackground()).getColor());
                        chartRenderer.addSeriesRenderer(renderer);
                    }

                    if (wizardPercent > 0F) {
                        renderer = new SimpleSeriesRenderer();
                        dataSeries.add("Wizards", wizardPercent);
                        renderer.setColor(((ColorDrawable) draftTargetView.findViewById(R.id.view_population_wizards_color).getBackground()).getColor());
                        chartRenderer.addSeriesRenderer(renderer);
                    }

                    if (thievesPercent > 0F) {
                        renderer = new SimpleSeriesRenderer();
                        dataSeries.add("Thieves", thievesPercent);
                        renderer.setColor(((ColorDrawable) draftTargetView.findViewById(R.id.view_population_thieves_color).getBackground()).getColor());
                        chartRenderer.addSeriesRenderer(renderer);
                    }

                    if (peasantsPercent > 0F) {
                        renderer = new SimpleSeriesRenderer();
                        dataSeries.add("Peasants", peasantsPercent);
                        renderer.setColor(((ColorDrawable) draftTargetView.findViewById(R.id.view_population_peasants_color).getBackground()).getColor());
                        chartRenderer.addSeriesRenderer(renderer);
                    }

                    builder.setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

                    final GraphicalView chartView = ChartFactory.getPieChartView(_activity, dataSeries, chartRenderer);
                    chartLayout.removeAllViews();
                    chartLayout.addView(chartView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                    builder.create().show();
                }
            };

            _view.findViewById(R.id.throne_peasants_button).setOnClickListener(onClickListener);
        }

        _view.findViewById(R.id.throne_draft_target_button).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final Integer currentDraftTarget = _session.getProvince().getDraftTarget();

                if (motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                    _drawDraftTarget(currentDraftTarget / 100F);
                    _toggleScrolling(true);
                    return true;
                }

                final Boolean eventIsComplete = (motionEvent.getAction() == MotionEvent.ACTION_UP);
                _toggleScrolling(eventIsComplete);

                final Integer maxWidth = view.getWidth();
                final Integer xPosition = (int) motionEvent.getAxisValue(MotionEvent.AXIS_X);

                final Float newPercent = ((float) xPosition) / maxWidth;

                final Integer newPercentAsInt = (int) (newPercent * 100);
                final Boolean draftTargetIsChanged = (! newPercentAsInt.equals(currentDraftTarget));
                if (eventIsComplete && draftTargetIsChanged) {
                    final TrainArmyData trainArmyData = _getDefaultTrainArmyBundle();
                    trainArmyData.setDraftTarget(newPercentAsInt);

                    _showLoadingScreen();
                    _session.trainArmy(trainArmyData, false, _defaultTrainArmyCallback);
                }

                _drawDraftTarget(newPercent);

                return true;
            }
        });
    }

    private void _toggleScrolling(final Boolean shouldScroll) {
        final ScrollView scrollView = (ScrollView) _view.findViewById(R.id.throne_scrollview);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return !shouldScroll;
            }
        });
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.throne2, container, false);

        _header = inflater.inflate(R.layout.throne2_header, null);
        ((TextView) _header.findViewById(R.id.throne_province_name)).setTypeface(Typeface.createFromAsset(this.getActivity().getAssets(), "fonts/CinzelDecorative-Bold.ttf"));
        ((TextView) _header.findViewById(R.id.throne_ruler_name)).setTypeface(Typeface.createFromAsset(this.getActivity().getAssets(), "fonts/CinzelDecorative-Bold.ttf"));
        _header.findViewById(R.id.throne_race_portrait).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidUtil.toggleNavigationDrawer(_activity);
            }
        });

        _activity.setActionBarView(_header);

        _maxResourceHeight = _view.findViewById(R.id.throne_stealth_button).getLayoutParams().height;

        if (_session.getProvince() != null) {
            _drawData();
        }

        // _bindAllInputs();

        // NOTE: Requires _view be instantiated.
        _tutorial = new Tutorial("throne", _view, _activity);
        // _tutorial.markCompleted(false); // DEBUG: Force Tutorial

        _downloadProvinceData(new Runnable() {
            @Override
            public void run() {
                if (! _tutorial.hasBeenCompleted()) {
                    _setupTutorial();

                    Dialog.setActivity(_activity);
                    Dialog.confirm("View Tutorial", "Do you want to view the tutorial for the new throne layout?", new Runnable() {
                        @Override
                        public void run() {
                            _tutorial.start();
                        }
                    }, new Runnable() {
                        @Override
                        public void run() {
                            _tutorial.markCompleted(true);
                        }
                    });
                }
            }
        });

        return _view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        _activity.resetActionBarView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        _activity = (MainActivity) context;
        _session = Session.getInstance();

        _activity.setOnBackCallback(new Runnable() {
            @Override
            public void run() {
                _onBack();
            }
        });
    }

    @Override
    public void onDetach() {
        _activity.setOnBackCallback(null);

        _activity.getSupportActionBar().setDisplayShowCustomEnabled(false);

        _session.removeThroneCallback(THRONE_CALLBACK_IDENTIFIER);
        _session.removeMilitarySettingsCallback(MILITARY_SETTINGS_CALLBACK_IDENTIFIER);
        _session.removeDateCallback(UTOPIA_DATE_CALLBACK_IDENTIFIER);

        super.onDetach();
    }
}
