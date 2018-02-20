package uk.co.mezpahlan.oldtimerag.data;

import io.reactivex.Single;
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
    Single<Search> search(@Query("type") String type);

    @GET
    Single<SingleItem> singleItem(@Url String id);
}
