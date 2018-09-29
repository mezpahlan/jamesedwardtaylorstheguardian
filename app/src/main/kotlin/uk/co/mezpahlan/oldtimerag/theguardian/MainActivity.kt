package uk.co.mezpahlan.oldtimerag.theguardian

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import uk.co.mezpahlan.oldtimerag.R
import uk.co.mezpahlan.oldtimerag.theguardian.feed.FeedFragment
import uk.co.mezpahlan.oldtimerag.theguardian.feed.FeedType
import uk.co.mezpahlan.oldtimerag.theguardian.viewmodels.SharedViewModel

/**
 * Main UI Controller for the application.
 *
 * TODO: Use navigation components.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private val viewModel: SharedViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theguardian_feed)

        setupToolbar()
        setupTabNavigation()
        determinePaneLayout()
        subscribeUi()

        navigateToFeed()
    }

    private fun setupToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    private fun setupTabNavigation() {
        tabLayout = findViewById(R.id.tabs)

        val homeTab = tabLayout.newTab().setCustomView(R.layout.tab_text)
        val articlesTab = tabLayout.newTab().setCustomView(R.layout.tab_text)
        val liveBlogTab = tabLayout.newTab().setCustomView(R.layout.tab_text)

        homeTab.text = "All"
        articlesTab.text = "Articles"
        liveBlogTab.text = "Live Blog"

        tabLayout.addTab(homeTab, 0)
        tabLayout.addTab(articlesTab, 1)
        tabLayout.addTab(liveBlogTab, 2)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> viewModel.feedType.value = FeedType.ALL
                    1 -> viewModel.feedType.value = FeedType.ARTICLE
                    2 -> viewModel.feedType.value = FeedType.LIVE_BLOG
                }

                navigateToFeed()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                // Do nothing
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                // Do nothing. Swipe to refresh can refresh
            }
        })
    }

    private fun determinePaneLayout() {
        // Try and find the "Detail" frame view in the "Master" view to determine which layout we are in.
        // If we find it then we assume we are in a two-pane layout with "Master-Detail".
        // If we don't then we assume we are in a single-pane mode with "Master" and "Detail" in different activities.
        val detailFrameView = findViewById<View>(R.id.articleFrameView)
        viewModel.isTwoPane = detailFrameView != null
    }

    private fun subscribeUi() {
        viewModel.article.observe(this, Observer {
            title = it?.title
        })
    }

    private fun navigateToFeed() {
        // Add the FeedFragment to the layout
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.feedFrameView, FeedFragment())
        transaction.commit()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}