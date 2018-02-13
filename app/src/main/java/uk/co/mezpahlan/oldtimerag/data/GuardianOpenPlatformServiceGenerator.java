package uk.co.mezpahlan.oldtimerag.data;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.mezpahlan.oldtimerag.base.Constants;

/**
 * Retrofit Service Generator for The Guardian Open Platform.
 */
public class GuardianOpenPlatformServiceGenerator {
    private static final String API_BASE_URL = "http://content.guardianapis.com/";
    private static final String QUERY_PARAM_KEY_API_KEY = "api-key";
    private static final String QUERY_PARAM_KEY_SHOW_FIELDS = "show-fields";
    private static final String QUERY_PARAM_KEY_PAGE_SIZE = "page-size";
    private static final String QUERY_PARAM_VALUE_SHOW_FIELDS_SEARCH = "thumbnail,headline,trailText";
    private static final String QUERY_PARAM_VALUE_SHOW_FIELDS_SINGLE_ITEM = "body";
    private static final String QUERY_PARAM_VALUE_PAGE_SIZE = "50";

    private static final OkHttpClient.Builder httpSearchClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            HttpUrl originalHttpUrl = original.url();

            HttpUrl url = originalHttpUrl.newBuilder()
                    .addQueryParameter(QUERY_PARAM_KEY_SHOW_FIELDS, QUERY_PARAM_VALUE_SHOW_FIELDS_SEARCH)
                    .addQueryParameter(QUERY_PARAM_KEY_PAGE_SIZE, QUERY_PARAM_VALUE_PAGE_SIZE)
                    .addQueryParameter(QUERY_PARAM_KEY_API_KEY, Constants.GUARDIAN_KEY)
                    .build();

            // Request customization: add request headers
            Request.Builder requestBuilder = original.newBuilder()
                    .url(url);

            Request request = requestBuilder.build();
            return chain.proceed(request);
        }
    });

    private static final OkHttpClient.Builder httpSingleItemClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
        @Override
        public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
            Request original = chain.request();
            HttpUrl originalHttpUrl = original.url();

            HttpUrl url = originalHttpUrl.newBuilder()
                    .addQueryParameter(QUERY_PARAM_KEY_SHOW_FIELDS, QUERY_PARAM_VALUE_SHOW_FIELDS_SINGLE_ITEM)
                    .addQueryParameter(QUERY_PARAM_KEY_API_KEY, Constants.GUARDIAN_KEY)
                    .build();

            // Request customization: add request headers
            Request.Builder requestBuilder = original.newBuilder()
                    .url(url);

            Request request = requestBuilder.build();
            return chain.proceed(request);
        }
    });

    private static final Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

    public static <S> S createSearchService(Class<S> serviceClass) {
        Retrofit retrofit = GuardianOpenPlatformServiceGenerator.builder.client(httpSearchClient.build()).build();
        return retrofit.create(serviceClass);
    }

    public static <S> S createSingleItemService(Class<S> serviceClass) {
        Retrofit retrofit = GuardianOpenPlatformServiceGenerator.builder.client(httpSingleItemClient.build()).build();
        return retrofit.create(serviceClass);
    }
}
