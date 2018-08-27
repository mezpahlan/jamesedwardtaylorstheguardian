package uk.co.mezpahlan.oldtimerag.theguardian.data.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import uk.co.mezpahlan.oldtimerag.theguardian.data.models.search.Search
import uk.co.mezpahlan.oldtimerag.theguardian.data.models.singleitem.SingleItem

/**
 * Retrofit client for The Guardian Open Platform Client
 */
interface TheGuardianOpenPlatformClient {
    @GET("search")
    fun search(@Query("type") type: String): Single<Search>

    @GET
    fun singleItem(@Url id: String): Single<SingleItem>
}
