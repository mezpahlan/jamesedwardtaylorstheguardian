package uk.co.mezpahlan.jamesedwardtaylorstheguardian.theguardian.feed;

import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.List;

import uk.co.mezpahlan.jamesedwardtaylorstheguardian.data.model.Result;

/**
 * Presenter for TheGuardian.Feed.
 */
public class FeedPresenter implements FeedMvp.Presenter {
    private WeakReference<FeedMvp.View> itemView;
    private final FeedMvp.ModelInteractor modelInteractor;

    public FeedPresenter(@NonNull FeedMvp.View feedView) {
        this.itemView = new WeakReference<>(feedView);
        modelInteractor = new FeedModelInteractor(this);
    }

    @Override
    public void load(boolean isUserDrive) {
        itemView.get().showLoading(isUserDrive);
        modelInteractor.fetch();
    }

    @Override
    public void onLoadSuccess(@NonNull List<Result> resultList) {
        itemView.get().updateContent(resultList);
        itemView.get().showContent();
    }

    @Override
    public void onLoadError() {
        itemView.get().showError();
    }

    @Override
    public void onDestroy(boolean isConfigChanging) {

    }

    @Override
    public void onConfigurationChanged(FeedMvp.View view) {
        itemView = new WeakReference<>(view);
        itemView.get().showLoading(false);
    }

    @Override
    public void onSelectResult(int position) {
        itemView.get().showGuardianArticle(position);
    }
}