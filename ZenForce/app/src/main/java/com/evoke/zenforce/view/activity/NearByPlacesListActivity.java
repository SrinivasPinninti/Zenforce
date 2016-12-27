package com.evoke.zenforce.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.evoke.zenforce.R;
import com.evoke.zenforce.utility.Util;
import com.evoke.zenforce.view.application.ZenForceApplication;
import com.evoke.zenforce.model.adapter.NewPlacesListAdapter;
import com.evoke.zenforce.model.pojo.nearby.Result;
import com.evoke.zenforce.model.pojo.placedetails.Place;
import com.evoke.zenforce.presenter.NearbyPresenter;
import com.evoke.zenforce.presenter.PlaceDetailsPresenter;

import java.util.List;

public class NearByPlacesListActivity extends AppCompatActivity implements NewPlacesListAdapter.OnItemClickListener {

    private static final String TAG = "NearByPlaces";

    private RecyclerView mRecyclerView;
    private NewPlacesListAdapter mNewPlacesListAdapter;
    private ProgressDialog mProgressDialog;
    private Toolbar mToolbar;

    private Context mContext;

    private ZenForceApplication mApplication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newplaces_list);
        mContext = this;
        mApplication = ZenForceApplication.getInstance();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Near by places");
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Util.getItemColor(this, R.color.launch_primary)));
        initUI();
        fetchNearbyPlaces();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void fetchNearbyPlaces() {

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            Log.e(TAG, " bundle is null...");
            return;
        }
        Double lat = bundle.getDouble("LAT");
        Double lng = bundle.getDouble("LNG");
        String latLng = lat +", "+lng;
        Log.v(TAG, " latLng  : " +latLng);
        if (latLng != null) {
            Log.v(TAG, " isNetworkConnected  : " +Util.isNetworkConnected());
            if (!Util.isNetworkConnected()) {
                showSnackBar("No NetWork Connection");
                return;
            }

            Log.v(TAG, "start fetching nearby places.....");
            showProgressDialog();

            new NearbyPresenter().startFetchingNearbyPlaces(latLng, new NearbyPresenter.CallbackListener() {

                @Override
                public void onSuccess(List<Result> resultList) {
                    Log.v(TAG, " onFetchComplete : " + resultList.size());
                    dismissProgress();
                    mNewPlacesListAdapter = new NewPlacesListAdapter(NearByPlacesListActivity.this, resultList, NearByPlacesListActivity.this);
                    mRecyclerView.setAdapter(mNewPlacesListAdapter);
                }

                @Override
                public void onFail(String errorMsg) {
                    Log.v(TAG, "onFetchFail......");
                    dismissProgress();
                    showSnackBar(errorMsg);
                }
            });
        } else  {
            Log.e(TAG, " error in receiving LatLng..");
            showSnackBar(" error in receiving LatLng..");
        }
    }



    private void initUI() {
        mRecyclerView = (RecyclerView) findViewById(R.id.list_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ZenForceApplication.getInstance());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
    }



    private void showSnackBar(String errorMsg) {
        if (errorMsg != null) {
            Snackbar.make(findViewById(android.R.id.content), errorMsg, Snackbar.LENGTH_LONG)
                    .setAction(R.string.close, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    })
                    .setActionTextColor(Color.RED)
                    .show();
        }
    }

    @Override
    public void onItemClick(Result result) {  // TODO : Launch SelectedPlaceMapsActivity from Adapter
        Log.v(TAG, "onItemClick show confirmation dialog..." );
//        showConfirmationDialog(result);
        String placeId = result.getPlaceId();
        Log.v(TAG, "placeId : " + placeId);
        if (placeId != null) {
            showProgressDialog();
            new PlaceDetailsPresenter().startFetchingPlaceDetails(placeId, new PlaceDetailsPresenter.CallbackListener() {

                @Override
                public void onSuccess(Place place) {
                    Log.v(TAG, " onSuccess : " + place);
                    dismissProgress();
                    com.evoke.zenforce.model.pojo.placedetails.Result selectedPlace = place.getResult();

                    Log.e(TAG, " onSuccess formatted address : " + selectedPlace.getFormattedAddress());
                    Intent myPlacesIntent = new Intent(mContext, PlaceMapActivity.class);
                    myPlacesIntent.putExtra("SELECTED_PLACE", selectedPlace);
                    myPlacesIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(myPlacesIntent);
                    finish();
                }

                @Override
                public void onError(String errorMsg) {
                    Log.v(TAG, "onError......");
                    dismissProgress();
                    showSnackBar(errorMsg);
                }
            });
        } else {
            Log.e(TAG, "No placeId found...");
            showSnackBar("PlaceId not found...");
        }
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }
        mProgressDialog.setMessage(getString(R.string.loading));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }


    private void dismissProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            if (!isFinishing()) {
                mProgressDialog.dismiss();
            }
        }
    }

/*
    @Override
    public void onFetchPlaceDetailsStart() {
        Log.v(TAG, "onFetchPlaceDetailsStart.....");
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }
        mProgressDialog.setMessage(getString(R.string.loading_place));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    public void onFetchPlaceDetailsComplete(Place place) {
        Log.v(TAG, " onFetchComplete : " + place);
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            if (!isFinishing()) {
                mProgressDialog.dismiss();
            }
        }
        com.evoke.zenforce.model.pojo.placedetails.Result result = place.getResult();
//        etName.setText(result.getName());
//        etAddress.setText(result.getAdrAddress());
//        etPhone.setText(result.getInternationalPhoneNumber());
//        etWebsite.setText(result.getWebsite());

        Log.v(TAG, " set place details in IntentService...");
        PlaceIntentService.setPlaceToInsert(result);
        Intent myPlacesIntent = new Intent(mContext, PlaceMapActivity.class);
        myPlacesIntent.putExtra("SELECTED_PLACE", result);
        myPlacesIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(myPlacesIntent);
    }

    @Override
    public void onFetchPlaceDetailsFail(String errorMsg) {
        Log.v(TAG, "onFetchPlaceDetailsFail......");
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            if (!isFinishing()) {
                mProgressDialog.dismiss();
            }
        }
        showSnackBar(errorMsg);
    }*/

    public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
        private Drawable mDivider;

        public SimpleDividerItemDecoration(Context context) {
            mDivider = context.getResources().getDrawable(R.drawable.line_divider);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left+30, top, right+30, bottom);
                mDivider.draw(c);
            }
        }
    }

}
