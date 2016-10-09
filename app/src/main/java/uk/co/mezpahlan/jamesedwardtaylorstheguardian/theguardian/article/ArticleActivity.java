package uk.co.mezpahlan.jamesedwardtaylorstheguardian.theguardian.article;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import uk.co.mezpahlan.jamesedwardtaylorstheguardian.R;

/**
 * Activity for TheGuardian.Article. Sets up the fragment and a custom Toolbar.
 */
public class ArticleActivity extends AppCompatActivity {

    public static final String EXTRA_ARTICLE_ID = "ARTICLE_URL";
    public static final String EXTRA_ARTICLE_TITLE = "ARTICLE_TITLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theguardian_feed);

        // Get the requested RSS Item Title and Link
        final Intent intent = getIntent();
        String articleId = intent.getStringExtra(EXTRA_ARTICLE_ID);
        String articleTitle = intent.getStringExtra(EXTRA_ARTICLE_TITLE);

        setupToolbar();

        if (null == savedInstanceState) {
            initFragment(ArticleFragment.newInstance(articleId, articleTitle));
        }

    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
