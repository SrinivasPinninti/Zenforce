package com.evoke.zenforce.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import com.evoke.zenforce.R;
import com.evoke.zenforce.model.adapter.NavigationDrawerAdapter;
import com.evoke.zenforce.model.pojo.NavigationItem;
import com.evoke.zenforce.view.fragment.HistoryFragment;
import com.evoke.zenforce.view.fragment.VisitedPlacesFragment;
import com.evoke.zenforce.view.fragment.ScheduleFragment;
import com.evoke.zenforce.view.fragment.TodayFragment;

import java.util.ArrayList;
import java.util.List;

public class LaunchActivity extends AppCompatActivity implements NavigationDrawerAdapter.NavigationOnClickListener {

    private static final String TAG = "LaunchActivity";

    private static final String NAV_TODAY = "Today";
    private static final String NAV_MYPlACES = "My Places";
    private static final String NAV_HISTORY = "History";
    private static final String NAV_SCHEDULE = "Schedule";


    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private ListView mListView;
    private NavigationDrawerAdapter mAdapter;
    private ArrayList<NavigationItem> mNavList;

    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        initUI();
    }

    private void initUI() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getItemColor(R.color.launch_primary)));
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();
        mListView  = (ListView) findViewById(R.id.listView);
        mAdapter = new NavigationDrawerAdapter(this, getNavigationItems(), this);
        mListView.setAdapter(mAdapter);

    }


    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private List<NavigationItem> getNavigationItems() {
        mNavList = new ArrayList<NavigationItem>() {{
            add(new NavigationItem(getItemColor(R.color.red), R.drawable.today_black_24dp, getString(R.string.today), getItemColor(R.color.gray)));
            add(new NavigationItem(getItemColor(R.color.launch_primary), R.drawable.ic_location_city_black_24dp, getString(R.string.my_places), getItemColor(R.color.gray)));
            add(new NavigationItem(getItemColor(R.color.orange), R.drawable.history_black_24dp, getString(R.string.history), getItemColor(R.color.gray)));
            add(new NavigationItem(getItemColor(R.color.blue), R.drawable.schedule_black_24dp, getString(R.string.schedule), getItemColor(R.color.gray)));
        }};

        return mNavList;

    }

    private int getItemColor(int color) {
        return  ContextCompat.getColor(this, color);
    }

   /* @Override
    public void onClick(View v) {

        NavigationItem item = (NavigationItem) v.getTag();
        String navItem = item.getName();
        Log.v(TAG, "onClick...from tag " + navItem);

        Fragment fragment;

        switch (navItem) {
            case NAV_TODAY:
                Log.v(TAG, " Loading Today...");
                fragment = TodayFragment.newInstance();
                break;
            case NAV_MYPlACES:
                Log.v(TAG, " Loading My Places...");
                fragment = VisitedPlacesFragment.newInstance();
                break;
            case NAV_HISTORY:
                Log.v(TAG, " Loading History...");
                fragment = HistoryFragment.newInstance();
                break;
            case NAV_SCHEDULE:
                Log.v(TAG, " Loading Schedule...");
                fragment = ScheduleFragment.newInstance();
                break;
            default:
                fragment = TodayFragment.newInstance();
                break;
        }


        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();

        getSupportActionBar().setTitle(item.getName());
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(item.getColor()));
        mDrawer.closeDrawers();
    }*/

    @Override
    public void onNavigationItemClick(NavigationItem item) {

        String navItem = item.getName();
        Log.v(TAG, "onClick...from tag " + navItem);

        Fragment fragment;

        switch (navItem) {
            case NAV_TODAY:
                Log.v(TAG, " Loading Today...");
                fragment = TodayFragment.newInstance();
                break;
            case NAV_MYPlACES:
                Log.v(TAG, " Loading My Places...");
                fragment = VisitedPlacesFragment.newInstance();
                break;
            case NAV_HISTORY:
                Log.v(TAG, " Loading History...");
                fragment = HistoryFragment.newInstance();
                break;
            case NAV_SCHEDULE:
                Log.v(TAG, " Loading Schedule...");
                fragment = ScheduleFragment.newInstance();
                break;
            default:
                fragment = TodayFragment.newInstance();
                break;
        }


        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().remove(fragment);
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();

        getSupportActionBar().setTitle(item.getName());
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(item.getColor()));
        mDrawer.closeDrawers();
    }
}
