package uk.co.mezpahlan.oldtimerag.theguardian.article;

import uk.co.mezpahlan.oldtimerag.base.BaseMvp;
import uk.co.mezpahlan.oldtimerag.data.model.singleitem.SingleItem;

/**
 * MVP definitions for RSS Feed.
 */
public interface ArticleMvp {
    interface View extends BaseMvp.LCEView <String> {
        void setTitle(String title);
    }

    interface Presenter {
        void load(String id);
        void onLoadSuccess(String articleHtml);
        void onLoadError();
        void onDestroy(boolean isConfigChanging);
        void onConfigurationChanged(View view, String id);
    }

    interface ModelInteractor {
        void fetch(String id);
        void fetchCached(String id);
        void onFetched(SingleItem singleItem);
        void onError();
        void onDestroy();
    }
}