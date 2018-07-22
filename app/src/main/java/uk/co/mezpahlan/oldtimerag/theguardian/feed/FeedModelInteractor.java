package uk.co.mezpahlan.oldtimerag.theguardian.feed;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import uk.co.mezpahlan.oldtimerag.data.GuardianOpenPlatformClient;
import uk.co.mezpahlan.oldtimerag.data.GuardianOpenPlatformServiceGenerator;
import uk.co.mezpahlan.oldtimerag.data.model.search.Search;

/**
 * ModelInteractor for TheGuardian.Feed. Part of the Model layer.
 */
public class FeedModelInteractor implements FeedMvp.ModelInteractor {

    private FeedMvp.Presenter feedPresenter;
    private GuardianOpenPlatformClient client;
    private Search cachedSearch;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public FeedModelInteractor(FeedMvp.Presenter feedPresenter) {
        this.feedPresenter = feedPresenter;
        client = GuardianOpenPlatformServiceGenerator.createSearchService(GuardianOpenPlatformClient.class);
    }

    @VisibleForTesting
    public FeedModelInteractor(FeedMvp.Presenter feedPresenter, GuardianOpenPlatformClient client) {
        this.feedPresenter = feedPresenter;
        this.client = client;
    }

    @Override
    public void fetch(@Nullable String queryType) {
        final Disposable disposable = client.search(queryType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onFetched, throwable -> onError());

        compositeDisposable.add(disposable);
    }

    @Override
    public void fetch() {
        fetch(null);
    }

    @Override
    public void onFetched(Search search) {
        cachedSearch = search;
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
        compositeDisposable.dispose();
    }
}