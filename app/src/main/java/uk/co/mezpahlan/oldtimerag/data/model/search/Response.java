package uk.co.mezpahlan.oldtimerag.data.model.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Response {

    @SerializedName("currentPage")
    @Expose
    private int currentPage;
    @SerializedName("pageSize")
    @Expose
    private int pageSize;
    @SerializedName("pages")
    @Expose
    private int pages;
    @SerializedName("total")
    @Expose
    private int total;
    @SerializedName("userTier")
    @Expose
    private String userTier;
    @SerializedName("startIndex")
    @Expose
    private int startIndex;
    @SerializedName("results")
    @Expose
    private List<Result> results = new ArrayList<Result>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("orderBy")
    @Expose
    private String orderBy;

    /**
     *
     * @return
     *     The currentPage
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     *
     * @param currentPage
     *     The currentPage
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     *
     * @return
     *     The pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     *
     * @param pageSize
     *     The pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     *
     * @return
     *     The pages
     */
    public int getPages() {
        return pages;
    }

    /**
     *
     * @param pages
     *     The pages
     */
    public void setPages(int pages) {
        this.pages = pages;
    }

    /**
     *
     * @return
     *     The total
     */
    public int getTotal() {
        return total;
    }

    /**
     *
     * @param total
     *     The total
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     *
     * @return
     *     The userTier
     */
    public String getUserTier() {
        return userTier;
    }

    /**
     *
     * @param userTier
     *     The userTier
     */
    public void setUserTier(String userTier) {
        this.userTier = userTier;
    }

    /**
     *
     * @return
     *     The startIndex
     */
    public int getStartIndex() {
        return startIndex;
    }

    /**
     *
     * @param startIndex
     *     The startIndex
     */
    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    /**
     *
     * @return
     *     The results
     */
    public List<Result> getResults() {
        return results;
    }

    /**
     *
     * @param results
     *     The results
     */
    public void setResults(List<Result> results) {
        this.results = results;
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

    /**
     *
     * @return
     *     The orderBy
     */
    public String getOrderBy() {
        return orderBy;
    }

    /**
     *
     * @param orderBy
     *     The orderBy
     */
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

}