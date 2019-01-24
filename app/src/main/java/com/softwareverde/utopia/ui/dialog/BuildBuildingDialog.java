package com.softwareverde.utopia.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.softwareverde.util.Util;
import com.softwareverde.utopia.R;

public class BuildBuildingDialog extends DialogFragment {
    public interface Callback {
        void run(String setValue, Boolean isConstructionExpedited, Boolean shouldUseBuildingCredits);
    }

    private View _view;

    private Activity _activity = this.getActivity();
    private Callback _callback = null;
    private String _buildingName = "";
    private Integer _totalLand = 1;
    private Integer _buildingCount = 0;
    private String _positiveButtonText = "Build";
    private String _cancelButtonText = "Cancel";
    private Integer _buildCost = null;
    private Integer _razeCost = null;
    private Integer _buildTime = null;

    private Boolean _isConstructionExpedited() {
        return ((CheckBox) _view.findViewById(R.id.set_building_build_fast)).isChecked();
    }

    private Boolean _shouldUseBuildingCredits() {
        return ((CheckBox) _view.findViewById(R.id.use_building_credits)).isChecked();
    }

    private void _updateCost() {
        final TextView actionLabel = ((TextView) _view.findViewById(R.id.set_building_action_label));
        final EditText amountInput = (EditText) _view.findViewById(R.id.set_building_amount_input);
        final TextView costLabel = (TextView) _view.findViewById(R.id.set_building_action_building_cost);

        Integer amount = Util.parseInt(amountInput.getText().toString());
        Integer deltaValue = amount - _buildingCount;

        Integer cost = null;
        if (deltaValue < 0) {
            actionLabel.setTextColor(Color.parseColor("#902020"));
            if (_razeCost != null) {
                cost = _razeCost * Math.abs(deltaValue);
            }
        }
        else {
            actionLabel.setTextColor(Color.parseColor("#FFFFFF"));
            if (_buildCost != null) {
                cost = _buildCost * deltaValue;
            }
        }

        if (cost == null) {
            costLabel.setVisibility(View.GONE);
        }
        else {
            if (_isConstructionExpedited()) {
                cost = (int) (cost * 2.0F);
            }
            costLabel.setVisibility(View.VISIBLE);
            costLabel.setText("(" + Util.formatNumberString(cost) + " gc)");
        }
    }

    private void _updateBuildTime() {
        final TextView buildTimeLabel = (TextView) _view.findViewById(R.id.set_building_build_time);
        if (_buildTime == null) {
            buildTimeLabel.setVisibility(View.GONE);
            return;
        }

        Long now = System.currentTimeMillis();
        Integer timeMinutes = 60 - ((int) ((now - Util.truncateMinutes(now)) / 1000F / 60F));

        if (_isConstructionExpedited()) {
            buildTimeLabel.setText("(Ready in " + Util.formatNumberString((_buildTime - 1) / 2) + "h "+ Util.formatNumberString(timeMinutes) +"m");
        }
        else {
            buildTimeLabel.setText("(Ready in "+ Util.formatNumberString((_buildTime - 1)) +"h "+ Util.formatNumberString(timeMinutes) +"m)");
        }
    }

    public void setActivity(Activity activity) {
        _activity = activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(_activity);

        LayoutInflater inflater = _activity.getLayoutInflater();
        final View view = inflater.inflate(R.layout.set_building_dialog, null);
        _view = view;
        builder.setView(view);

        _updateCost();
        _updateBuildTime();

        final EditText amountInput = (EditText) view.findViewById(R.id.set_building_amount_input);
        final EditText percentInput = (EditText) view.findViewById(R.id.set_building_percent_input);

        if (_buildingName != null) {
            ((TextView) view.findViewById(R.id.set_building_type_label)).setText(_buildingName);
            ((TextView) view.findViewById(R.id.set_building_action_building_type_label)).setText(_buildingName);
        }

        if (_totalLand != null && _buildingCount != null) {
            ((TextView) view.findViewById(R.id.set_building_total_building_amount)).setText(Util.formatNumberString(_buildingCount));
            ((TextView) view.findViewById(R.id.set_building_total_building_percent)).setText(Util.formatPercentString(_buildingCount / (float) _totalLand * 100.0f));

            amountInput.setText(Util.formatNumberString(_buildingCount));
            percentInput.setText(Util.formatPercentString(_buildingCount / (float) _totalLand * 100.0f));
        }

        final TextView actionLabel = ((TextView) view.findViewById(R.id.set_building_action_label));
        final TextView actionAmountLabel = ((TextView) view.findViewById(R.id.set_building_action_amount));
        amountInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                Integer amount = Util.parseInt(amountInput.getText().toString());
                Integer deltaValue = amount - _buildingCount;

                actionLabel.setText(deltaValue < 0 ? "Raze" : "Build");
                actionAmountLabel.setText(Util.formatNumberString(Math.abs(deltaValue)));

                percentInput.setText(Util.formatPercentString(amount / (float) _totalLand * 100.0f));

                _updateCost();
                _updateBuildTime();

                return false;
            }
        });
        percentInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                Float percent = Util.parseFloat(percentInput.getText().toString()) / 100.0f;
                Integer amount = (int) Math.ceil(_totalLand * percent);
                Integer deltaValue = amount - _buildingCount;

                actionLabel.setText(deltaValue < 0 ? "Raze" : "Build");
                actionAmountLabel.setText(Util.formatNumberString(Math.abs(deltaValue)));

                amountInput.setText(Util.formatNumberString(amount));

                _updateCost();
                _updateBuildTime();

                return false;
            }
        });

        ((CheckBox) _view.findViewById(R.id.set_building_build_fast)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                _updateCost();
                _updateBuildTime();
            }
        });

        builder.setPositiveButton(_positiveButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if (_callback != null) {
                    String setValue = ((TextView) view.findViewById(R.id.set_building_amount_input)).getText().toString();

                    Integer deltaValue = Util.parseInt(setValue) - _buildingCount;

                    _callback.run(deltaValue.toString(), _isConstructionExpedited(), _shouldUseBuildingCredits());
                }
            }
        });

        builder.setNegativeButton(_cancelButtonText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                BuildBuildingDialog.this.getDialog().cancel();
            }
        });

        return builder.create();
    }

    public void setBuildingName(String name) {
        _buildingName = name;
    }

    public void setBuildingCount(Integer currentValue) {
        _buildingCount = currentValue;
    }

    public void setTotalLand(Integer totalLand) { _totalLand = totalLand; }

    public void setCallback(Callback callback) {
        _callback = callback;
    }

    public void setBuildCost(Integer buildCost) { _buildCost = buildCost; }

    public void setRazeCost(Integer razeCost) { _razeCost = razeCost; }

    public void setBuildTime(Integer buildTime) { _buildTime = buildTime; }
}
