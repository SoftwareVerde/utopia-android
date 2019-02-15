package com.softwareverde.utopia.ui.fragment;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.softwareverde.utopia.R;
import com.softwareverde.utopia.Session;
import com.softwareverde.utopia.ui.adapter.DrawerItem;
import com.softwareverde.utopia.ui.adapter.DrawerItemAdapter;

public class NavigationDrawerFragment extends Fragment {
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
    private NavigationDrawerCallbacks _callbacks;
    private ActionBarDrawerToggle _drawerToggle;
    private DrawerLayout _drawerLayout;
    private ListView _drawerListView;
    private View _fragmentContainerView;

    private int _currentSelectedPosition = 0;
    private Session _session;
    private Activity _activity;

    private DrawerItemAdapter _loggedInDrawerItemAdapter;
    private DrawerItemAdapter _loggedOutDrawerItemAdapter;

    public static final int LOGIN_TAB_ID = 0;
    public static final int THRONE_TAB_ID = 1;
    public static final int NEWS_TAB_ID = 2;
    public static final int BUILDINGS_TAB_ID = 3;
    public static final int CHAT_TAB_ID = 4;
    public static final int SPELLS_TAB_ID = 5;
    public static final int KINGDOM_TAB_ID = 6;
    public static final int INTEL_SETTINGS_TAB_ID = 7;
    public static final int DRAGON_TAB_ID = 8;
    public static final int EXPLORE_TAB_ID = 9;
    public static final int SCIENCE_TAB_ID = 10;
    public static final int FORUM_TAB_ID = 11;

    public static final String LOGIN_CALLBACK_IDENTIFIER = "NavigationDrawerLoginCallback";
    public static final String LOGOUT_CALLBACK_IDENTIFIER = "NavigationDrawerLogoutCallback";

    public NavigationDrawerFragment() { }

    private void _setAdapter() {
        if (_session.isLoggedIn()) {
            _drawerListView.setAdapter(_loggedInDrawerItemAdapter);
        }
        else {
            _drawerListView.setAdapter(_loggedOutDrawerItemAdapter);
        }
    }

    private void _selectItem(int position) {
        _currentSelectedPosition = position;

        if (_drawerListView != null) {
            _drawerListView.setItemChecked(position, true);
        }

        if (_drawerLayout != null) {
            _drawerLayout.closeDrawer(_fragmentContainerView);
        }

        if (_callbacks != null) {
            _callbacks.onNavigationDrawerItemSelected((int) _drawerListView.getAdapter().getItemId(position));
        }
    }

    private ActionBar _getActionBar() {
        return ((AppCompatActivity) _activity).getSupportActionBar();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            _currentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
        }

        _loggedInDrawerItemAdapter = new DrawerItemAdapter(_activity);
        _loggedOutDrawerItemAdapter = new DrawerItemAdapter(_activity);
        _loggedOutDrawerItemAdapter.add(new DrawerItem(LOGIN_TAB_ID, "Login"));
        _loggedInDrawerItemAdapter.add(new DrawerItem(THRONE_TAB_ID, "Throne"));
        _loggedInDrawerItemAdapter.add(new DrawerItem(SPELLS_TAB_ID, "Spells"));
        _loggedInDrawerItemAdapter.add(new DrawerItem(NEWS_TAB_ID, "News"));
        _loggedInDrawerItemAdapter.add(new DrawerItem(BUILDINGS_TAB_ID, "Buildings"));
        _loggedInDrawerItemAdapter.add(new DrawerItem(SCIENCE_TAB_ID, "Science"));
        _loggedInDrawerItemAdapter.add(new DrawerItem(KINGDOM_TAB_ID, "Kingdom"));
        _loggedInDrawerItemAdapter.add(new DrawerItem(CHAT_TAB_ID, "Messages"));
        _loggedInDrawerItemAdapter.add(new DrawerItem(FORUM_TAB_ID, "Forum"));
        _loggedInDrawerItemAdapter.add(new DrawerItem(EXPLORE_TAB_ID, "Explore"));
        _loggedInDrawerItemAdapter.add(new DrawerItem(DRAGON_TAB_ID, "Dragon"));
        _loggedInDrawerItemAdapter.add(new DrawerItem(INTEL_SETTINGS_TAB_ID, "Intel Sync"));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _drawerListView = (ListView) inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        _drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _selectItem(position);
            }
        });
        if (_session.isLoggedIn()) {
            _drawerListView.setAdapter(_loggedInDrawerItemAdapter);
        }
        else {
            _drawerListView.setAdapter(_loggedOutDrawerItemAdapter);
        }

        _drawerListView.setItemChecked(_currentSelectedPosition, true);
        return _drawerListView;
    }

    public boolean isDrawerOpen() {
        return _drawerLayout != null && _drawerLayout.isDrawerOpen(_fragmentContainerView);
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        _fragmentContainerView = _activity.findViewById(fragmentId);
        _drawerLayout = drawerLayout;

        _drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        final ActionBar actionBar = _getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        _drawerToggle = new ActionBarDrawerToggle(_activity, _drawerLayout, R.drawable.ic_drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (! isAdded()) {
                    return;
                }

                ((AppCompatActivity) _activity).supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (! isAdded()) {
                    return;
                }

                ((AppCompatActivity) _activity).supportInvalidateOptionsMenu();
            }
        };

        _drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                _drawerToggle.syncState();
            }
        });

        _drawerLayout.setDrawerListener(_drawerToggle);

        _setAdapter();
        _selectItem(_currentSelectedPosition);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _activity = activity;
        _session = Session.getInstance();

        try {
            _callbacks = (NavigationDrawerCallbacks) activity;
        }
        catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }

        _session.addLoginCallback(LOGIN_CALLBACK_IDENTIFIER, new Runnable() {
            public void run() {
                _activity.runOnUiThread(new Runnable() {
                    public void run() {
                        _setAdapter();
                    }
                });
            }
        });

        _session.addLogoutCallback(LOGOUT_CALLBACK_IDENTIFIER, new Runnable() {
            @Override
            public void run() {
                _activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        _setAdapter();
                    }
                });
            }
        });
    }

    @Override
    public void onDetach() {
        _session.removeLoginCallback(LOGIN_CALLBACK_IDENTIFIER);
        _session.removeLogoutCallback(LOGOUT_CALLBACK_IDENTIFIER);

        _callbacks = null;

        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(STATE_SELECTED_POSITION, _currentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        _drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (_drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public interface NavigationDrawerCallbacks {
        void onNavigationDrawerItemSelected(int position);
    }
}
