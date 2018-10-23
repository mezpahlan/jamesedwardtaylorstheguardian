package uk.co.mezpahlan.oldtimerag.theguardian.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_theguardian_feed.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import uk.co.mezpahlan.oldtimerag.R
import uk.co.mezpahlan.oldtimerag.base.LceType
import uk.co.mezpahlan.oldtimerag.base.LceView
import uk.co.mezpahlan.oldtimerag.theguardian.viewmodels.SharedViewModel

/**
 * UI Controller for TheGuardian.Feed.
 */
abstract class FeedFragment : Fragment(), LceView {
    protected val viewModel: SharedViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_theguardian_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FeedItemAdapter {
            viewModel.articleId.value = it.id
            findNavController().navigate(R.id.articleFragment)
        }

        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Pull-to-refresh
        swipeRefreshView.setColorSchemeColors(
                ContextCompat.getColor(requireContext(), R.color.primaryDarkColor),
                ContextCompat.getColor(requireContext(), R.color.primaryColor),
                ContextCompat.getColor(requireContext(), R.color.primaryDarkColor))

        swipeRefreshView.setOnRefreshListener {
            viewModel.loadFeed()
        }

        subscribeUi(adapter)

        viewModel.loadFeed()
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
        loadingView?.visibility = View.VISIBLE
        swipeRefreshView?.visibility = View.INVISIBLE
        errorView?.visibility = View.GONE
    }

    override fun showContent() {
        swipeRefreshView?.visibility = View.VISIBLE
        loadingView?.visibility = View.GONE
        errorView?.visibility = View.GONE
        swipeRefreshView?.isRefreshing = false
    }

    override fun showError() {
        errorView?.visibility = View.VISIBLE
        loadingView?.visibility = View.GONE
        swipeRefreshView?.visibility = View.GONE
    }
}