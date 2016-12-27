package com.evoke.zenforce.model.api;

import com.evoke.zenforce.model.pojo.autocomplete.AutoComplete;
import com.evoke.zenforce.model.pojo.nearby.NearbyPlace;
import com.evoke.zenforce.utility.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by spinninti on 11/21/2016.
 */
public interface AutoCompletePlaceApi {

    @GET("api/place/autocomplete/json?types="+Constants.TYPES+"&key="+ Constants.GOOGLE_NEAR_BY_PLACES_KEY) // , @Query("radius") int radius, @Query("types") String types
    Call<AutoComplete> getAutoCompletePlaces(@Query("input") String input);  // radius="+Constants.AUTO_RADIUS+"
}
