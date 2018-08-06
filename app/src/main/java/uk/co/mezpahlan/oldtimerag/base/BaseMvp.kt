package uk.co.mezpahlan.oldtimerag.base

/**
 * Base MVP definitions for operations that all implementations will use.
 */
interface BaseMvp {
    interface LCEView<VT> {
        fun showLoading()
        fun showContent()
        fun showError()
        fun updateContent(viewType: VT)
    }

    interface Presenter<PT> {
        fun load()
        fun onLoadSuccess(presenterType: PT)
        fun onLoadError()
        fun onDestroy(isConfigChanging: Boolean)
    }

    interface ModelInteractor<MT> {
        fun fetch()
        fun onFetched(modelType: MT)
        fun onError()
        fun onDestroy()
    }
}