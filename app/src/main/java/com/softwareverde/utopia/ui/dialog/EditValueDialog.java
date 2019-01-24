package com.softwareverde.utopia.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.softwareverde.util.Util;
import com.softwareverde.utopia.R;
import com.softwareverde.utopia.ui.widget.InProgressWidget;

public class EditValueDialog extends DialogFragment {
    public interface Callback {
        void run(String setValue, Boolean isExpedited);
    }

    private Activity _activity;
    private View _view;
    private Callback _callback = null;
    private String _titleText = "";
    private String _content = null;
    private String _currentValue = "";
    private String _inProgressValue = "";
    private Integer[] _inProgressValues = null;
    private String _positiveButtonText = "Train";
    private String _cancelButtonText = "Cancel";
    private String _valueTypeString = null;
    private Integer _cost = null;
    private Integer _time = null;
    private String _expediteText = null;
    private InProgressWidget _inProgressWidget = null;

    private String _onNegativeString = null;
    private String _onPositiveString = null;

    private Boolean _isExpedited() {
        return ((CheckBox) _view.findViewById(R.id.edit_value_expedite)).isChecked();
    }

    public void setActivity(Activity activity) {
        _activity = activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(_activity);

        final LayoutInflater inflater = _activity.getLayoutInflater();
        _view = inflater.inflate(R.layout.edit_value_dialog, null);
        builder.setView(_view);

        _drawData();

        if (_titleText != null) {
            ((TextView) _view.findViewById(R.id.edit_value_title)).setText(_titleText);
        }

        TextView contentView = ((TextView) _view.findViewById(R.id.edit_value_content));
        if (_content != null) {
            contentView.setText(_content);
            contentView.setVisibility(View.VISIBLE);
        }
        else {
            contentView.setVisibility(View.GONE);
        }

        if (_currentValue != null) {
            ((EditText) _view.findViewById(R.id.edit_value_input)).setHint("Currently: "+ _currentValue);
            ((TextView) _view.findViewById(R.id.edit_value_current_value)).setText(_currentValue);
        }
        else {
            ((ViewGroup) (_view.findViewById(R.id.edit_value_current_value).getParent())).removeAllViews();
        }

        if (_inProgressValue != null) {
            ((TextView) _view.findViewById(R.id.edit_value_in_progress_value)).setText(_inProgressValue);
        }
        else {
            ((ViewGroup) (_view.findViewById(R.id.edit_value_in_progress_value).getParent())).removeAllViews();
        }

        if (_inProgressValues != null) {
            _inProgressWidget = InProgressWidget.newInstance(_activity.getLayoutInflater(), (ViewGroup) _view.findViewById(R.id.edit_value_progress_container));
            _inProgressWidget.setInProgress(_inProgressValues);
        }

        if (_currentValue != null && _inProgressValue != null) {
            ((TextView) _view.findViewById(R.id.edit_value_total_value)).setText(Util.formatNumberString((Integer) (Util.parseInt(_inProgressValue) + Util.parseInt(_currentValue))));
        }
        else {
            ((ViewGroup) (_view.findViewById(R.id.edit_value_total_value).getParent())).removeAllViews();
        }

        if (_valueTypeString != null) {
            ((TextView) _view.findViewById(R.id.edit_value_type_label)).setText(_valueTypeString);
        }

        if (_cost == null) {
            _view.findViewById(R.id.edit_value_cost).setVisibility(View.GONE);
        }
        else {
            _view.findViewById(R.id.edit_value_cost).setVisibility(View.VISIBLE);
        }

        ((CheckBox) this._view.findViewById(R.id.edit_value_expedite)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                _drawData();
            }
        });

        final EditText editText = ((EditText) _view.findViewById(R.id.edit_value_input));
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                _drawData();
            }
        });

        builder.setPositiveButton(_positiveButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if (_callback != null) {
                    String setValue = ((EditText) _view.findViewById(R.id.edit_value_input)).getText().toString();
                    _callback.run(setValue, _isExpedited());
                }
            }
        });

        builder.setNegativeButton(_cancelButtonText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                EditValueDialog.this.getDialog().cancel();
            }
        });

        return builder.create();
    }

    private void _drawData() {
        final EditText editText = ((EditText) _view.findViewById(R.id.edit_value_input));
        final TextView actionAmountView = ((TextView) _view.findViewById(R.id.edit_value_action_amount));
        final TextView positiveNegativeStringView = ((TextView) _view.findViewById(R.id.edit_value_positive_negative_label));
        final TextView costView = (TextView) _view.findViewById(R.id.edit_value_cost);

        CheckBox checkBox = ((CheckBox) _view.findViewById(R.id.edit_value_expedite));
        if (_expediteText == null) {
            checkBox.setVisibility(View.GONE);
        }
        else {
            checkBox.setText(_expediteText);
            checkBox.setVisibility(View.VISIBLE);
        }

        Integer value = Util.parseInt(editText.getText().toString());

        Integer totalCost = null;
        if (_cost != null) {
            totalCost = _cost * Math.abs(value);

            if (_isExpedited()) {
                totalCost *= 2;
            }
        }

        if ((value < 0)) {
            if (_onNegativeString != null) {
                positiveNegativeStringView.setText(_onNegativeString);
                positiveNegativeStringView.setVisibility(View.VISIBLE);
            }
            else {
                positiveNegativeStringView.setVisibility(View.GONE);
            }

            if (_cost != null) {
                costView.setVisibility(View.VISIBLE);
                costView.setText("("+ Util.formatNumberString(totalCost) +" gc)");
            }
        }
        else {
            if (_onPositiveString != null) {
                positiveNegativeStringView.setText(_onPositiveString);
                positiveNegativeStringView.setVisibility(View.VISIBLE);
            }
            else {
                positiveNegativeStringView.setVisibility(View.GONE);
            }

            if (_cost != null) {
                costView.setVisibility(View.VISIBLE);
                costView.setText("("+ Util.formatNumberString(totalCost) +" gc)");
            }
        }

        TextView timeView = (TextView) _view.findViewById(R.id.edit_value_time);
        if (_time != null) {
            timeView.setVisibility(View.VISIBLE);
            Integer hours = _time - 1;
            Integer minutes = 0;

            if (_isExpedited()) {
                hours = hours / 2;
            }

            timeView.setText("(Ready in "+ hours +"h "+ minutes +"m)");
        }
        else {
            timeView.setVisibility(View.GONE);
        }

        if (_valueTypeString == null || (_onNegativeString == null && _onPositiveString == null)) {
            _view.findViewById(R.id.edit_value_action_amount).setVisibility(View.GONE);
            _view.findViewById(R.id.edit_value_type_label).setVisibility(View.GONE);
        }
        else {
            _view.findViewById(R.id.edit_value_action_amount).setVisibility(View.VISIBLE);
            _view.findViewById(R.id.edit_value_type_label).setVisibility(View.VISIBLE);
        }

        if (_valueTypeString != null) {
            actionAmountView.setText(Util.formatNumberString(Math.abs(value)));
            actionAmountView.setVisibility(View.VISIBLE);
        }
        else {
            actionAmountView.setVisibility(View.GONE);
        }
    }

    public void setTitle(String title) {
        _titleText = title;
    }

    public void setCurrentValue(String currentValue) {
        _currentValue = currentValue;
    }

    public void setInProgressValue(String inProgressValue) { _inProgressValue = inProgressValue; }
    public void setInProgress(final Integer[] inProgress) {
        _inProgressValues = inProgress;
    }

    public void setContent(String content) {
        _content = content;
    }

    public void setCallback(Callback callback) {
        _callback = callback;
    }

    public void setOnNegativeString(String onNegativeString) {
        _onNegativeString = onNegativeString;
    }

    public void setOnPositiveString(String onPositiveString) {
        _onPositiveString = onPositiveString;
    }

    public void setValueTypeString(String valueType) {
        _valueTypeString = valueType;
    }

    public void setPositiveButtonText(String positiveButtonText) {
        _positiveButtonText = positiveButtonText;
    }

    public void setCost(Integer cost) {
        _cost = cost;
    }

    public void setTime(Integer time) {
        _time = time;
    }

    public void setExpediteText(String expediteText) { _expediteText = expediteText; }
}
