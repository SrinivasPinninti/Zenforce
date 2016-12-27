package com.evoke.zenforce.model.api;

import com.evoke.zenforce.model.pojo.nearby.NearbyPlace;
import com.evoke.zenforce.utility.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by spinninti on 11/12/2016.
 */
public interface NearbyApi {


    @GET("api/place/nearbysearch/json?key="+ Constants.GOOGLE_NEAR_BY_PLACES_KEY)
    Call<NearbyPlace> getNearbyPlaces(@Query("type") String type, @Query("location") String location, @Query("radius") int radius);


}
