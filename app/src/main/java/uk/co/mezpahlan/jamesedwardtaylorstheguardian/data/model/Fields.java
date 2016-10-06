
package uk.co.mezpahlan.jamesedwardtaylorstheguardian.data.model;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Fields {

    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("trailText")
    @Expose
    private String trailText;
    @SerializedName("headline")
    @Expose
    private String headline;

    /**
     *
     * @return
     *     The thumbnail
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     *
     * @param thumbnail
     *     The thumbnail
     */
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     *
     * @return
     *     The trailText
     */
    public String getTrailText() {
        return trailText;
    }

    /**
     *
     * @param trailText
     *     The trailText
     */
    public void setTrailText(String trailText) {
        this.trailText = trailText;
    }

    /**
     *
     * @return
     *     The headline
     */
    public String getHeadline() {
        return headline;
    }

    /**
     *
     * @param headline
     *     The headline
     */
    public void setHeadline(String headline) {
        this.headline = headline;
    }

}