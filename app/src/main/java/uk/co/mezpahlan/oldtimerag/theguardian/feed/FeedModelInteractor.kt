package uk.co.mezpahlan.oldtimerag.theguardian.feed

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import uk.co.mezpahlan.oldtimerag.data.GuardianOpenPlatformClient
import uk.co.mezpahlan.oldtimerag.data.model.search.Search

/**
 * ModelInteractor for TheGuardian.Feed.
 */
class FeedModelInteractor(private val feedPresenter: FeedMvp.Presenter, private val client: GuardianOpenPlatformClient) : FeedMvp.ModelInteractor {

    private var cachedSearch: Search? = null // TODO: Use Room to cache this result?
    private val compositeDisposable = CompositeDisposable()

    override fun fetch(queryType: String?) {
        val disposable = client.search(queryType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ this.onFetched(it) }, { _ -> onError() })

        compositeDisposable.add(disposable)
    }

    override fun fetch() {
        fetch(null)
    }

    override fun onFetched(modelType: Search) {
        cachedSearch = modelType
        feedPresenter.onLoadSuccess(modelType.response!!.results) // TODO: What's this? response
    }

    override fun fetchCached(queryType: String?) {
        if (cachedSearch == null) {
            fetch(queryType)
        } else {
            cachedSearch?.let { onFetched(it) }
        }
    }

    override fun onError() {
        feedPresenter.onLoadError()
    }

    override fun onDestroy() {
        cachedSearch = null
        compositeDisposable.dispose()
    }
}