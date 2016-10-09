package uk.co.mezpahlan.jamesedwardtaylorstheguardian.theguardian.feed;

import android.support.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.mezpahlan.jamesedwardtaylorstheguardian.data.GuardianOpenPlatformClient;
import uk.co.mezpahlan.jamesedwardtaylorstheguardian.data.GuardianOpenPlatformServiceGenerator;
import uk.co.mezpahlan.jamesedwardtaylorstheguardian.data.model.search.Search;

/**
 * ModelInteractor for TheGuardian.Feed. Part of the Model layer.
 */
public class FeedModelInteractor implements FeedMvp.ModelInteractor {

    private FeedMvp.Presenter feedPresenter;
    private GuardianOpenPlatformClient client;
    private Search cachedSearch;

    public FeedModelInteractor(FeedMvp.Presenter itemPresenter) {
        this.feedPresenter = itemPresenter;
        client = GuardianOpenPlatformServiceGenerator.createSearchService(GuardianOpenPlatformClient.class);
    }

    @Override
    public void fetch(@Nullable String queryType) {
        Call<Search> call = client.search(queryType);

        call.enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {
                if (response.isSuccessful()) {
                    // We just want the response. Don't do any conversions here.
                    // Cache response as we might need it later
                    cachedSearch = response.body();
                    onFetched(cachedSearch);
                } else {
                    // We just want the error. Don't do any conversions here.
                    onError();
                }
            }

            @Override
            public void onFailure(Call<Search> call, Throwable t) {
                // We just want the error. Don't do any conversions here.
                onError();
            }
        });
    }

    @Override
    public void fetch() {
        fetch(null);
    }

    @Override
    public void onFetched(Search search) {
        feedPresenter.onLoadSuccess(search.getResponse().getResults());
    }

    @Override
    public void fetchCached(@Nullable String queryType) {
        if (cachedSearch == null) {
            fetch(queryType);
        } else {
            onFetched(cachedSearch);
        }
    }

    @Override
    public void onError() {
        feedPresenter.onLoadError();
    }

    @Override
    public void onDestroy() {
        cachedSearch = null;
    }
}