package uk.co.mezpahlan.jamesedwardtaylorstheguardian.data;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.mezpahlan.jamesedwardtaylorstheguardian.base.Constants;

/**
 * Retrofit Service Generator for The Guardian Open Platform.
 */
public class GuardianOpenPlatformServiceGenerator {
    private static final String API_BASE_URL = "http://content.guardianapis.com";
    private static final String QUERY_PARAM_KEY_API_KEY = "api-key";
    private static final String QUERY_PARAM_KEY_SHOW_FIELDS = "show-fields";
    private static final String QUERY_PARAM_VALUE_SHOW_FIELDS = "thumbnail,headline,trailText";

    private static final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static final Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {

        // Always add the API key to requests
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();

                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter(QUERY_PARAM_KEY_SHOW_FIELDS, QUERY_PARAM_VALUE_SHOW_FIELDS )
                        .addQueryParameter(QUERY_PARAM_KEY_API_KEY, Constants.GUARDIAN_KEY)
                        .build();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .url(url);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        Retrofit retrofit = GuardianOpenPlatformServiceGenerator.builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }
}
