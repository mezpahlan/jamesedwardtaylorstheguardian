package uk.co.mezpahlan.oldtimerag.theguardian.feed

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_theguardian_feed.*
import uk.co.mezpahlan.oldtimerag.R
import uk.co.mezpahlan.oldtimerag.data.model.search.Result
import uk.co.mezpahlan.oldtimerag.theguardian.article.ArticleFragment

/**
 * UI Controller for TheGuardian.Feed.
 */
class FeedFragment : Fragment(), FeedMvp.View {

    private lateinit var listAdapter: FeedRecyclerViewAdapter
    private lateinit var presenter: FeedMvp.Presenter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var viewModel: FeedViewModel

    /**
     * Listener for clicks on items in the RecyclerView.
     */
    private val resultClickListener = object : ResultClickListener {
        override fun onResultClick(result: Result) {
            presenter.onSelectResult(result)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(requireActivity()).get(FeedViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val root = inflater.inflate(R.layout.fragment_theguardian_feed, container, false)
        val recyclerView = root.findViewById<View>(R.id.recyclerView) as RecyclerView

        listAdapter = FeedRecyclerViewAdapter()
        listAdapter.setClickListener(resultClickListener)
        recyclerView.adapter = listAdapter

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Pull-to-refresh
        swipeRefreshLayout = root.findViewById<View>(R.id.contentView) as SwipeRefreshLayout
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(requireContext(), R.color.colorAccent),
                ContextCompat.getColor(requireContext(), R.color.colorPrimary),
                ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark))
        swipeRefreshLayout.setOnRefreshListener {

            presenter.load(viewModel.feedType.name)

        }

        return root
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

    override fun updateContent(viewType: List<Result>) {
        listAdapter.updateResults(viewType)
        listAdapter.notifyDataSetChanged()
    }

    override fun showGuardianArticle(id: String, title: String) {
        val articleFragment = ArticleFragment.newInstance(id, title)
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

    override fun onDestroy() {
        val isConfigChanging = requireActivity().isChangingConfigurations
        presenter.onDestroy(isConfigChanging)
        super.onDestroy()
    }

    interface ResultClickListener {
        fun onResultClick(result: Result)
    }
}