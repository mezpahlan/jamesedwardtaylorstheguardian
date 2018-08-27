package uk.co.mezpahlan.oldtimerag.theguardian.feed;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import uk.co.mezpahlan.oldtimerag.theguardian.data.models.search.Result;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Unit Tests for TheGuardian.Feed.Presenter
 */
@RunWith(MockitoJUnitRunner.class)
public class FeedPresenterTest {

    @Mock
    private Feed.View view;
    @Mock
    private Feed.ModelInteractor modelInteractor;

    @Test
    public void presenter_load() {
        // Given
        Feed.Presenter presenter = new FeedPresenter(view, modelInteractor);

        // When
        presenter.load();

        // Then
        verify(view).showLoading();
        verify(modelInteractor).fetch();
    }

    @Test
    public void presenter_load_nonNullQueryType() {
        // Given
        Feed.Presenter presenter = new FeedPresenter(view, modelInteractor);
        String queryType = "test";

        // When
        presenter.load(queryType);

        // Then
        verify(view).showLoading();
        verify(modelInteractor).fetch(queryType);
    }

    @Test
    public void presenter_onLoadSuccess_emptyResultList() {
        // Given
        Feed.Presenter presenter = new FeedPresenter(view, modelInteractor);
        List<Result> resultList = new ArrayList<>(0);

        // When
        presenter.onLoadSuccess(resultList);

        // Then
        verify(view).updateContent(resultList);
        verify(view).showContent();
    }

    @Test
    public void presenter_onLoadError() {
        // Given
        Feed.Presenter presenter = new FeedPresenter(view, modelInteractor);

        // When
        presenter.onLoadError();

        // Then
        verify(view).showError();
    }

    @Test
    public void presenter_onDestroy_configurationChanging() {
        // Given
        Feed.Presenter presenter = new FeedPresenter(view, modelInteractor);

        // When
        presenter.onDestroy(true);

        // Then
        verify(modelInteractor, never()).onDestroy();
    }

    @Test
    public void presenter_onDestroy_configurationNotChanging() {
        // Given
        Feed.Presenter presenter = new FeedPresenter(view, modelInteractor);

        // When
        presenter.onDestroy(false);

        // Then
        verify(modelInteractor, times(1)).onDestroy();
    }

    @Test
    public void presenter_onConfigurationChanged_nonNullQueryType() {
        // Given
        Feed.Presenter presenter = new FeedPresenter(view, modelInteractor);
        String queryType = "test";


        // When
        presenter.onConfigurationChanged(view, queryType);

        // Then
        verify(view).showLoading();
        verify(modelInteractor).fetchCached(queryType);
    }

    @Test
    public void presenter_onSelectResult_nonNullResult() {
        // Given
        Feed.Presenter presenter = new FeedPresenter(view, modelInteractor);
        Result result = new Result();

        // When
        presenter.onSelectItem(result);

        // Then
        verify(view).showItem(result.getId(), result.getWebTitle());
    }

}