package uk.co.mezpahlan.jamesedwardtaylorstheguardian.theguardian.feed;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.mezpahlan.jamesedwardtaylorstheguardian.data.GuardianOpenPlatformClient;
import uk.co.mezpahlan.jamesedwardtaylorstheguardian.data.GuardianOpenPlatformServiceGenerator;
import uk.co.mezpahlan.jamesedwardtaylorstheguardian.data.model.Search;

/**
 * ModelInteractor for TheGuardian.Feed. Part of the Model layer.
 */
public class FeedModelInteractor implements FeedMvp.ModelInteractor {

    private static final String TAG = "FeedModelInteractor";

    private FeedMvp.Presenter feedPresenter;
    private GuardianOpenPlatformClient client;
    private Search cachedSearch;

    public FeedModelInteractor(FeedMvp.Presenter itemPresenter) {
        this.feedPresenter = itemPresenter;
        client = GuardianOpenPlatformServiceGenerator.createService(GuardianOpenPlatformClient.class);
    }

    @Override
    public void fetch() {
        Call<Search> call = client.search();

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
    public void onFetched(Search search) {
        feedPresenter.onLoadSuccess(search.getResponse().getResults());
    }

    @Override
    public void fetchCached() {
        if (cachedSearch == null) {
            fetch();
        } else {
            onFetched(cachedSearch);
        }
    }

    @Override
    public void onError() {
        // TODO: Implement me
        Log.e(TAG, "Something went wrong in the FeedModelInteractor");
    }

    @Override
    public void onDestroy() {

    }
}