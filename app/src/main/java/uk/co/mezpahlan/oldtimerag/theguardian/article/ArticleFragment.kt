package uk.co.mezpahlan.oldtimerag.theguardian.article

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_theguardian_article.*
import org.koin.android.viewmodel.ext.android.viewModel
import uk.co.mezpahlan.oldtimerag.R
import uk.co.mezpahlan.oldtimerag.base.LceType
import uk.co.mezpahlan.oldtimerag.base.LceView
import uk.co.mezpahlan.oldtimerag.theguardian.viewmodels.SharedViewModel

/**
 * UI Controller for TheGuardian.Article.
 */
class ArticleFragment : Fragment(), LceView {
    private val viewModel: SharedViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_theguardian_article, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configure the webView with sensible defaults
        webView.settings.loadsImagesAutomatically = true
        webView.settings.javaScriptEnabled = false
        webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        webView.webViewClient = ArticleWebViewClient(this)

        subscribeUi()
    }

    private fun subscribeUi() {
        viewModel.lceType.observe(requireActivity(), Observer {
            when (it) {
                LceType.LOADING -> showLoading()
                LceType.CONTENT -> showContent()
                LceType.ERROR -> showError()
                null -> showError()
            }
        })

        viewModel.article.observe(requireActivity(), Observer { item ->
            item?.let {
                requireActivity().title = it.title
                webView.loadDataWithBaseURL("file:///android_asset/", it.pageData, "text/html", "UTF-8", null)
            }
        })
    }

    override fun showLoading() {
        loadingView.visibility = View.VISIBLE
        webView.visibility = View.INVISIBLE
        errorView.visibility = View.GONE
    }

    override fun showContent() {
        webView.visibility = View.VISIBLE
        loadingView.visibility = View.GONE
        errorView.visibility = View.GONE
    }

    override fun showError() {
        errorView.visibility = View.VISIBLE
        webView.visibility = View.GONE
        loadingView.visibility = View.GONE
    }
}