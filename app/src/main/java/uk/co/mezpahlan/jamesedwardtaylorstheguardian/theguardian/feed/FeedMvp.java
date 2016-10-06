package uk.co.mezpahlan.jamesedwardtaylorstheguardian.theguardian.feed;

import android.support.annotation.NonNull;

import java.util.List;

import uk.co.mezpahlan.jamesedwardtaylorstheguardian.base.BaseMvp;
import uk.co.mezpahlan.jamesedwardtaylorstheguardian.data.model.Response;
import uk.co.mezpahlan.jamesedwardtaylorstheguardian.data.model.Result;

/**
 * MVP definitions for RSS Feed.
 */
public interface FeedMvp {
    interface View extends BaseMvp.LCEView <List<Result>> {
        void showGuardianArticle();
    }

    interface Presenter extends BaseMvp.Presenter <List<Result>> {
        void onConfigurationChanged(View view);
        void onSelectResult(@NonNull Result result);
    }

    interface ModelInteractor extends BaseMvp.ModelInteractor <Response> {
        void fetchCached();
    }
}