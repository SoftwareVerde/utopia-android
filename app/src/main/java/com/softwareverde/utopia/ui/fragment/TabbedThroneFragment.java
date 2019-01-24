package com.softwareverde.utopia.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.softwareverde.utopia.Province;
import com.softwareverde.utopia.R;
import com.softwareverde.utopia.Session;

public class TabbedThroneFragment extends ThroneFragment {

    @Override
    protected void _drawData() {
        super._drawData();

        if (_view == null || _province == null || ! _province.isValid()) { return; }

        _view.findViewById(R.id.province_tab_layout).setVisibility(View.VISIBLE);
        _toggleModifierIcons(View.GONE);

        View backButton = _view.findViewById(R.id.province_back_button);
        backButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void setProvince(Province province) {
        super.setProvince(province);

        _updateAidButton();
    }

    private void _updateAidButton() {
        if (_view == null) {
            return;
        }

        ImageView aidButton = (ImageView) _view.findViewById(R.id.aid_icon);
        if (_province != null && _session != null && _session.getKingdom().getIdentifier().equals(_province.getKingdomIdentifier())) {
            aidButton.setVisibility(View.VISIBLE);
            aidButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    _showAidFragment();
                }
            });
        }
        else {
            aidButton.setVisibility(View.GONE);
            aidButton.setOnClickListener(null);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        _updateAidButton();

        View backButton = _view.findViewById(R.id.province_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _back();
            }
        });

        _view.findViewById(R.id.province_tab_thievery).setOnClickListener(new View.OnClickListener() {
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
        _view.findViewById(R.id.province_tab_magic).setOnClickListener(new View.OnClickListener() {
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
        _view.findViewById(R.id.province_tab_survey).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        _showTargetSurvey();
                    }
                });
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        _province = _session.loadProvinceFromStore(_province);

        _showLoadingScreen();
        _session.downloadProvinceIntel(_province, new Session.Callback() {
            @Override
            public void run(Session.SessionResponse response) {
                _activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        _drawData();
                        _hideLoadingScreen();
                    }
                });
            }
        });
    }

    private void _back() {
        FragmentManager fragmentManager = ((AppCompatActivity) _activity).getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        KingdomFragment kingdomFragment = new KingdomFragment();
        kingdomFragment.setKingdomIdentifier(_province.getKingdomIdentifier());
        kingdomFragment.setShouldUseCachedKingdomData(true);
        KingdomFragment fragment = kingdomFragment;

        transaction.replace(R.id.container, fragment);
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
    private void _showTargetSurvey() {
        FragmentManager fragmentManager = ((AppCompatActivity) _activity).getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);

        TabbedBuildingsCouncilFragment tabbedBuildingsCouncilFragment = new TabbedBuildingsCouncilFragment();
        tabbedBuildingsCouncilFragment.setProvince(_province);

        transaction.replace(R.id.container, tabbedBuildingsCouncilFragment);
        transaction.commit();
    }

    private void _showAidFragment() {
        FragmentManager fragmentManager = ((AppCompatActivity) _activity).getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);

        AidFragment aidFragment = new AidFragment();
        aidFragment.setProvince(_province);

        transaction.replace(R.id.container, aidFragment);
        transaction.commit();
    }
}
