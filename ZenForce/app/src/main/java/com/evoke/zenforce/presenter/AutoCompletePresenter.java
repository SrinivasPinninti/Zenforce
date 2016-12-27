package com.evoke.zenforce.presenter;

import android.util.Log;

import com.evoke.zenforce.model.api.AutoCompleteApiManager;
import com.evoke.zenforce.model.pojo.autocomplete.AutoComplete;
import com.evoke.zenforce.model.pojo.autocomplete.Prediction;
import com.evoke.zenforce.utility.Util;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by spinninti on 11/21/2016.
 */
public class AutoCompletePresenter {

    private static final String TAG = "AutoCompletePresenter";

//    private CallbackListener mCallbackListener;


    public interface CallbackListener {


        void onFetchComplete(List<Prediction> predictions);

        void onFetchFail(String errorMsg);
    }


    private AutoCompleteApiManager apiManager;


    public AutoCompletePresenter() {
        apiManager = new AutoCompleteApiManager();
    }

    public void startFetchingAutoCompletePlace(String input, final CallbackListener callbackListener) {

        if (!Util.isNetworkConnected()) {
            callbackListener.onFetchFail("No NetWork Connection");
            return;
        }

        if (!Util.isLocationServicesEnabled()) {
            callbackListener.onFetchFail("Location service not enabled");
            return;
        }

        Log.v(TAG, "Called startFetchingAutoCompletePlace....");
        Call<AutoComplete> call = apiManager.getApi().getAutoCompletePlaces(input);

        call.enqueue(new Callback<AutoComplete>() {
            @Override
            public void onResponse(Call<AutoComplete> call, Response<AutoComplete> response) {
                Log.v(TAG, " onResponse....." + response.code());
                AutoComplete auto = response.body();
                List<Prediction> predictions = auto.getPredictions();
                Log.v(TAG, " predictions size : " + predictions.size());
                callbackListener.onFetchComplete(predictions);
            }

            @Override
            public void onFailure(Call<AutoComplete> call, Throwable t) {
                Log.e(TAG, " onFailure.... ");
                t.printStackTrace();
                callbackListener.onFetchFail("Something went wrong...");
            }
        });
    }

}