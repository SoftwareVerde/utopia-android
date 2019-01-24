package com.softwareverde.utopia.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.softwareverde.util.Util;
import com.softwareverde.utopia.R;

public class CastSpellDialog extends DialogFragment {
    public interface Callback {
        void run();
    }

    private Activity _activity = this.getActivity();
    private Callback _callback = null;
    private String _titleText = "";
    private String _content = "";
    private Integer _currentRuneCount = 0;
    private Integer _runeCost = 0;
    private String _positiveButtonText = "Cast";
    private String _cancelButtonText = "Cancel";

    public void setActivity(Activity activity) {
        _activity = activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(_activity);

        LayoutInflater inflater = _activity.getLayoutInflater();
        final View view = inflater.inflate(R.layout.cast_spell_dialog, null);
        builder.setView(view);

        if (_titleText != null) {
            ((TextView) view.findViewById(R.id.cast_spell_title)).setText(_titleText);
        }
        if (_content != null) {
            ((TextView) view.findViewById(R.id.cast_spell_content)).setText(_content);
        }
        if (_currentRuneCount != null) {
            ((TextView) view.findViewById(R.id.cast_spell_current_runes)).setText(Util.formatNumberString(_currentRuneCount));
        }
        if (_runeCost != null) {
            ((TextView) view.findViewById(R.id.cast_spell_rune_cost)).setText(Util.formatNumberString(_runeCost));
        }

        if (_currentRuneCount != null && _runeCost != null) {
            ((TextView) view.findViewById(R.id.cast_spell_runes_remaining)).setText(Util.formatNumberString(_currentRuneCount - _runeCost));
        }

        builder.setPositiveButton(_positiveButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if (_callback != null) {
                    _callback.run();
                }
            }
        });

        builder.setNegativeButton(_cancelButtonText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                CastSpellDialog.this.getDialog().cancel();
            }
        });

        return builder.create();
    }

    public void setTitle(String title) {
        _titleText = title;
    }

    public void setCurrentRuneCount(Integer currentValue) {
        _currentRuneCount = currentValue;
    }

    public void setRuneCost(Integer runeCost) { _runeCost = runeCost; }

    public void setContent(String content) {
        _content = content;
    }

    public void setCallback(Callback callback) {
        _callback = callback;
    }
}
