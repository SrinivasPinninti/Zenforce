package com.evoke.zenforce.model.api;

import com.evoke.zenforce.utility.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by spinninti on 11/15/2016.
 */
public class PlaceDetailsApiManager {

    private PlaceDetailsApi mPlaceDetailsApi;


    public PlaceDetailsApi getPlaceDetailsApi() {

        if (mPlaceDetailsApi == null) {

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            mPlaceDetailsApi = retrofit.create(PlaceDetailsApi.class);

        }

        return mPlaceDetailsApi;
    }

}
