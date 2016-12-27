package com.evoke.zenforce.view.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.evoke.zenforce.R;
import com.evoke.zenforce.model.database.beanentity.VisitBean;
import com.evoke.zenforce.model.database.dao.VisitDAO;
import com.evoke.zenforce.model.pojo.autocomplete.Prediction;
import com.evoke.zenforce.model.pojo.placedetails.Place;
import com.evoke.zenforce.model.pojo.placedetails.Result;
import com.evoke.zenforce.presenter.AutoCompletePresenter;
import com.evoke.zenforce.presenter.PlaceDetailsPresenter;
import com.evoke.zenforce.utility.Util;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class PlaceMapActivity extends AppCompatActivity implements
        OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener, GoogleMap.OnMarkerDragListener, AdapterView.OnItemClickListener {

    private static final String TAG = "PlaceMapActivity";
    private static final int PERMISSION_ACCESS_COARSE_LOCATION = 123;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;

    LatLng latLng;
    GoogleMap mGoogleMap;
    SupportMapFragment mFragment;
    Marker mMarker;
    private ProgressDialog mProgressDialog;

    private Double mLat;
    private Double mLng;
//    private CollapsingToolbarLayout collapsingToolbarLayout;


    private EditText etName;
    private AutoCompleteTextView actAddress;
    private EditText etPhone;
    private EditText etWebsite;


    private VisitBean mVisitBean;

    ArrayList<String> placeIdList;

    private PlaceDetailsPresenter mPlaceDetailsPresenter;

    private UiSettings mUiSettings;
    private Location mLastLocation;
    private Location mCurrentLocation;

//    private  Result mSelectedPlace;

    private com.evoke.zenforce.model.pojo.placedetails.Location mPlaceLocation = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myplacemap_activity);
        initUI();
        showProgress();
        buildGoogleApiClient();
        getDataFromIntent();

    }


    private void initUI() {
        mFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mFragment.getMapAsync(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setTitle("New Place");


        etName = (EditText) findViewById(R.id.etName);
        actAddress = (AutoCompleteTextView) findViewById(R.id.etAddress);
        actAddress.setAdapter(new AutoCompleteTextAdapter(this, R.layout.autocomplete_list_item));
        actAddress.setOnItemClickListener(this);
        actAddress.setThreshold(1);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etWebsite = (EditText) findViewById(R.id.etWebsite);

    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();


    }

    @Override
    public void onResume() {
        Log.v(TAG, "onResume mGoogleApiClient  isConnected : " + mGoogleApiClient.isConnected());
        /*if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            startLocationUpdates();

        }*/
        super.onResume();
    }

    private void getDataFromIntent() {

        Bundle bundle = getIntent().getExtras();
        Log.d(TAG, " bundle : " + bundle);
        if (bundle != null) {
            Result place = bundle.getParcelable("SELECTED_PLACE");
            if (place != null) {
                setDataToView(etName, place.getName());
                setDataToView(actAddress, place.getFormattedAddress());
                setDataToView(etPhone, place.getInternationalPhoneNumber());
                setDataToView(etWebsite, place.getWebsite());

                Log.e(TAG, "  getFormattedAddress : " + place.getFormattedAddress());
                com.evoke.zenforce.model.pojo.placedetails.Location location = place.getGeometry().getLocation();

                if (location != null) {
                    showProgress();
                    updateMapDelay(location); // TODO : mGoogleApiClient is connected
                }

                mVisitBean = getLocationBean(place);

            } else {
                Log.e(TAG, "No placeId found...");
//                showSnackBar("PlaceId not found...");
            }
        }
    }


    private VisitBean getLocationBean(Result place) {

        VisitBean bean = new VisitBean();

        bean.setName(place.getName());
        bean.setAddress(place.getFormattedAddress());
        bean.setPhone(place.getInternationalPhoneNumber());
        bean.setWebsite(place.getWebsite());
        bean.setLocationId(place.getPlaceId());


        com.evoke.zenforce.model.pojo.placedetails.Location location = place.getGeometry().getLocation();

        bean.setLocationLat(location.getLat()+"");
        bean.setLocationLng(location.getLng()+"");


        Log.d(TAG, " map url : " + place.getWebsite());

        return bean;
    }


    private void updateMapDelay(final com.evoke.zenforce.model.pojo.placedetails.Location location) {
        Handler handler = new Handler(Looper.myLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.v(TAG, " Update marker position after 5 seconds...");
                updateMapLocation(new LatLng(location.getLat(), location.getLng()));
                dismissProgress();
            }
        }, 5000);
    }

    private void setDataToView(EditText view, String value) {

        view.setText(value);
       /* if (TextUtils.isEmpty(value)) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
            view.setText(value);
        }*/
    }

    /*private void clearViews() {
        etName.setText("");
        actAddress.setText("");
        etPhone.setText("");
        etWebsite.setText("");
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map_activity_toolbar_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.toolbar_submit:
                if (isFieldsEmpty()) {  // Fields are empty
                    displayPopupToSaveData();
                } else {
                    Toast.makeText(PlaceMapActivity.this, "Name and Location fields must not be empty!!", Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean isFieldsEmpty() {
        String name = etName.getText().toString().trim();
        String address = actAddress.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(address)) {
            return false;
        }

        return true;
    }

    private void displayPopupToSaveData() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.title_new_place);
        builder.setMessage(R.string.msg_new_place);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.v(TAG, "mVisitBean : " + mVisitBean);

                if (mVisitBean == null) {  // Case :  User manually entered details

                    mVisitBean = new VisitBean();
                    mVisitBean.setName(etName.getText().toString());
                    mVisitBean.setAddress(actAddress.getText().toString());
                    mVisitBean.setPhone(etPhone.getText().toString());
                    mVisitBean.setWebsite(etWebsite.getText().toString());

                }

                VisitDAO dao = VisitDAO.getSingletonInstance(PlaceMapActivity.this);
                long visitId = dao.insert(mVisitBean);
                Log.v(TAG, " inserted visitId : " + visitId);
                if (visitId > 0) {
//                    Toast.makeText(PlaceMapActivity.this, "Successfully added place", Toast.LENGTH_LONG).show();
                    Log.v(TAG, " insertion success set visitId : "+visitId);
                    mVisitBean.set_ID(visitId);
                    Intent intent = new Intent(PlaceMapActivity.this, VisitActivity.class);
                    intent.putExtra("visitBean", mVisitBean);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    Log.e(TAG, " insertion failed...");
                    Toast.makeText(PlaceMapActivity.this, "Insertion failed !!", Toast.LENGTH_LONG).show();
                }

            }
        });

        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                clearViews();
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    @Override
    public void onMapReady(GoogleMap gMap) {
        Log.v(TAG, "onMapReady called...." + gMap);
        gMap.setOnMarkerDragListener(this);
        mGoogleMap = gMap;
        mGoogleMap.setBuildingsEnabled(true);
        mUiSettings = mGoogleMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
//        buildGoogleApiClient();
    }


    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }

    }


    protected synchronized void buildGoogleApiClient() {
        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.v(TAG, "onConnected called....");


//        Toast.makeText(this, "onConnected", Toast.LENGTH_SHORT).show();

        int currentApiVersion = android.os.Build.VERSION.SDK_INT;


        Log.v(TAG, "currentApiVersion : " + currentApiVersion);

        if (currentApiVersion > android.os.Build.VERSION_CODES.LOLLIPOP){
            // Do something for lollipop and above versions

            Log.v(TAG, " Marshmallow......");

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_COARSE_LOCATION },
                        PERMISSION_ACCESS_COARSE_LOCATION);
            }



        } else{
            // do something for phones running an SDK before lollipop

            Log.v(TAG, " lollipop......");

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

            createLocationRequest();
            startLocationUpdates();

        }




        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            //mSelectedPlace marker at current position
            //mGoogleMap.clear();

            mLat = mLastLocation.getLatitude();
            mLng = mLastLocation.getLongitude();

            Log.v(TAG, " Lat : " + mLastLocation.getLatitude() + " Long : " + mLastLocation.getLongitude());
            latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());


//            createLocationRequest();
//            startLocationUpdates();

            updateMapLocation(latLng);



            /*MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Current Position");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            mMarker = mGoogleMap.addMarker(markerOptions);*/
        }
//        mLocationRequest = new LocationRequest();
//        mLocationRequest.setInterval(10000); //5 seconds
//        mLocationRequest.setFastestInterval(5000); //3 seconds
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        //mLocationRequest.setSmallestDisplacement(0.1F); //1/10 meter

//        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ACCESS_COARSE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // All good!
                    createLocationRequest();
                    startLocationUpdates();
                } else {
                    Toast.makeText(this, "Need your location!", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000); // 10 sec
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }



    protected void stopLocationUpdates() {

        Log.v(TAG, "stopLocationUpdates mGoogleApiClient isConnected : " + mGoogleApiClient.isConnected());
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.v(TAG, "onConnectionSuspended called......");
//        Toast.makeText(this,"onConnectionSuspended",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.v(TAG, "onConnectionFailed called....");
//        Toast.makeText(this,"onConnectionFailed",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        Log.v(TAG, "onLocationChanged....");
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        updateMapLocation(latLng);

    }


    public void searchNearbyPlaces(View v) {
        Log.v(TAG, " NetWork Connection :  " + Util.isNetworkConnected());
        if (!Util.isNetworkConnected()) {
            showSnackBar("No NetWork Connection");
            return;
        }

        Log.v(TAG, " location enabled :  " + Util.isLocationServicesEnabled());
        if (!Util.isLocationServicesEnabled()) {
            showLocationEnableDialog();
            return;
        }

        Log.v(TAG, "searchNearbyPlaces...Calling startFetchingNearbyPlaces....");
        Intent myPlacesIntent = new Intent(this, NearByPlacesListActivity.class);
        myPlacesIntent.putExtra("LAT", mLat);
        myPlacesIntent.putExtra("LNG", mLng);
        myPlacesIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(myPlacesIntent);
//        new NearbyPresenter(this).startFetchingNearbyPlaces("restaurant", "17.4531745,78.414476", 500);
    }

    private void showLocationEnableDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getResources().getString(R.string.enable_location));
        dialog.setMessage(R.string.enable_location_msg);
        dialog.setPositiveButton(getResources().getString(R.string.location_settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                // TODO Auto-generated method stub
                Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(myIntent);
                //get gps
            }
        });
        dialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                // TODO Auto-generated method stub

            }
        });
        dialog.show();
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



    private void setDataToViews(Result result) {
        etName.setText(result.getName());
//        etAddress.setText(result.getAdrAddress());
        etPhone.setText(result.getInternationalPhoneNumber());
        etWebsite.setText(result.getWebsite());


        mVisitBean = getLocationBean(result);

        /*if (mMarker != null) {
            mMarker.remove();
        }*/
        Log.v(TAG, "setDataToViews....");
        com.evoke.zenforce.model.pojo.placedetails.Location loc = result.getGeometry().getLocation();

        latLng = new LatLng(loc.getLat(), loc.getLng());
        updateMapLocation(latLng);
    }


    @Override
    public void onMarkerDragStart(Marker marker) {
        Log.v(TAG, "onMarkerDragStart....");
    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        Log.v(TAG, "onMarkerDragEnd....");
        LatLng latLng = marker.getPosition();
        updateMapLocation(latLng);
    }


    private void updateMapLocation(LatLng latLng) {
        Log.v(TAG, " updateMapLocation mMarker " + mMarker);
        stopLocationUpdates();
        if (mMarker != null) {
            mMarker.remove();
        }
        mGoogleMap.clear();
        Log.v(TAG, " ################## Changing marker position #################### ");

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.draggable(true);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mMarker = mGoogleMap.addMarker(markerOptions);

        //zoom to current position:
        CameraPosition cameraPosition = new CameraPosition.Builder().target(mMarker.getPosition()).zoom(14).build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        dismissProgress();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String str = (String) parent.getItemAtPosition(position);
        Log.v(TAG, "onItemClick : " + str);
        if (placeIdList != null) {
            String pId = placeIdList.get(position);
            Log.v(TAG, "onItemClick mSelectedPlace id : " + pId);

            if (mPlaceDetailsPresenter == null) {
                mPlaceDetailsPresenter = new PlaceDetailsPresenter();
            }

            mPlaceDetailsPresenter.startFetchingPlaceDetails(pId, new PlaceDetailsPresenter.CallbackListener() {

                @Override
                public void onSuccess(Place place) {
                    Log.v(TAG, " onFetchComplete : " + place);
                    dismissProgress();
//                    com.evoke.zenforce.model.pojo.placedetails.Result result = mSelectedPlace.getResult();
                    setDataToViews(place.getResult());

                }

                @Override
                public void onError(String errorMsg) {

                }
            });
        }
    }

    private void dismissProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            if (!isFinishing()) {
                mProgressDialog.dismiss();
            }
        }
    }

    private void showProgress() {

        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }
        mProgressDialog.setMessage(getString(R.string.loading_place));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

    }


    public class AutoCompleteTextAdapter extends ArrayAdapter implements Filterable {
        private ArrayList<String> resultList;
        private AutoCompletePresenter mPresenter;

        public AutoCompleteTextAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
            mPresenter = new AutoCompletePresenter();
        }

        @Override
        public int getCount() {
            Log.v(TAG, " getCount : " + resultList.size());
            return resultList.size();
        }

        @Override
        public String getItem(int index) {
            Log.v(TAG, " getItem : " + resultList.get(index));
            return resultList.get(index);
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    final FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        String latLng = mLat + ", " + mLng;
                        String input = constraint.toString();
                        Log.v(TAG, " filter constraint : " + input);

                        mPresenter.startFetchingAutoCompletePlace(input, new AutoCompletePresenter.CallbackListener() {

                            @Override
                            public void onFetchComplete(List<Prediction> predictions) {
                                ArrayList<String> autoList = new ArrayList<>();
                                placeIdList = new ArrayList<>();
                                for (Prediction p : predictions) {
                                    autoList.add(p.getDescription());
                                    placeIdList.add(p.getPlaceId());
                                }
                                Log.v(TAG, " onFetchComplete auto complete places : " + autoList);
                                resultList = new ArrayList<String>(autoList);
                                Log.e(TAG, " resultList : " + resultList.size());
                                if (resultList.size() > 0) {
                                    filterResults.values = resultList;
                                    filterResults.count = resultList.size();
                                    Log.v(TAG, " notifyDataSetChanged");
                                    notifyDataSetChanged();
                                } else {
                                    notifyDataSetInvalidated();
                                }
                            }

                            @Override
                            public void onFetchFail(String errorMsg) {
                                Log.e(TAG, " onFetchFail : " + errorMsg);
                                if ("No NetWork Connection".equals(errorMsg)) {
                                    showSnackBar(errorMsg);
                                } else { // enable location
                                    Log.e(TAG, " enable location...");
                                    showLocationEnableDialog();
                                }
                            }
                        });

                        // Retrieve the autocomplete results.
//                        resultList = autocomplete(constraint.toString());


                        // Assign the data to the FilterResults
//                            filterResults.values = resultList;
//                            filterResults.count = resultList.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    Log.v(TAG, " publishResults : " + results.count);
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }


       /* private ArrayList<String> autocomplete(String string) {

            String latLng = mLat + ", " + mLng;
            mPresenter.startFetchingAutoCompletePlace(string, latLng, new AutoCompletePresenter.CallbackListener() {
                @Override
                public void onFetchStart() {

                }

                @Override
                public List<String> onFetchComplete(List<Prediction> predictions) {
                    ArrayList<String> autoList = new ArrayList<>();
                    for (Prediction p : predictions) {
                        autoList.add(p.getDescription());
                    }
                    Log.v(TAG, " onFetchComplete auto complete places : " + autoList);
                    return autoList;

                }

                @Override
                public void onFetchFail(String errorMsg) {

                }
            });
            return null;
        }*/





    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}