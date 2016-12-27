package com.evoke.zenforce.model.api;

import com.evoke.zenforce.model.pojo.placedetails.Place;
import com.evoke.zenforce.utility.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by spinninti on 11/15/2016.
 */
public interface PlaceDetailsApi {

    @GET("api/place/details/json?key=" + Constants.GOOGLE_NEAR_BY_PLACES_KEY)
    Call<Place> getPlaceDetails(@Query("placeid") String id);

}