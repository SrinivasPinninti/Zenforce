package com.evoke.zenforce.view.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;
import android.widget.TextView;

import com.evoke.zenforce.R;
import com.evoke.zenforce.model.database.beanentity.VisitBean;
import com.evoke.zenforce.model.pojo.placedetails.Result;
import com.evoke.zenforce.view.fragment.ActivitiesFragment;
import com.evoke.zenforce.view.fragment.InfoFragment;
import com.evoke.zenforce.view.fragment.VisitHistoryFragment;

import java.util.ArrayList;
import java.util.List;

public class VisitActivity extends AppCompatActivity implements
        TabLayout.OnTabSelectedListener/*, LoaderManager.LoaderCallbacks<Cursor>*/ {

    private static final String TAG = "VisitActivity";

    private static final int LOADER_ID = 10;
    private static final int UPLOAD_IMAGE_REQUEST_CODE = 1;

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Result mSelectedPlace;
    private TextView tvPlaceName;
    private TextView tvPlaceAddress;
    private Toolbar mToolbar;

    private long mVisitId;
    private String mName;
    private String mAddress;
    private String mPhone;
    private String mUrl;

    private String mLat;
    private String mLng;

    private ViewPagerAdapter mViewPagerAdapter;
//    private String mLocationId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate...");
        setContentView(R.layout.activity_visit);
        initUI();
        getDataFromIntent();

    }

    private void initUI() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
       /* mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        tvPlaceName = (TextView) findViewById(R.id.tvPlaceName);
        tvPlaceAddress = (TextView) findViewById(R.id.tvPlaceAddress);
    }



    public long getVisitId() {
        return mVisitId;
    }

    public String getPhone() {
        return mPhone;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getLat() {
        return mLat;
    }

    public String getLng() {
        return mLng;
    }



    private void getDataFromIntent() {

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            VisitBean bean = bundle.getParcelable("visitBean");
            long visitId = bean.get_ID();
            Log.d(TAG, "visitId : " +visitId);
            mVisitId = visitId;
            mName       = bean.getName();
            mAddress    = bean.getAddress();
            mPhone      = bean.getPhone();
            mUrl        = bean.getWebsite();
            mLat        = bean.getLocationLat();
            mLng        = bean.getLocationLng();

            tvPlaceName.setText(mName);
            tvPlaceAddress.setText(mAddress);

        } else {
            Log.e(TAG, "some error......");


        }


    }




    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TAG, "onStart...");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "onResume...");
        mViewPager.setOffscreenPageLimit(2);
        setupViewPager(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "onPause...");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(TAG, "onStop...");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy...");
    }



    public String getVisitName() {
        Log.v(TAG, " getVisitName : " + mName);
        return mName;
    }



    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.v(TAG, "onSaveInstanceState.....mName..." + mName);
        outState.putString("visitName", mName);
        outState.putString("visitAddress", mAddress);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.v(TAG, "onRestoreInstanceState.....mName..." + mName);
        if (savedInstanceState != null) {
            mName = savedInstanceState.getString("visitName");
            mAddress = savedInstanceState.getString("visitAddress");
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    /**
     * Sets view pager with fragments
     *
     * @param viewPager viewpager to set the fragments
     */
    private void setupViewPager(final ViewPager viewPager) {

        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        ActivitiesFragment activitiesFragment = ActivitiesFragment.newInstance();
        InfoFragment infoFragment = InfoFragment.newInstance();
        VisitHistoryFragment visitHistoryFragment = VisitHistoryFragment.newInstance();

        mViewPagerAdapter.addFrag(activitiesFragment, getString(R.string.tab_activities));
        mViewPagerAdapter.addFrag(infoFragment, getString(R.string.tab_info));
        mViewPagerAdapter.addFrag(visitHistoryFragment, getString(R.string.tab_history));
        viewPager.setAdapter(mViewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }


        });

    }


    private static class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }


        @Override
        public int getItemPosition(Object object) {
            Log.d(TAG, "getItemPosition(" + object.getClass().getSimpleName() + ")");
            return super.getItemPosition(object);
        }
    }



    @Override
    public void onBackPressed() {
        finish();
    }
}
