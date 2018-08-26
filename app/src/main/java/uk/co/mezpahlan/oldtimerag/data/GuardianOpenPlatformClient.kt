package uk.co.mezpahlan.oldtimerag.data

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import uk.co.mezpahlan.oldtimerag.data.model.search.Search
import uk.co.mezpahlan.oldtimerag.data.model.singleitem.SingleItem

/**
 * Retrofit client for The Guardian Open Platform Client
 */
interface GuardianOpenPlatformClient {
    @GET("search")
    fun search(@Query("type") type: String): Single<Search>

    @GET
    fun singleItem(@Url id: String): Single<SingleItem>
}
