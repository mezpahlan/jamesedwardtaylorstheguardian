package uk.co.mezpahlan.jamesedwardtaylorstheguardian.theguardian.feed;

import android.support.annotation.Nullable;

import java.util.List;

import uk.co.mezpahlan.jamesedwardtaylorstheguardian.base.BaseMvp;
import uk.co.mezpahlan.jamesedwardtaylorstheguardian.data.model.search.Result;
import uk.co.mezpahlan.jamesedwardtaylorstheguardian.data.model.search.Search;

/**
 * MVP definitions for TheGuardian.Feed
 */
public interface FeedMvp {
    interface View extends BaseMvp.LCEView <List<Result>> {
        void showGuardianArticle(String id, String title);
    }

    interface Presenter extends BaseMvp.Presenter <List<Result>> {
        void onConfigurationChanged(View view);
        void onSelectResult(Result result);
    }

    interface ModelInteractor extends BaseMvp.ModelInteractor <Search> {
        void fetch(@Nullable String type);
        void fetchCached();
    }
}