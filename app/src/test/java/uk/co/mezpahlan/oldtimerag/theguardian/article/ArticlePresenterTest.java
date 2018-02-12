package uk.co.mezpahlan.oldtimerag.theguardian.article;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Unit Tests for TheGuardian.Feed.Presenter
 */
@RunWith(MockitoJUnitRunner.class)
public class ArticlePresenterTest {

    @Mock
    private ArticleMvp.View view;

    @Mock
    private ArticleMvp.ModelInteractor modelInteractor;

    @Test
    public void presenter_load_id() throws Exception {
        // Given
        ArticleMvp.Presenter presenter = new ArticlePresenter(view, modelInteractor);
        String id = "test";

        // When
        presenter.load(id);

        // Then
        verify(view).showLoading();
        verify(modelInteractor).fetch(id);
    }

    @Test
    public void presenter_onLoadSuccess_articleHtml() throws Exception {
        // Given
        ArticleMvp.Presenter presenter = new ArticlePresenter(view, modelInteractor);
        String articleHtml = "test";
        String webpageHtml = "<!DOCTYPE html> \n" +
                "<html>\n" +
                "<head>\n" +
                "  <meta charset=\"utf-8\">\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "  <link rel=\"stylesheet\" href=\"style.css\">\n" +
                "</head>\n" +
                "<body>" +
                "test" +
                "</body>\n" +
                "</html>";

        // When
        presenter.onLoadSuccess(articleHtml);

        // Then
        verify(view).updateContent(webpageHtml);
        verify(view).showContent();
    }

    @Test
    public void presenter_onLoadError() throws Exception {
        // Given
        ArticleMvp.Presenter presenter = new ArticlePresenter(view, modelInteractor);

        // When
        presenter.onLoadError();

        // Then
        verify(view).showError();
    }

    @Test
    public void presenter_onDestroy_configurationChanging() throws Exception {
        // Given
        ArticleMvp.Presenter presenter = new ArticlePresenter(view, modelInteractor);

        // When
        presenter.onDestroy(true);

        // Then
        verify(modelInteractor, never()).onDestroy();
    }

    @Test
    public void presenter_onDestroy_configurationNotChanging() throws Exception {
        // Given
        ArticleMvp.Presenter presenter = new ArticlePresenter(view, modelInteractor);

        // When
        presenter.onDestroy(false);

        // Then
        verify(modelInteractor, times(1)).onDestroy();
    }

    @Test
    public void presenter_onConfigurationChanged_nonNullId() throws Exception {
        // Given
        ArticleMvp.Presenter presenter = new ArticlePresenter(view, modelInteractor);
        String id = "test";


        // When
        presenter.onConfigurationChanged(view, id);

        // Then
        verify(view).showLoading();
        verify(modelInteractor).fetchCached(id);
    }

}