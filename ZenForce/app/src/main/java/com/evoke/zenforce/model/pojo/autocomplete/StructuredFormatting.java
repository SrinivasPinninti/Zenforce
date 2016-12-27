
package com.evoke.zenforce.model.pojo.autocomplete;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class StructuredFormatting {

    @SerializedName("main_text")
    @Expose
    private String mainText;
    @SerializedName("main_text_matched_substrings")
    @Expose
    private List<MainTextMatchedSubstring> mainTextMatchedSubstrings = new ArrayList<MainTextMatchedSubstring>();
    @SerializedName("secondary_text")
    @Expose
    private String secondaryText;

    /**
     * 
     * @return
     *     The mainText
     */
    public String getMainText() {
        return mainText;
    }

    /**
     * 
     * @param mainText
     *     The main_text
     */
    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    /**
     * 
     * @return
     *     The mainTextMatchedSubstrings
     */
    public List<MainTextMatchedSubstring> getMainTextMatchedSubstrings() {
        return mainTextMatchedSubstrings;
    }

    /**
     * 
     * @param mainTextMatchedSubstrings
     *     The main_text_matched_substrings
     */
    public void setMainTextMatchedSubstrings(List<MainTextMatchedSubstring> mainTextMatchedSubstrings) {
        this.mainTextMatchedSubstrings = mainTextMatchedSubstrings;
    }

    /**
     * 
     * @return
     *     The secondaryText
     */
    public String getSecondaryText() {
        return secondaryText;
    }

    /**
     * 
     * @param secondaryText
     *     The secondary_text
     */
    public void setSecondaryText(String secondaryText) {
        this.secondaryText = secondaryText;
    }

}
