package com.softwareverde.utopia.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.softwareverde.util.Dialog;
import com.softwareverde.util.StringUtil;
import com.softwareverde.utopia.Util;
import com.softwareverde.utopia.DraftRate;
import com.softwareverde.utopia.InAppPurchases;
import com.softwareverde.utopia.Kingdom;
import com.softwareverde.utopia.Province;
import com.softwareverde.utopia.R;
import com.softwareverde.utopia.Session;
import com.softwareverde.utopia.TrainArmyData;
import com.softwareverde.utopia.ui.dialog.EditOptionDialog;
import com.softwareverde.utopia.ui.dialog.EditValueDialog;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import java.util.ArrayList;
import java.util.List;

public class InteractiveThroneFragment extends ThroneFragment {

    public static final String THRONE_CALLBACK_IDENTIFIER = "ThroneFragment_ThroneUpdateCallbackIdentifier";
    public static final String MILITARY_SETTINGS_CALLBACK_IDENTIFIER = "ThroneFragment_MilitarySettingsCallbackIdentifier";

    private interface FieldBinding {
        void onSubmit(Integer providedValue, Boolean isExpedited);
    }
    private class FieldBindingValueGetter {
        public Integer getCurrentValue() { return 0; }
        public Integer getInProgressValue() { return 0; }
        public boolean getShouldDisplay() { return true; }
    }

    EditValueDialog _createDefaultEditValueDialog(String title, String positiveButtonText, Integer cost, FieldBindingValueGetter fieldBindingValueGetter, final FieldBinding binding) {
        EditValueDialog editValueDialog = new EditValueDialog();
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
        Integer currentValue = fieldBindingValueGetter.getCurrentValue();
        if (currentValue != null) {
            currentValueString = StringUtil.formatNumberString(currentValue);
        }
        editValueDialog.setCurrentValue(currentValueString);

        String inProgressValueString = null;
        Integer inProgressValue = fieldBindingValueGetter.getInProgressValue();
        if (inProgressValue != null) {
            inProgressValueString = StringUtil.formatNumberString(inProgressValue);
        }
        editValueDialog.setInProgressValue(inProgressValueString);

        editValueDialog.setCallback(new EditValueDialog.Callback() {
            public void run(String newValue, Boolean isExpedited) {

                Integer newValueInt = Util.parseInt(newValue);
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
                    _session.downloadMilitaryCouncil(new Session.Callback() {
                        @Override
                        public void run(Session.SessionResponse response) {
                            _session.downloadMilitarySettings(new Session.Callback() {
                                @Override
                                public void run(Session.SessionResponse response) {
                                    _activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            _hideLoadingScreen();
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            });
        }
    };

    @Override
    protected void _drawData() {
        super._drawData();

        if (_view == null || _province == null || ! _province.isValid()) { return; }

        _view.findViewById(R.id.province_tab_layout).setVisibility(View.GONE);

        View backButton = _view.findViewById(R.id.province_back_button);
        backButton.setVisibility(View.GONE);

        _toggleModifierIcons(View.VISIBLE);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        _session.addThroneCallback(THRONE_CALLBACK_IDENTIFIER, new Runnable() {
            public void run() {
                _activity.runOnUiThread(new Runnable() {
                    public void run() {
                        _drawData();
                    }
                });
            }
        });
        _session.addMilitarySettingsCallback(MILITARY_SETTINGS_CALLBACK_IDENTIFIER, new Runnable() {
            @Override
            public void run() {
                _activity.runOnUiThread(new Runnable() {
                    public void run() {
                        _drawData();
                    }
                });
            }
        });

        _showLoadingScreen();
        _session.downloadKingdom(new Session.DownloadKingdomCallback() {
            @Override
            public void run(Session.SessionResponse response, Kingdom.Identifier kingdomIdentifier) {
                _session.downloadThrone(new Session.Callback() {
                    @Override
                    public void run(Session.SessionResponse response) {
                        if (response.getWasSuccess()) {
                            _session.downloadMilitaryCouncil(new Session.Callback() {
                                @Override
                                public void run(Session.SessionResponse response) {
                                    _session.downloadStateCouncil(new Session.Callback() {
                                        @Override
                                        public void run(Session.SessionResponse response) {
                                            _session.downloadMilitarySettings(new Session.Callback() {
                                                @Override
                                                public void run(Session.SessionResponse response) {
                                                    _bindAllInputs();
                                                    _hideLoadingScreen();

                                                    _session.downloadAvailableSpells(null);
                                                }
                                            });
                                        }
                                    });
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

        View backButton = _view.findViewById(R.id.province_back_button);
        backButton.setVisibility(View.GONE);
        backButton.setOnClickListener(null);

        if (! _session.getShouldHidePremiumIconPreference()) {
            final InAppPurchases.InAppPurchaseHelper inAppPurchaseHelper = new InAppPurchases.InAppPurchaseHelper(InAppPurchases.getInstance(_activity));
            inAppPurchaseHelper.downloadPurchasedItems(new Runnable() {
                @Override
                public void run() {
                    final List<String> purchasedItems = inAppPurchaseHelper.getPurchasedItems();
                    if (! purchasedItems.isEmpty()) {
                        _showPremiumIcon();
                    }
                }
            });
        }

        view.findViewById(R.id.throne_premium_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog.setActivity(_activity);
                Dialog.confirm(
                    "Hide Premium Icon",
                    "Are you sure you want to hide the premium icon?",
                    new Runnable() {
                        @Override
                        public void run() {
                            _session.setShouldHidePremiumIconPreference(true);
                            _activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    _view.findViewById(R.id.throne_premium_icon).setVisibility(View.GONE);
                                }
                            });
                        }
                    },
                    null
                );
            }
        });

        return view;
    }

    protected void _bindAllInputs() {
        for (Integer inputId : new Integer[]{ R.id.throne_soldiers, R.id.throne_soldiers_plus_minus }) {
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
                                Province province = _session.getProvince();
                                TrainArmyData trainArmyData = new TrainArmyData(province.getRace());
                                trainArmyData.setDraftRate(province.getDraftRate());
                                trainArmyData.setDraftTarget(province.getDraftTarget());
                                trainArmyData.setWageRate(province.getMilitaryWageRate());

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


        _bindFieldInputs(new Integer[]{R.id.throne_offensive_units, R.id.throne_offensive_units_plus_minus}, "Train/Release Offensive Units", _province.getOffenseUnitCost(), new FieldBindingValueGetter() {
            @Override
            public Integer getCurrentValue() {
                return _session.getProvince().getOffensiveUnits();
            }

            @Override
            public Integer getInProgressValue() {
                return _session.getProvince().getTotalOffensiveUnitsInProgress();
            }
        }, new FieldBinding() {
            @Override
            public void onSubmit(final Integer providedValue, Boolean isExpedited) {
                Province province = _session.getProvince();
                TrainArmyData trainArmyData = new TrainArmyData(province.getRace());
                trainArmyData.setDraftRate(province.getDraftRate());
                trainArmyData.setDraftTarget(province.getDraftTarget());
                trainArmyData.setWageRate(province.getMilitaryWageRate());

                trainArmyData.setOffensiveUnitCount(providedValue);

                _showLoadingScreen();
                _session.trainArmy(trainArmyData, isExpedited, _defaultTrainArmyCallback);
            }
        }, null);

        _bindFieldInputs(new Integer[]{R.id.throne_defensive_units, R.id.throne_defensive_units_plus_minus}, "Train/Release Defensive Units", _province.getDefenseUnitCost(), new FieldBindingValueGetter() {
            @Override
            public Integer getCurrentValue() {
                return _session.getProvince().getDefensiveUnits();
            }

            @Override
            public Integer getInProgressValue() {
                return _session.getProvince().getTotalDefensiveUnitsInProgress();
            }
        }, new FieldBinding() {
            @Override
            public void onSubmit(Integer providedValue, Boolean isExpedited) {
                Province province = _session.getProvince();
                TrainArmyData trainArmyData = new TrainArmyData(province.getRace());
                trainArmyData.setDraftRate(province.getDraftRate());
                trainArmyData.setDraftTarget(province.getDraftTarget());
                trainArmyData.setWageRate(province.getMilitaryWageRate());

                trainArmyData.setDefensiveUnitCount(providedValue);

                _showLoadingScreen();
                _session.trainArmy(trainArmyData, isExpedited, _defaultTrainArmyCallback);
            }
        }, null);

        _bindFieldInputs(new Integer[]{R.id.throne_thieves, R.id.throne_thieves_plus_minus}, "Train/Release Thieves", _province.getThiefCost(), new FieldBindingValueGetter() {
            @Override
            public Integer getCurrentValue() {
                return _session.getProvince().getThieves();
            }

            @Override
            public Integer getInProgressValue() {
                return _session.getProvince().getTotalThievesInProgress();
            }
        }, new FieldBinding() {
            @Override
            public void onSubmit(Integer providedValue, Boolean isExpedited) {
                Province province = _session.getProvince();
                TrainArmyData trainArmyData = new TrainArmyData(province.getRace());
                trainArmyData.setDraftRate(province.getDraftRate());
                trainArmyData.setDraftTarget(province.getDraftTarget());
                trainArmyData.setWageRate(province.getMilitaryWageRate());

                trainArmyData.setThiefCount(providedValue);

                _showLoadingScreen();
                _session.trainArmy(trainArmyData, isExpedited, _defaultTrainArmyCallback);
            }
        }, null);

        _bindFieldInputs(new Integer[]{R.id.throne_elites, R.id.throne_elites_plus_minus}, "Train/Release Elites", _province.getEliteCost(), new FieldBindingValueGetter() {
            @Override
            public Integer getCurrentValue() {
                return _session.getProvince().getElites();
            }

            @Override
            public Integer getInProgressValue() {
                return _session.getProvince().getTotalElitesInProgress();
            }

            @Override
            public boolean getShouldDisplay() {
                // return (_session.getProvince().getRace() != Province.Race.UNDEAD);
                return true;
            }
        }, new FieldBinding() {
            @Override
            public void onSubmit(Integer providedValue, Boolean isExpedited) {
                Province province = _session.getProvince();
                TrainArmyData trainArmyData = new TrainArmyData(province.getRace());
                trainArmyData.setDraftRate(province.getDraftRate());
                trainArmyData.setDraftTarget(province.getDraftTarget());
                trainArmyData.setWageRate(province.getMilitaryWageRate());

                trainArmyData.setEliteCount(providedValue);

                _showLoadingScreen();
                _session.trainArmy(trainArmyData, isExpedited, _defaultTrainArmyCallback);
            }
        }, null);

        for (Integer inputId : new Integer[]{ R.id.throne_wage, R.id.throne_wage_plus_minus }) {
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
                                Province province = _session.getProvince();
                                TrainArmyData trainArmyData = new TrainArmyData(province.getRace());
                                trainArmyData.setDraftRate(province.getDraftRate());
                                trainArmyData.setDraftTarget(province.getDraftTarget());
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
                    EditOptionDialog editOptionDialog = new EditOptionDialog();
                    editOptionDialog.setActivity(_activity);
                    editOptionDialog.setTitle("Draft Rate");
                    editOptionDialog.setContent("Set draft rate:");

                    editOptionDialog.setCurrentValue(_session.getProvince().getDraftRate().getName());
                    List<String> draftRates = new ArrayList<String>();
                    List<DraftRate> availableDraftRates = _session.getDraftRates();
                    for (DraftRate draftRate : availableDraftRates) {
                        draftRates.add(draftRate.getName());
                    }
                    editOptionDialog.setOptions(draftRates);

                    editOptionDialog.setCallback(new EditOptionDialog.Callback() {
                        @Override
                        public void run(String newValue) {
                            DraftRate selectedDraftRate = null;
                            List<DraftRate> availableDraftRates = _session.getDraftRates();
                            Boolean isValid = false;
                            for (DraftRate draftRate : availableDraftRates) {
                                if (draftRate.getName().equals(newValue)) {
                                    isValid = true;
                                    selectedDraftRate = draftRate;
                                    break;
                                }
                            }

                            if (! isValid) {
                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(_activity, AlertDialog.THEME_HOLO_DARK);
                                dialogBuilder.setTitle("Invalid Value");
                                dialogBuilder.setMessage("Invalid value.");
                                dialogBuilder.setPositiveButton("Ok", null);
                                dialogBuilder.create().show();
                            }
                            else {
                                Province province = _session.getProvince();
                                TrainArmyData trainArmyData = new TrainArmyData(province.getRace());
                                trainArmyData.setDraftRate(selectedDraftRate);
                                trainArmyData.setDraftTarget(province.getDraftTarget());
                                trainArmyData.setWageRate(province.getMilitaryWageRate());

                                _showLoadingScreen();
                                _session.trainArmy(trainArmyData, false, _defaultTrainArmyCallback);
                            }
                        }
                    });
                    editOptionDialog.show(_activity.getFragmentManager(), "EDITABLE_VALUE_FIELD_CLICKED");
                }
            };

            _view.findViewById(R.id.throne_draft_rate).setOnClickListener(onClickListener);
            _view.findViewById(R.id.throne_draft_rate_plus_minus).setOnClickListener(onClickListener);
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
                            Province province = _session.getProvince();
                            TrainArmyData trainArmyData = new TrainArmyData(province.getRace());
                            trainArmyData.setDraftRate(province.getDraftRate());
                            trainArmyData.setDraftTarget(providedValue);
                            trainArmyData.setWageRate(province.getMilitaryWageRate());

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

            _view.findViewById(R.id.throne_draft_target).setOnClickListener(onClickListener);
            _view.findViewById(R.id.throne_draft_target_plus_minus).setOnClickListener(onClickListener);
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

                    // Integer currentDesiredValue = _province.getDraftTarget();

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

            _view.findViewById(R.id.throne_peasants).setOnClickListener(onClickListener);
            _view.findViewById(R.id.throne_peasants_plus_minus).setOnClickListener(onClickListener);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _province = _session.getProvince();
    }

    @Override
    public void onDestroy() {
        _session.removeThroneCallback(THRONE_CALLBACK_IDENTIFIER);
        _session.removeMilitarySettingsCallback(MILITARY_SETTINGS_CALLBACK_IDENTIFIER);
        super.onDestroy();
    }
}
