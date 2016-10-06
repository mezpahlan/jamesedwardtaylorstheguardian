package uk.co.mezpahlan.jamesedwardtaylorstheguardian.data;

import retrofit2.Call;
import retrofit2.http.GET;
import uk.co.mezpahlan.jamesedwardtaylorstheguardian.data.model.Search;

/**
 * Retrofit client for The Guardian Open Platform Client
 */
public interface GuardianOpenPlatformClient {
    @GET("search")
    Call<Search> search();
}
