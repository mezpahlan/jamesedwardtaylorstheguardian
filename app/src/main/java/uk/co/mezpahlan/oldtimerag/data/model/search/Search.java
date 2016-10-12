package uk.co.mezpahlan.oldtimerag.data.model.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Search {

    @SerializedName("response")
    @Expose
    private Response response;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Search)) return false;
        Search search = (Search) o;
        return Objects.equals(response, search.response);
    }

    @Override
    public int hashCode() {
        return Objects.hash(response);
    }

    /**
     *
     * @return
     *     The response
     */
    public Response getResponse() {
        return response;
    }

    /**
     *
     * @param response
     *     The response
     */
    public void setResponse(Response response) {
        this.response = response;
    }

}