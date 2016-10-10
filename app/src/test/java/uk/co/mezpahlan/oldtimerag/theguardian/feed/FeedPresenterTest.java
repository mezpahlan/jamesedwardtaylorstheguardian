package uk.co.mezpahlan.oldtimerag.theguardian.feed;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import uk.co.mezpahlan.oldtimerag.data.model.search.Result;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Unit Tests for TheGuardian.Feed.Presenter
 */
@RunWith(MockitoJUnitRunner.class)
public class FeedPresenterTest {

    @Mock FeedMvp.View view;
    @Mock FeedMvp.ModelInteractor modelInteractor;

    @Test
    public void presenter_load() throws Exception {
        // Given
        FeedMvp.Presenter presenter = new FeedPresenter(view, modelInteractor);

        // When
        presenter.load();

        // Then
        verify(view).showLoading();
        verify(modelInteractor).fetch();
    }

    @Test
    public void presenter_load_nonNullQueryType() throws Exception {
        // Given
        FeedMvp.Presenter presenter = new FeedPresenter(view, modelInteractor);
        String queryType = "test";

        // When
        presenter.load(queryType);

        // Then
        verify(view).showLoading();
        verify(modelInteractor).fetch(queryType);
    }

    @Test
    public void presenter_onLoadSuccess_emptyResultList() throws Exception {
        // Given
        FeedMvp.Presenter presenter = new FeedPresenter(view, modelInteractor);
        List<Result> resultList = new ArrayList<>(0);

        // When
        presenter.onLoadSuccess(resultList);

        // Then
        verify(view).updateContent(resultList);
        verify(view).showContent();
    }

    @Test
    public void presenter_onLoadError() throws Exception {
        // Given
        FeedMvp.Presenter presenter = new FeedPresenter(view, modelInteractor);

        // When
        presenter.onLoadError();

        // Then
        verify(view).showError();
    }

    @Test
    public void presenter_onDestroy_configurationChanging() throws Exception {
        // Given
        FeedMvp.Presenter presenter = new FeedPresenter(view, modelInteractor);

        // When
        presenter.onDestroy(true);

        // Then
        verify(modelInteractor, never()).onDestroy();
    }

    @Test
    public void presenter_onDestroy_configurationNotChanging() throws Exception {
        // Given
        FeedMvp.Presenter presenter = new FeedPresenter(view, modelInteractor);

        // When
        presenter.onDestroy(false);

        // Then
        verify(modelInteractor, times(1)).onDestroy();
    }

    @Test
    public void presenter_onConfigurationChanged_nonNullQueryType() throws Exception {
        // Given
        FeedMvp.Presenter presenter = new FeedPresenter(view, modelInteractor);
        String queryType = "test";


        // When
        presenter.onConfigurationChanged(view, queryType);

        // Then
        verify(view).showLoading();
        verify(modelInteractor).fetchCached(queryType);
    }

    @Test
    public void presenter_onSelectResult_nonNullResult() throws Exception {
        // Given
        FeedMvp.Presenter presenter = new FeedPresenter(view, modelInteractor);
        Result result = new Result();

        // When
        presenter.onSelectResult(result);

        // Then
        verify(view).showGuardianArticle(result.getId(), result.getWebTitle());
    }

}