/*
package com.evoke.zenforce.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.evoke.zenforce.R;
import com.evoke.zenforce.model.adapter.NavigationDrawerAdapter;
import com.evoke.zenforce.model.pojo.NavigationItem;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "BaseActivity";

    private static final String NAV_TODAY = "Today";
    private static final String NAV_MYPlACES = "My Places";
    private static final String NAV_HISTORY = "History";
    private static final String NAV_SCHEDULE = "Schedule";
    private Context mContext;

    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = BaseActivity.this;
    }

    @Override
    public void setContentView(int layoutResID) {
        Log.v(TAG, "layoutResID : " + layoutResID);
        super.setContentView(layoutResID);
        initToolbar(layoutResID);
    }

    protected abstract Boolean showNavigationDrawer();

    private void initToolbar(int layoutResID) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        Log.v(TAG, "initToolbar mToolbar : " + mToolbar);
        if (mToolbar == null) {
            RelativeLayout fullView = (RelativeLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
            FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.activity_container);
            getLayoutInflater().inflate(layoutResID, activityContainer, true);
            super.setContentView(fullView);
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
        }
        setSupportActionBar(mToolbar);
    }

    private void setUpNavigation() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Log.v(TAG, "setUpNavigation  mDrawer : " + mDrawer);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Log.v(TAG, "onPostCreate....");
        if (showNavigationDrawer()) {
            setUpNavigation();
            mDrawerToggle.syncState();
        }
    }

    @Override
    public void onClick(View v) {
        NavigationItem item = (NavigationItem) v.getTag();
        Log.v(TAG, "onClick...from tag " + item.getName());
        if (mDrawer != null) {
            mDrawer.closeDrawers();
        }
        String navItem = item.getName();
        switch (navItem) {
            case NAV_TODAY:
                Log.v(TAG, " Loading Today...");
                break;
            case NAV_MYPlACES:
                Log.v(TAG, " Loading My Places...");
                Intent myPlacesIntent = new Intent(mContext, MyPlacesActivity.class);
                myPlacesIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(myPlacesIntent);
                break;
            case NAV_HISTORY:
                Log.v(TAG, " Loading History...");
                break;
            case NAV_SCHEDULE:
                Log.v(TAG, " Loading Schedule...");
                break;
        }
        mToolbar.setTitle(item.getName());
        mToolbar.setBackground(new ColorDrawable(item.getColor()));


    }
}
*/
