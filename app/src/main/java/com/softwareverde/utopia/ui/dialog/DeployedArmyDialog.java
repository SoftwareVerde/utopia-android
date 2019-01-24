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
import com.softwareverde.utopia.Province;
import com.softwareverde.utopia.R;

public class DeployedArmyDialog extends DialogFragment {
    public interface Callback {
        void run(String setValue);
    }

    private Activity _activity = this.getActivity();
    private String _dismissButtonText = "Ok";
    private Province.DeployedArmy _deployedArmy;

    private String _formatOptionalNumberString(Integer number) {
        if (number == null) {
            return "??";
        }

        return Util.formatNumberString(number);
    }

    public void setActivity(Activity activity) {
        _activity = activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(_activity);

        LayoutInflater inflater = _activity.getLayoutInflater();
        final View view = inflater.inflate(R.layout.deployed_army_details, null);
        builder.setView(view);

        if (_deployedArmy != null) {
            Integer returnTime = _deployedArmy.getReturnTime();
            Integer hours = (int) (returnTime / 60.0f / 60.0f);
            Integer minutes = (int) (((returnTime / 60.0f / 60.0f) - hours) * 60.0f);
            ((TextView) view.findViewById(R.id.deployed_army_details_return_time_value)).setText(String.format("%dh %dm", hours, minutes));

            ((TextView) view.findViewById(R.id.deployed_army_details_generals_value)).setText(_formatOptionalNumberString(_deployedArmy.getGenerals()));
            ((TextView) view.findViewById(R.id.deployed_army_details_acres_value)).setText(_formatOptionalNumberString(_deployedArmy.getAcres()));
            ((TextView) view.findViewById(R.id.deployed_army_details_soldiers_value)).setText(_formatOptionalNumberString(_deployedArmy.getSoldiers()));
            ((TextView) view.findViewById(R.id.deployed_army_details_offensive_units_value)).setText(_formatOptionalNumberString(_deployedArmy.getOffensiveUnits()));
            ((TextView) view.findViewById(R.id.deployed_army_details_elites_value)).setText(_formatOptionalNumberString(_deployedArmy.getElites()));
            ((TextView) view.findViewById(R.id.deployed_army_details_horses_value)).setText(_formatOptionalNumberString(_deployedArmy.getHorses()));
        }

        builder.setPositiveButton(_dismissButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) { }
        });

        return builder.create();
    }

    public void setDeployedArmy(Province.DeployedArmy deployedArmy) {
        _deployedArmy = deployedArmy;
    }

}
