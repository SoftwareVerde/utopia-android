package com.softwareverde.utopia.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softwareverde.utopia.R;

public class TabbedBuildingsCouncilFragment extends BuildingsCouncilFragment {
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        View backButton = _view.findViewById(R.id.building_back_button);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _back();
            }
        });

        _view.findViewById(R.id.building_province_name).setVisibility(View.VISIBLE);
        _view.findViewById(R.id.building_province_intel).setVisibility(View.GONE);

        _view.findViewById(R.id.building_tab_province).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        _showTargetProvince();
                    }
                });
            }
        });
        _view.findViewById(R.id.building_tab_thievery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        _showTargetThievery();
                    }
                });
            }
        });
        _view.findViewById(R.id.building_tab_magic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        _showTargetMagic();
                    }
                });
            }
        });

        _view.findViewById(R.id.building_tab_layout).setVisibility(View.VISIBLE);

        return view;
    }

    @Override
    protected void _drawData() {
        super._drawData();

        if (_province == null) { return; }
        if (_view == null) { return; }

        ((TextView) _view.findViewById(R.id.building_province_name)).setText(_province.getName());
    }

    private void _back() {
        FragmentManager fragmentManager = ((AppCompatActivity) _activity).getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        KingdomFragment kingdomFragment = new KingdomFragment();
        kingdomFragment.setKingdomIdentifier(_province.getKingdomIdentifier());
        KingdomFragment fragment = kingdomFragment;

        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    private void _showTargetProvince() {
        FragmentManager fragmentManager = ((AppCompatActivity) _activity).getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);

        TabbedThroneFragment throneFragment = new TabbedThroneFragment();
        throneFragment.setProvince(_province);

        transaction.replace(R.id.container, throneFragment);
        transaction.commit();
    }
    private void _showTargetThievery() {
        FragmentManager fragmentManager = ((AppCompatActivity) _activity).getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);

        ThieveryOpsFragment thieveryOpsFragment = new ThieveryOpsFragment();
        thieveryOpsFragment.setTargetProvince(_province);

        transaction.replace(R.id.container, thieveryOpsFragment);
        transaction.commit();
    }
    private void _showTargetMagic() {
        FragmentManager fragmentManager = ((AppCompatActivity) _activity).getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);

        OffensiveSpellListFragment offensiveSpellListFragment = new OffensiveSpellListFragment();
        offensiveSpellListFragment.setTargetProvince(_province);

        transaction.replace(R.id.container, offensiveSpellListFragment);
        transaction.commit();
    }
}
