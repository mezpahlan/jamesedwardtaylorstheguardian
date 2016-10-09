package uk.co.mezpahlan.jamesedwardtaylorstheguardian.theguardian.feed;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import uk.co.mezpahlan.jamesedwardtaylorstheguardian.R;

/**
 * Activity for TheGuardian.Feed. Sets up the fragment and a custom Toolbar.
 */
public class FeedActivity extends AppCompatActivity {

    private boolean isTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theguardian_feed);

        setupToolbar();
        setupTabNavigation();
        determinePaneLayout();

        if (null == savedInstanceState) {
            initFragment(FeedFragment.newInstance(isTwoPane, null));
        }

    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupTabNavigation() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        final TabLayout.Tab homeTab = tabLayout.newTab();
        final TabLayout.Tab articlesTab = tabLayout.newTab();
        final TabLayout.Tab liveBlogTab = tabLayout.newTab();

        homeTab.setText("All");
        articlesTab.setText("Articles");
        liveBlogTab.setText("Live Blog");

        tabLayout.addTab(homeTab, 0);
        tabLayout.addTab(articlesTab, 1);
        tabLayout.addTab(liveBlogTab, 2);

        tabLayout.setTabTextColors(ContextCompat.getColorStateList(this, R.color.colorAccent));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.colorAccent));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        initFragment(FeedFragment.newInstance(isTwoPane, null));
                        break;
                    case 1:
                        initFragment(FeedFragment.newInstance(isTwoPane, "article"));
                        break;
                    case 2:
                        initFragment(FeedFragment.newInstance(isTwoPane, "liveblog"));
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Do nothing
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Do nothing. Swipe to refresh can refresh
            }
        });
    }

    private void determinePaneLayout() {
        // Try and find the "Detail" frame view in the "Master" view to determine which layout we are in.
        // If we find it then we assume we are in a two-pane layout with "Master-Detail".
        // If we don't then we assume we are in a single-pane mode with "Master" and "Detail" in different activities.
        View detailFrameView = findViewById(R.id.article_frame_view);
        if (detailFrameView != null) {
            isTwoPane = true;
        } else {
            isTwoPane = false;
        }
    }

    private void initFragment(Fragment feedFragment) {
        // Add the FeedFragment to the layout
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.feed_frame_view, feedFragment);
        transaction.commit();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}