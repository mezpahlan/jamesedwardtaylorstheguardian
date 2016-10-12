package uk.co.mezpahlan.oldtimerag.data.model.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Result {

    @SerializedName("webUrl")
    @Expose
    private String webUrl;
    @SerializedName("webPublicationDate")
    @Expose
    private String webPublicationDate;
    @SerializedName("webTitle")
    @Expose
    private String webTitle;
    @SerializedName("sectionName")
    @Expose
    private String sectionName;
    @SerializedName("apiUrl")
    @Expose
    private String apiUrl;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("isHosted")
    @Expose
    private boolean isHosted;
    @SerializedName("fields")
    @Expose
    private Fields fields;
    @SerializedName("sectionId")
    @Expose
    private String sectionId;
    @SerializedName("type")
    @Expose
    private String type;

    /**
     *
     * @return
     *     The webUrl
     */
    public String getWebUrl() {
        return webUrl;
    }

    /**
     *
     * @param webUrl
     *     The webUrl
     */
    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    /**
     *
     * @return
     *     The webPublicationDate
     */
    public String getWebPublicationDate() {
        return webPublicationDate;
    }

    /**
     *
     * @param webPublicationDate
     *     The webPublicationDate
     */
    public void setWebPublicationDate(String webPublicationDate) {
        this.webPublicationDate = webPublicationDate;
    }

    /**
     *
     * @return
     *     The webTitle
     */
    public String getWebTitle() {
        return webTitle;
    }

    /**
     *
     * @param webTitle
     *     The webTitle
     */
    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }

    /**
     *
     * @return
     *     The sectionName
     */
    public String getSectionName() {
        return sectionName;
    }

    /**
     *
     * @param sectionName
     *     The sectionName
     */
    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    /**
     *
     * @return
     *     The apiUrl
     */
    public String getApiUrl() {
        return apiUrl;
    }

    /**
     *
     * @param apiUrl
     *     The apiUrl
     */
    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    /**
     *
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     *     The isHosted
     */
    public boolean isIsHosted() {
        return isHosted;
    }

    /**
     *
     * @param isHosted
     *     The isHosted
     */
    public void setIsHosted(boolean isHosted) {
        this.isHosted = isHosted;
    }

    /**
     *
     * @return
     *     The fields
     */
    public Fields getFields() {
        return fields;
    }

    /**
     *
     * @param fields
     *     The fields
     */
    public void setFields(Fields fields) {
        this.fields = fields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Result)) return false;
        Result result = (Result) o;
        return isHosted == result.isHosted &&
                Objects.equals(webUrl, result.webUrl) &&
                Objects.equals(webPublicationDate, result.webPublicationDate) &&
                Objects.equals(webTitle, result.webTitle) &&
                Objects.equals(sectionName, result.sectionName) &&
                Objects.equals(apiUrl, result.apiUrl) &&
                Objects.equals(id, result.id) &&
                Objects.equals(fields, result.fields) &&
                Objects.equals(sectionId, result.sectionId) &&
                Objects.equals(type, result.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(webUrl, webPublicationDate, webTitle, sectionName, apiUrl, id, isHosted, fields, sectionId, type);
    }

    /**

     *
     * @return
     *     The sectionId
     */
    public String getSectionId() {
        return sectionId;
    }

    /**
     *
     * @param sectionId
     *     The sectionId
     */
    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    /**
     *
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

}