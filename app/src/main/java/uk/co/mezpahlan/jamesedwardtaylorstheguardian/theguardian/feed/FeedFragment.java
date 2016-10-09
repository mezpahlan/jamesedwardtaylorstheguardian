package uk.co.mezpahlan.jamesedwardtaylorstheguardian.theguardian.feed;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import uk.co.mezpahlan.jamesedwardtaylorstheguardian.R;
import uk.co.mezpahlan.jamesedwardtaylorstheguardian.base.StateMaintainer;
import uk.co.mezpahlan.jamesedwardtaylorstheguardian.data.model.search.Result;
import uk.co.mezpahlan.jamesedwardtaylorstheguardian.theguardian.article.ArticleActivity;
import uk.co.mezpahlan.jamesedwardtaylorstheguardian.theguardian.article.ArticleFragment;

/**
 *  Fragment for TheGuardian.Feed. Part of the View Layer.
 */
public class FeedFragment extends Fragment implements FeedMvp.View {

    private static final String TAG = "FeedFragment";
    public static final String ARGUMENT_FEED_IS_TWO_PANE = "FEED_IS_TWO_PANE";
    public static final String ARGUMENT_FEED_TYPE = "FEED_TYPE";

    private StateMaintainer stateMaintainer;
    private FeedRecyclerViewAdapter listAdapter;
    private FeedMvp.Presenter presenter;

    private SwipeRefreshLayout swipeRefreshLayout;
    private View loadingView;
    private View contentView;
    private View errorView;
    private boolean isTwoPane;
    private String type;

    public static FeedFragment newInstance(boolean isTwoPane, @Nullable String type) {
        Bundle arguments = new Bundle();
        arguments.putBoolean(ARGUMENT_FEED_IS_TWO_PANE, isTwoPane);
        arguments.putString(ARGUMENT_FEED_TYPE, type);

        FeedFragment fragment = new FeedFragment();
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isTwoPane = getArguments().getBoolean(ARGUMENT_FEED_IS_TWO_PANE, false);
        type = getArguments().getString(ARGUMENT_FEED_TYPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_theguardian_feed, container, false);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);

        listAdapter = new FeedRecyclerViewAdapter();
        listAdapter.setClickListener(resultClickListener);
        recyclerView.setAdapter(listAdapter);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Pull-to-refresh
        swipeRefreshLayout =
                (SwipeRefreshLayout) root.findViewById(R.id.content_view);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (type == null) {
                    presenter.load();
                } else {
                    presenter.load(type);
                }
            }
        });

        return root;
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadingView = view.findViewById(R.id.loading_view);
        contentView = view.findViewById(R.id.content_view);
        errorView = view.findViewById(R.id.error_view);
    }

    @Override
    public void onStart() {
        super.onStart();
        setupStateMaintainer();
        checkForRetainedState();
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

    private void initialise(FeedMvp.View view) throws InstantiationException, IllegalAccessException{
        presenter = new FeedPresenter(view);
        stateMaintainer.put(FeedMvp.Presenter.class.getSimpleName(), presenter);
        if (type == null) {
            presenter.load();
        } else {
            presenter.load(type);
        }
    }

    private void reinitialise(FeedMvp.View view) throws InstantiationException, IllegalAccessException {
        presenter = stateMaintainer.get(FeedMvp.Presenter.class.getSimpleName());

        if (presenter == null) {
            // If we can't find a presenter assume that its not there and initialise it again.
            initialise(view);
        } else {
            // Otherwise tell it that the configuration has changed
            presenter.onConfigurationChanged(view, type);
        }
    }

    @Override
    public void showLoading() {
        loadingView.setVisibility(View.VISIBLE);
        contentView.setVisibility(View.INVISIBLE);
        errorView.setVisibility(View.GONE);
    }

    @Override
    public void showContent() {
        contentView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError() {
        errorView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
        contentView.setVisibility(View.GONE);
    }

    @Override
    public void updateContent(List<Result> resultList) {
        listAdapter.updateResults(resultList);
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void showGuardianArticle(String articleId, String articleTitle) {
        if (isTwoPane) {
            ArticleFragment articleFragment = ArticleFragment.newInstance(articleId, articleTitle);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.article_frame_view, articleFragment);
            transaction.commit();
        } else {
            // Naive implementation using an intent to move between activities
            Intent intent = new Intent(getActivity(), ArticleActivity.class);
            intent.putExtra(ArticleActivity.EXTRA_ARTICLE_ID, articleId);
            intent.putExtra(ArticleActivity.EXTRA_ARTICLE_TITLE, articleTitle);
            startActivity(intent);
        }
    }

    @Override
    public void onDestroy() {
        boolean isConfigChanging = getActivity().isChangingConfigurations();
        presenter.onDestroy(isConfigChanging);
        super.onDestroy();
    }

    /**
     * Listener for clicks on items in the RecyclerView.
     */
    ResultClickListener resultClickListener = new ResultClickListener() {
        @Override
        public void onResultClick(Result result) {
            presenter.onSelectResult(result);
        }
    };

    public interface ResultClickListener {
        void onResultClick(Result result);
    }
}