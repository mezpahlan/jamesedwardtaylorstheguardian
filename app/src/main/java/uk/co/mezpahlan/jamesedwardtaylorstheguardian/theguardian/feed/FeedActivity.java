package uk.co.mezpahlan.jamesedwardtaylorstheguardian.theguardian.feed;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import uk.co.mezpahlan.jamesedwardtaylorstheguardian.R;

/**
 * Activity for TheGuardian.Feed. Sets up the fragment and a custom Toolbar.
 */
public class FeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theguardian_feed);

        setupToolbar();

        if (null == savedInstanceState) {
            initFragment(FeedFragment.newInstance(determinePaneLayout(), null));
        }

    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private boolean determinePaneLayout() {
        boolean isTwoPane = false;
        // Try and find the "Detail" frame view in the "Master" view to determine which layout we are in.
        // If we find it then we assume we are in a two-pane layout with "Master-Detail".
        // If we don't then we assume we are in a single-pane mode with "Master" and "Detail" in different activities.
        View detailFrameView = findViewById(R.id.article_frame_view);
        if (detailFrameView != null) {
            isTwoPane = true;
        }

        return isTwoPane;
    }

    private void initFragment(Fragment feedFragment) {
        // Add the FeedFragment to the layout
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.feed_frame_view, feedFragment);
        transaction.commit();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}