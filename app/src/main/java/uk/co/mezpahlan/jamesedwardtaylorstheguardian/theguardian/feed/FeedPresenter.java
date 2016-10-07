package uk.co.mezpahlan.jamesedwardtaylorstheguardian.theguardian.feed;

import android.support.annotation.NonNull;

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

    @Override
    public void load(boolean isUserDrive) {
        feedView.get().showLoading(isUserDrive);
        modelInteractor.fetch();
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

    }

    @Override
    public void onConfigurationChanged(FeedMvp.View view) {
        feedView = new WeakReference<>(view);
        feedView.get().showLoading(false);
        modelInteractor.fetchCached();
    }

    @Override
    public void onSelectResult(int position) {
        feedView.get().showGuardianArticle(position);
    }
}