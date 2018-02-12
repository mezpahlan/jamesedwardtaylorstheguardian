package uk.co.mezpahlan.oldtimerag.theguardian.article;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import retrofit2.Call;
import uk.co.mezpahlan.oldtimerag.data.GuardianOpenPlatformClient;
import uk.co.mezpahlan.oldtimerag.data.model.singleitem.Content;
import uk.co.mezpahlan.oldtimerag.data.model.singleitem.Fields;
import uk.co.mezpahlan.oldtimerag.data.model.singleitem.Response;
import uk.co.mezpahlan.oldtimerag.data.model.singleitem.SingleItem;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * Unit Tests for TheGuardian.Article.ModelInteractor
 */
@RunWith(MockitoJUnitRunner.class)
public class ArticleModelInteractorTest {

    @Mock
    private ArticleMvp.Presenter presenter;

    @Mock
    private GuardianOpenPlatformClient client;

    @Mock
    private Call<SingleItem> call;

    @Mock
    private SingleItem singleItem;

    @Mock
    private Response response;

    @Mock
    private Content content;

    @Mock
    private Fields fields;

    @Test
    public void modelInteractor_fetch_nonNullId() throws Exception {
        // Given
        ArticleMvp.ModelInteractor modelInteractor = new ArticleModelInteractor(presenter, client);
        String id = "test";
        given(client.singleItem(id)).willReturn(call);

        // When
        modelInteractor.fetch(id);

        // Then
        verify(client).singleItem(id);
    }

    @Test
    public void modelInteractor_onFetched_singleItem() throws Exception {
        // Given
        ArticleMvp.ModelInteractor modelInteractor = new ArticleModelInteractor(presenter, client);
        given(singleItem.getResponse()).willReturn(response);
        given(response.getContent()).willReturn(content);
        given(content.getFields()).willReturn(fields);
        given(fields.getBody()).willReturn("test");

        // When
        modelInteractor.onFetched(singleItem);

        // Then
        verify(presenter).onLoadSuccess(singleItem.getResponse().getContent().getFields().getBody());
    }

    @Test
    public void modelInteractor_fetchCached_nullId() throws Exception {
        // Given
        ArticleMvp.ModelInteractor modelInteractor = spy(new ArticleModelInteractor(presenter, client));
        willDoNothing().given(modelInteractor).fetch((String) isNull());

        // When
        modelInteractor.fetchCached(null);

        // Then
        verify(modelInteractor).fetch((String) isNull());
    }

    @Test
    public void modelInteractor_fetchCached_nonNullId() throws Exception {
        // Given
        ArticleMvp.ModelInteractor modelInteractor = spy(new ArticleModelInteractor(presenter, client));
        willDoNothing().given(modelInteractor).fetch(anyString());
        String id = "test";

        // When
        modelInteractor.fetchCached(id);

        // Then
        verify(modelInteractor).fetch(id);
    }

    @Test
    public void modelInteractor_onError() throws Exception {
        // Given
        ArticleMvp.ModelInteractor modelInteractor = new ArticleModelInteractor(presenter, client);

        // When
        modelInteractor.onError();

        // Then
        verify(presenter).onLoadError();
    }

    @Test
    @Ignore
    public void modelInteractor_onDestroy() throws Exception {
        // TODO: method cleans up a private member of the class. Not sure how to do this.
    }
}