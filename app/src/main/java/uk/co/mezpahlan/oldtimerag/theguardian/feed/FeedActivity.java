package uk.co.mezpahlan.oldtimerag.theguardian.feed;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import uk.co.mezpahlan.oldtimerag.R;

/**
 * Activity for TheGuardian.Feed. Sets up the fragment and a custom Toolbar.
 */
public class FeedActivity extends AppCompatActivity {

    private static final String STATE_SELECTED_TAB = "STATE_SELECTED_TAB";
    private boolean isTwoPane;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
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
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        final TabLayout.Tab homeTab = tabLayout.newTab().setCustomView(R.layout.tab_text);
        final TabLayout.Tab articlesTab = tabLayout.newTab().setCustomView(R.layout.tab_text);
        final TabLayout.Tab liveBlogTab = tabLayout.newTab().setCustomView(R.layout.tab_text);

        homeTab.setText("All");
        articlesTab.setText("Articles");
        liveBlogTab.setText("Live Blog");

        tabLayout.addTab(homeTab, 0);
        tabLayout.addTab(articlesTab, 1);
        tabLayout.addTab(liveBlogTab, 2);

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

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // Save custom values into the bundle
        savedInstanceState.putInt(STATE_SELECTED_TAB, tabLayout.getSelectedTabPosition());

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore a previously selected Tab
        tabLayout.getTabAt(savedInstanceState.getInt(STATE_SELECTED_TAB, 0)).select();
    }
}