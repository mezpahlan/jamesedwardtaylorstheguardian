package uk.co.mezpahlan.oldtimerag.theguardian.feed

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_theguardian_feed.*
import org.koin.android.viewmodel.ext.android.viewModel
import uk.co.mezpahlan.oldtimerag.R
import uk.co.mezpahlan.oldtimerag.base.LceType
import uk.co.mezpahlan.oldtimerag.base.LceView
import uk.co.mezpahlan.oldtimerag.theguardian.article.ArticleFragment
import uk.co.mezpahlan.oldtimerag.theguardian.viewmodels.SharedViewModel

/**
 * UI Controller for TheGuardian.Feed.
 */
class FeedFragment : Fragment(), LceView {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val viewModel: SharedViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_theguardian_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FeedItemAdapter {
            viewModel.selected.value = it.id
            navigateToArticle()
        }

        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Pull-to-refresh
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(requireContext(), R.color.colorAccent),
                ContextCompat.getColor(requireContext(), R.color.colorPrimary),
                ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark))
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadFeed(viewModel.feedType)
        }

        subscribeUi(adapter)
    }

    private fun subscribeUi(adapter: FeedItemAdapter) {
        viewModel.lceType.observe(requireActivity(), Observer {
            when (it) {
                LceType.LOADING -> showLoading()
                LceType.CONTENT -> showContent()
                LceType.ERROR -> showError()
                null -> showError()
            }
        })

        viewModel.items.observe(requireActivity(), Observer {
            if (it != null && it.isNotEmpty()) {
                adapter.submitList(it)
            }
        })

    }

    override fun showLoading() {
        loadingView.visibility = View.VISIBLE
        contentView.visibility = View.INVISIBLE
        errorView.visibility = View.GONE
    }

    override fun showContent() {
        contentView.visibility = View.VISIBLE
        loadingView.visibility = View.GONE
        errorView.visibility = View.GONE
        swipeRefreshLayout.isRefreshing = false
    }

    override fun showError() {
        errorView.visibility = View.VISIBLE
        loadingView.visibility = View.GONE
        contentView.visibility = View.GONE
    }

    fun navigateToArticle() {
        val articleFragment = ArticleFragment()
        when {
            viewModel.isTwoPane -> requireFragmentManager()
                    .beginTransaction()
                    .replace(R.id.articleFrameView, articleFragment)
                    .commit()
            else -> requireFragmentManager()
                    .beginTransaction()
                    .replace(R.id.feedFrameView, articleFragment)
                    .commit()
        }
    }
}