package com.evoke.zenforce.model.api;

import com.evoke.zenforce.utility.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by spinninti on 11/12/2016.
 */
public class NearbyApiManger {

    private NearbyApi mNearbyApi;

    public NearbyApi getNearbyApi() {

        if (mNearbyApi == null) {

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            mNearbyApi = retrofit.create(NearbyApi.class);

        }

        return mNearbyApi;
    }
}
