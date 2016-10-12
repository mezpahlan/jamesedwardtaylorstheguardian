
package uk.co.mezpahlan.oldtimerag.data.model.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Fields {

    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fields)) return false;
        Fields fields = (Fields) o;
        return Objects.equals(thumbnail, fields.thumbnail) &&
                Objects.equals(trailText, fields.trailText) &&
                Objects.equals(headline, fields.headline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(thumbnail, trailText, headline);
    }

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