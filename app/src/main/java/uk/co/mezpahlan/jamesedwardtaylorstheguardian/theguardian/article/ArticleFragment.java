package uk.co.mezpahlan.jamesedwardtaylorstheguardian.theguardian.article;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import uk.co.mezpahlan.jamesedwardtaylorstheguardian.R;
import uk.co.mezpahlan.jamesedwardtaylorstheguardian.base.StateMaintainer;
import uk.co.mezpahlan.jamesedwardtaylorstheguardian.theguardian.feed.FeedMvp;

/**
 *  Fragment for TheGuardian.Article. Part of the View Layer.
 */
public class ArticleFragment extends Fragment implements ArticleMvp.View{

    public static final String ARGUMENT_ARTICLE_ID = "ARTICLE_ID";
    public static final String ARGUMENT_ARTICLE_TITLE = "ARTICLE_TITLE";
    private static final String TAG = "ArticleFragment";

    private StateMaintainer stateMaintainer;
    private ArticleMvp.Presenter presenter;

    private View loadingView;
    private WebView webView;
    private String articleId;

    public static ArticleFragment newInstance(String articleId, String articleTitle) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_ARTICLE_ID, articleId);
        arguments.putString(ARGUMENT_ARTICLE_TITLE, articleTitle);

        ArticleFragment fragment = new ArticleFragment();
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        articleId = getArguments().getString(ARGUMENT_ARTICLE_ID);
        setTitle(getArguments().getString(ARGUMENT_ARTICLE_TITLE));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_theguardian_article, container, false);

        return root;
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadingView = view.findViewById(R.id.loading_view);
        webView = (WebView) view.findViewById(R.id.web_view);

        // Configure the webView with sensible defaults
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new ArticleWebViewClient());
    }

    @Override
    public void onStart() {
        super.onStart();
        setupStateMaintainer();
        checkForRetainedState();
    }

    @Override
    public void setTitle(String title) {
        getActivity().setTitle(title);
    }

    private void setupStateMaintainer() {
        if (stateMaintainer == null) {
            stateMaintainer = new StateMaintainer(getActivity().getFragmentManager(), TAG);
        }
    }

    private void checkForRetainedState() {
        try {
            if (stateMaintainer.isFirstTimeIn()) {
                initialise(this);
            } else {
                reinitialise(this);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void initialise(ArticleMvp.View view) throws InstantiationException, IllegalAccessException{
        presenter = new ArticlePresenter(view);
        stateMaintainer.put(FeedMvp.Presenter.class.getSimpleName(), presenter);
        presenter.load(articleId);
    }

    private void reinitialise(ArticleMvp.View view) throws InstantiationException, IllegalAccessException {
        presenter = stateMaintainer.get(FeedMvp.Presenter.class.getSimpleName());

        if (presenter == null) {
            // If we can't find a presenter assume that its not there and initialise it again.
            initialise(view);
        } else {
            // Otherwise tell it that the configuration has changed
            presenter.onConfigurationChanged(view, articleId);
        }
    }

    @Override
    public void showLoading(boolean active) {
        loadingView.setVisibility(View.VISIBLE);
        webView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showContent() {
        webView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void showError() {

    }

    @Override
    public void updateContent(String webPageHtml) {
        webView.loadDataWithBaseURL("file:///android_asset/", webPageHtml, "text/html", "UTF-8", null);
    }
}