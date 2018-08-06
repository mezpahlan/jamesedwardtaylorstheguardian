package uk.co.mezpahlan.oldtimerag.theguardian.feed

import uk.co.mezpahlan.oldtimerag.base.BaseMvp
import uk.co.mezpahlan.oldtimerag.data.model.search.Result
import uk.co.mezpahlan.oldtimerag.data.model.search.Search

/**
 * MVP definitions for TheGuardian.Feed
 */
interface FeedMvp {
    interface View : BaseMvp.LCEView<List<Result>> {
        fun showGuardianArticle(id: String, title: String)
    }

    interface Presenter : BaseMvp.Presenter<List<Result>> {
        fun load(queryType: String)
        fun onConfigurationChanged(view: View, queryType: String?)
        fun onSelectResult(result: Result)
    }

    interface ModelInteractor : BaseMvp.ModelInteractor<Search> {
        fun fetch(queryType: String?)
        fun fetchCached(queryType: String?)
    }
}