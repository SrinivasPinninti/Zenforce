
package com.evoke.zenforce.model.pojo.autocomplete;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class AutoComplete {

    @SerializedName("predictions")
    @Expose
    private List<Prediction> predictions = new ArrayList<Prediction>();
    @SerializedName("status")
    @Expose
    private String status;

    /**
     * 
     * @return
     *     The predictions
     */
    public List<Prediction> getPredictions() {
        return predictions;
    }

    /**
     * 
     * @param predictions
     *     The predictions
     */
    public void setPredictions(List<Prediction> predictions) {
        this.predictions = predictions;
    }

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

}
