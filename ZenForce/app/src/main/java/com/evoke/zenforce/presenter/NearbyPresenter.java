package com.evoke.zenforce.presenter;

import android.util.Log;

import com.evoke.zenforce.model.api.NearbyApiManger;
import com.evoke.zenforce.model.pojo.nearby.NearbyPlace;
import com.evoke.zenforce.model.pojo.nearby.Result;
import com.evoke.zenforce.utility.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by spinninti on 11/12/2016.
 */
public class NearbyPresenter {


    private static final String TAG = "NearbyPresenter";


    public interface CallbackListener {
        void onSuccess(List<Result> resultList);
        void onFail(String errorMsg);
    }


    private NearbyApiManger mNearbyApiManager;


    public NearbyPresenter() {
        mNearbyApiManager = new NearbyApiManger();
    }

    public void startFetchingNearbyPlaces(String latLng, final CallbackListener callbackListener) {

        Log.v(TAG, "Called startFetchingNearbyPlaces....");
        Call<NearbyPlace> call = mNearbyApiManager.getNearbyApi().getNearbyPlaces(Constants.TYPE, latLng, Constants.RADIUS);

        call.enqueue(new Callback<NearbyPlace>() {
            @Override
            public void onResponse(Call<NearbyPlace> call, Response<NearbyPlace> response) {
                Log.v(TAG, " onResponse....." + response.code());
                NearbyPlace place = response.body();
                List<Result> resultList = place.getResults();
                Log.v(TAG, " resultList size : " + resultList.size());
                callbackListener.onSuccess(resultList);
            }

            @Override
            public void onFailure(Call<NearbyPlace> call, Throwable t) {
                Log.e(TAG, " onFailure.... ");
                t.printStackTrace();
                callbackListener.onFail("Something went wrong...");
            }
        });

    }
}
