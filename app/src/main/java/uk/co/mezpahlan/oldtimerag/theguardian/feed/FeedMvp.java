package uk.co.mezpahlan.oldtimerag.theguardian.feed;

import android.support.annotation.Nullable;

import java.util.List;

import uk.co.mezpahlan.oldtimerag.base.BaseMvp;
import uk.co.mezpahlan.oldtimerag.data.model.search.Result;
import uk.co.mezpahlan.oldtimerag.data.model.search.Search;

/**
 * MVP definitions for TheGuardian.Feed
 */
public interface FeedMvp {
    interface View extends BaseMvp.LCEView <List<Result>> {
        void showGuardianArticle(String id, String title);
    }

    interface Presenter extends BaseMvp.Presenter <List<Result>> {
        void load(String queryType);
        void onConfigurationChanged(View view, @Nullable String queryType);
        void onSelectResult(Result result);
    }

    interface ModelInteractor extends BaseMvp.ModelInteractor <Search> {
        void fetch(@Nullable String queryType);
        void fetchCached(@Nullable String queryType);
    }
}