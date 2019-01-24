package com.softwareverde.utopia.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.softwareverde.utopia.R;

import java.util.ArrayList;
import java.util.List;

public class EditOptionDialog extends DialogFragment {
    public interface Callback {
        void run(String setValue);
    }

    private Activity _activity = this.getActivity();
    private Callback _callback = null;
    private String _titleText = "";
    private String _content = "";
    private String _currentValue = "";
    private List<String> _optionSet = new ArrayList<String>();
    private String _positiveButtonText = "Select";
    private String _cancelButtonText = "Cancel";

    public void setActivity(Activity activity) {
        _activity = activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(_activity);

        LayoutInflater inflater = _activity.getLayoutInflater();
        final View view = inflater.inflate(R.layout.edit_option_dialog, null);
        builder.setView(view);

        if (_titleText != null) {
            ((TextView) view.findViewById(R.id.edit_option_title)).setText(_titleText);
        }

        TextView contentView = ((TextView) view.findViewById(R.id.edit_option_content));
        if (_content != null) {
            contentView.setText(_content);
            contentView.setVisibility(View.VISIBLE);
        }
        else {
            contentView.setVisibility(View.GONE);
        }

        if (_currentValue != null) {
            ((TextView) view.findViewById(R.id.edit_option_current_value)).setText(_currentValue);
            ((TextView) view.findViewById(R.id.edit_option_selected_value)).setText(_currentValue);
        }
        else {
            ((ViewGroup) (view.findViewById(R.id.edit_option_current_value).getParent())).removeAllViews();
        }

        if (_optionSet != null) {
            ListView listView = ((ListView) view.findViewById(R.id.edit_option_list_view));
            listView.setAdapter(new ArrayAdapter<String>(_activity, R.layout.edit_option_item, R.id.edit_option_item_title, _optionSet));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View clickedView, int i, long l) {
                    String selectedItem = _optionSet.get(i);
                    ((TextView) view.findViewById(R.id.edit_option_selected_value)).setText(selectedItem);
                }
            });
        }
        else {
            View listView = view.findViewById(R.id.edit_option_list_view);
            ((ViewGroup) listView.getParent()).removeView(listView);
        }

        builder.setPositiveButton(_positiveButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if (_callback != null) {
                    String setValue = ((TextView) view.findViewById(R.id.edit_option_selected_value)).getText().toString();
                    _callback.run(setValue);
                }
            }
        });

        builder.setNegativeButton(_cancelButtonText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                EditOptionDialog.this.getDialog().cancel();
            }
        });

        return builder.create();
    }

    public void setTitle(String title) {
        _titleText = title;
    }

    public void setCurrentValue(String currentValue) {
        _currentValue = currentValue;
    }

    public void setOptions(List<String> optionSet) { _optionSet = optionSet; }

    public void setContent(String content) {
        _content = content;
    }

    public void setCallback(Callback callback) {
        _callback = callback;
    }
}
