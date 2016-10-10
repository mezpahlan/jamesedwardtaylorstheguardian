package uk.co.mezpahlan.oldtimerag.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import uk.co.mezpahlan.oldtimerag.data.model.search.Search;
import uk.co.mezpahlan.oldtimerag.data.model.singleitem.SingleItem;

/**
 * Retrofit client for The Guardian Open Platform Client
 */
public interface GuardianOpenPlatformClient {
    @GET("search")
    Call<Search> search(@Query("type") String type);

    @GET
    Call<SingleItem> singleItem(@Url String id);
}
