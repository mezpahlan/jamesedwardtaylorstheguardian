package uk.co.mezpahlan.jamesedwardtaylorstheguardian.theguardian.feed;

import java.util.List;

import uk.co.mezpahlan.jamesedwardtaylorstheguardian.base.BaseMvp;
import uk.co.mezpahlan.jamesedwardtaylorstheguardian.data.model.Result;
import uk.co.mezpahlan.jamesedwardtaylorstheguardian.data.model.Search;

/**
 * MVP definitions for TheGuardian.Feed
 */
public interface FeedMvp {
    interface View extends BaseMvp.LCEView <List<Result>> {
        void showGuardianArticle(int position);
    }

    interface Presenter extends BaseMvp.Presenter <List<Result>> {
        void onConfigurationChanged(View view);
        void onSelectResult(int position);
    }

    interface ModelInteractor extends BaseMvp.ModelInteractor <Search> {
        void fetchCached();
    }
}