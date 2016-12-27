package com.evoke.zenforce.presenter;

import android.util.Log;

import com.evoke.zenforce.model.api.PlaceDetailsApiManager;
import com.evoke.zenforce.model.pojo.placedetails.Place;
import com.evoke.zenforce.utility.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by spinninti on 11/15/2016.
 */
public class PlaceDetailsPresenter {


    private static final String TAG = "PlaceDetailsPresenter";
//    private CallbackListener mListener;

    public interface CallbackListener {

        void onSuccess(Place place);
        void onError(String errorMsg);
    }

    private PlaceDetailsApiManager mPlaceDetailsApiManager;

    public PlaceDetailsPresenter() {
        mPlaceDetailsApiManager = new PlaceDetailsApiManager();
    }

    public void startFetchingPlaceDetails(String placeId, final CallbackListener callbackListener) {

        if (!Util.isNetworkConnected()) {
            callbackListener.onError("No NetWork Connection");
            return;
        }

        Call<Place> call = mPlaceDetailsApiManager.getPlaceDetailsApi().getPlaceDetails(placeId);

        call.enqueue(new Callback<Place>() {
            @Override
            public void onResponse(Call<Place> call, Response<Place> response) {
                Log.v(TAG, " onResponse....." + response.code());
                Place place = response.body();
                callbackListener.onSuccess(place);
            }

            @Override
            public void onFailure(Call<Place> call, Throwable t) {
                Log.e(TAG, "onFailure...");
                t.printStackTrace();
                callbackListener.onError("Something went wrong...");
            }
        });


    }

}
