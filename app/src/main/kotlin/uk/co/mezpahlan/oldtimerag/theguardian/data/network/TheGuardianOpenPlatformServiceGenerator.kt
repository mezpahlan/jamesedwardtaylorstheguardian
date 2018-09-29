package uk.co.mezpahlan.oldtimerag.theguardian.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import uk.co.mezpahlan.oldtimerag.base.GUARDIAN_KEY

/**
 * Retrofit Service Generator for The Guardian Open Platform.
 */
object TheGuardianOpenPlatformServiceGenerator {
    private const val API_BASE_URL = "http://content.guardianapis.com/"
    private const val QUERY_PARAM_KEY_API_KEY = "api-key"
    private const val QUERY_PARAM_KEY_SHOW_FIELDS = "show-fields"
    private const val QUERY_PARAM_KEY_PAGE_SIZE = "page-size"
    private const val QUERY_PARAM_VALUE_SHOW_FIELDS_SEARCH = "thumbnail,headline,trailText"
    private const val QUERY_PARAM_VALUE_SHOW_FIELDS_SINGLE_ITEM = "body"
    private const val QUERY_PARAM_VALUE_PAGE_SIZE = "50"

    private val searchClient = OkHttpClient.Builder().addInterceptor { chain ->
        val original = chain.request()
        val originalHttpUrl = original.url()

        val url = originalHttpUrl.newBuilder()
                .addQueryParameter(QUERY_PARAM_KEY_SHOW_FIELDS, QUERY_PARAM_VALUE_SHOW_FIELDS_SEARCH)
                .addQueryParameter(QUERY_PARAM_KEY_PAGE_SIZE, QUERY_PARAM_VALUE_PAGE_SIZE)
                .addQueryParameter(QUERY_PARAM_KEY_API_KEY, GUARDIAN_KEY)
                .build()

        // Request customization: add request headers
        val requestBuilder = original.newBuilder()
                .url(url)

        val request = requestBuilder.build()
        chain.proceed(request)
    }

    private val articleClient = OkHttpClient.Builder().addInterceptor { chain ->
        val original = chain.request()
        val originalHttpUrl = original.url()

        val url = originalHttpUrl.newBuilder()
                .addQueryParameter(QUERY_PARAM_KEY_SHOW_FIELDS, QUERY_PARAM_VALUE_SHOW_FIELDS_SINGLE_ITEM)
                .addQueryParameter(QUERY_PARAM_KEY_API_KEY, GUARDIAN_KEY)
                .build()

        // Request customization: add request headers
        val requestBuilder = original.newBuilder()
                .url(url)

        val request = requestBuilder.build()
        chain.proceed(request)
    }

    private val builder = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

    fun createFeedService(): TheGuardianOpenPlatformClient {
        val retrofit = builder.client(searchClient.build()).build()
        return retrofit.create(TheGuardianOpenPlatformClient::class.java)
    }

    fun createArticleService(): TheGuardianOpenPlatformClient {
        val retrofit = builder.client(articleClient.build()).build()
        return retrofit.create(TheGuardianOpenPlatformClient::class.java)
    }
}