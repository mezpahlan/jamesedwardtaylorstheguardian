package uk.co.mezpahlan.jamesedwardtaylorstheguardian.theguardian.feed;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import java.lang.ref.WeakReference;
import java.util.List;

import uk.co.mezpahlan.jamesedwardtaylorstheguardian.data.model.search.Result;

/**
 * Presenter for TheGuardian.Feed.
 */
public class FeedPresenter implements FeedMvp.Presenter {
    private WeakReference<FeedMvp.View> feedView;
    private final FeedMvp.ModelInteractor modelInteractor;

    public FeedPresenter(@NonNull FeedMvp.View feedView) {
        this.feedView = new WeakReference<>(feedView);
        modelInteractor = new FeedModelInteractor(this);
    }


    @VisibleForTesting
    FeedPresenter(@NonNull FeedMvp.View feedView, FeedMvp.ModelInteractor modelInteractor) {
        this.feedView = new WeakReference<>(feedView);
        this.modelInteractor = modelInteractor;
    }

    @Override
    public void load() {
        feedView.get().showLoading();
        modelInteractor.fetch();
    }

    @Override
    public void load(String queryType) {
        feedView.get().showLoading();
        modelInteractor.fetch(queryType);
    }

    @Override
    public void onLoadSuccess(@NonNull List<Result> resultList) {
        feedView.get().updateContent(resultList);
        feedView.get().showContent();
    }

    @Override
    public void onLoadError() {
        feedView.get().showError();
    }

    @Override
    public void onDestroy(boolean isConfigChanging) {
        feedView = null;
        if (!isConfigChanging) {
            modelInteractor.onDestroy();
        }
    }

    @Override
    public void onConfigurationChanged(FeedMvp.View view, @Nullable String queryType) {
        feedView = new WeakReference<>(view);
        feedView.get().showLoading();
        modelInteractor.fetchCached(queryType);
    }

    @Override
    public void onSelectResult(Result result) {
        feedView.get().showGuardianArticle(result.getId(), result.getWebTitle());
    }
}