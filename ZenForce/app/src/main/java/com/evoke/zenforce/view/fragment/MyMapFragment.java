package com.evoke.zenforce.view.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.evoke.zenforce.R;
import com.evoke.zenforce.model.pojo.placedetails.Result;
import com.evoke.zenforce.presenter.PlaceDetailsPresenter;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MyMapFragment extends SupportMapFragment implements OnMapReadyCallback {
    private static final String TAG = "MyMapFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Context mContext;


    private RelativeLayout rlExploreNearPlaces;
    private EditText etName;
    private AutoCompleteTextView actAddress;
    private EditText etPhone;
    private EditText etWebsite;


    ArrayList<String> placeIdList;

    private PlaceDetailsPresenter mPlaceDetailsPresenter;

    private UiSettings mUiSettings;
    private Location mLastLocation;
    private Location mCurrentLocation;

    private Result mSelectedPlace;

    private com.evoke.zenforce.model.pojo.placedetails.Location mPlaceLocation = null;
    private GoogleApiClient mGoogleApiClient;
    private LatLng latLng;
    private Marker mMarker;
    private GoogleMap mGoogleMap;
    private Double mLat;
    private Double mLng;
    private LocationRequest mLocationRequest;
    private ProgressDialog mProgressDialog;
    private static SupportMapFragment mMapFragment;
    private MapView mapView;


    public MyMapFragment() {
        // Required empty public constructor
    }

    public static MyMapFragment newInstance() {
        MyMapFragment fragment = new MyMapFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        mMapFragment = (SupportMapFragment) fm.findFragmentById(R.id.my_map);
        if (mMapFragment == null) {
            mMapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map_container, mMapFragment).commit();
            mMapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
       /* if (mGoogleMap == null) {
            mGoogleMap = mMapFragment.getM();
            map.addMarker(new MarkerOptions().position(new LatLng(0, 0)));
        }*/
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.my_map);
        Log.v(TAG, " mapFragment : " + mapFragment);
        mapFragment.getMapAsync(this);
        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.v(TAG, " onMapReady : " + googleMap);
        mGoogleMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }



   /* private void initUI(View v) {
        mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        Log.v(TAG, " mMapFragment : " + mMapFragment);
        mMapFragment.getMapAsync(this);


        rlExploreNearPlaces = (RelativeLayout) v.findViewById(R.id.rlExploreNearPlaces);
        rlExploreNearPlaces.setOnClickListener(this);
        etName = (EditText) v.findViewById(R.id.etName);
        actAddress = (AutoCompleteTextView) v.findViewById(R.id.etAddress);
        actAddress.setAdapter(new AutoCompleteTextAdapter(mContext, R.layout.autocomplete_list_item));
        actAddress.setOnItemClickListener(this);
        actAddress.setThreshold(1);
        etPhone = (EditText) v.findViewById(R.id.etPhone);
        etWebsite = (EditText) v.findViewById(R.id.etWebsite);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    private void showConfirmationDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.title_new_place);
        builder.setMessage(R.string.msg_new_place);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.v(TAG, "mPlaceBean : " + mPlaceBean);
                if (mPlaceBean != null) {
                    PlaceDAO dao = PlaceDAO.getSingletonInstance(mContext);
                    long result = dao.insert(mPlaceBean);
                    if (result > 0) {
                        Log.v(TAG, " insertion success...");
                        Intent myPlacesIntent = new Intent(mContext, VisitActivity.class);
                        myPlacesIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(myPlacesIntent);
                    } else {
                        Log.e(TAG, " insertion failed...");
                    }
                } else {
                    Log.e(TAG, " fill mSelectedPlace details...");
                }

//                PlaceIntentService.startActionInsert(PlaceMapActivity.this);

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

        if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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




    protected synchronized void buildGoogleApiClient() {
        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(mContext)
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
        if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            //mSelectedPlace marker at current position
            //mGoogleMap.clear();

            mLat = mLastLocation.getLatitude();
            mLng = mLastLocation.getLongitude();

            Log.v(TAG, " Lat : " + mLastLocation.getLatitude() + " Long : " + mLastLocation.getLongitude());
            latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            Log.v(TAG, "onConnected....");

            createLocationRequest();
            startLocationUpdates();

            updateMapLocation(latLng);

            *//*MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Current Position");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            mMarker = mGoogleMap.addMarker(markerOptions);*//*
        }



//        mLocationRequest = new LocationRequest();
//        mLocationRequest.setInterval(10000); //5 seconds
//        mLocationRequest.setFastestInterval(5000); //3 seconds
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        //mLocationRequest.setSmallestDisplacement(0.1F); //1/10 meter

//        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    protected void createLocationRequest() {
        Log.v(TAG, " %%%%%%%%%%%%%%%%%%%% createLocationRequest %%%%%%%%%%%%%%%%%%%%%%55");
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000); // 10 sec
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }



    protected void stopLocationUpdates() {
        Log.v(TAG, " @@@@@@@@@@@@@ stopLocationUpdates called @@@@@@@@@@@@@");
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
//        mGoogleApiClient = null;
    }

    protected void startLocationUpdates() {
        Log.v(TAG, " $$$$$$$$$$$$ startLocationUpdates called $$$$$$$$$$$$$$");
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Log.v(TAG, " mGoogleApiClient :  " + mGoogleApiClient);
        Log.v(TAG, " mLocationRequest :  " + mLocationRequest);
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
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
//        Log.v(TAG, "onLocationChanged called....");
        //mSelectedPlace marker at current position
        //mGoogleMap.clear();
       *//* if (mMarker != null) {
            mMarker.remove();
        }*//*
        mCurrentLocation = location;
        Log.v(TAG, "onLocationChanged....");
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        updateMapLocation(latLng);

//        Log.v(TAG, " call removeLocationUpdates()....");
//        stopLocationUpdates();

        *//*MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mMarker = mGoogleMap.addMarker(markerOptions);

        //zoom to current position:
        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(14).build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));*//*

        //If you only need one location, unregister the listener
        //LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

    }


    public void searchNearbyPlaces() {
        Log.v(TAG, "searchNearbyPlaces...Calling startFetchingNearbyPlaces....");
//        Intent myPlacesIntent = new Intent(this, NearByPlacesListActivity.class);
//        myPlacesIntent.putExtra("LAT", mLat);
//        myPlacesIntent.putExtra("LNG", mLng);
//        myPlacesIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        startActivity(myPlacesIntent);
//        new NearbyPresenter(this).startFetchingNearbyPlaces("restaurant", "17.4531745,78.414476", 500);
    }


    private void showSnackbar(String errorMsg) {
        if (errorMsg != null) {
            Snackbar.make(getActivity().findViewById(android.R.id.content), errorMsg, Snackbar.LENGTH_LONG)
                    .setAction(R.string.close, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getActivity().finish();
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

        *//*if (mMarker != null) {
            mMarker.remove();
        }*//*
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
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mMarker = mGoogleMap.addMarker(markerOptions);

        //zoom to current position:
        CameraPosition cameraPosition = new CameraPosition.Builder().target(mMarker.getPosition()).zoom(14).build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

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
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        if (!getActivity().isFinishing()) {
                            mProgressDialog.dismiss();
                        }
                    }
//                    com.evoke.zenforce.model.pojo.placedetails.Result result = mSelectedPlace.getResult();
                    setDataToViews(place.getResult());

                }

                @Override
                public void onError(String errorMsg) {

                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlExploreNearPlaces:
                searchNearbyPlaces();
                break;
        }
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
            Log.v(TAG, " getCount : " +resultList.size());
            return resultList.size();
        }

        @Override
        public String getItem(int index) {
            Log.v(TAG, " getItem : " +resultList.get(index));
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
                                Log.e(TAG, " resultList : " +resultList.size());
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
                    Log.v(TAG, " publishResults : " +results.count);
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }

    }*/
}
