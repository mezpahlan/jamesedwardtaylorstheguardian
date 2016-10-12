package uk.co.mezpahlan.oldtimerag.theguardian.feed;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import retrofit2.Call;
import uk.co.mezpahlan.oldtimerag.data.GuardianOpenPlatformClient;
import uk.co.mezpahlan.oldtimerag.data.model.search.Response;
import uk.co.mezpahlan.oldtimerag.data.model.search.Result;
import uk.co.mezpahlan.oldtimerag.data.model.search.Search;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * Unit Tests for TheGuardian.Feed.ModelInteractor
 */
@RunWith(MockitoJUnitRunner.class)
public class FeedModelInteractorTest {

    @Mock FeedMvp.Presenter itemPresenter;
    @Mock GuardianOpenPlatformClient client;
    @Mock Call<Search> call;
    @Mock Search search;
    @Mock Response response;
    @Mock java.util.List<Result> results;

    @Test
    public void modelInteractor_fetch_nullQueryType() throws Exception {
        // Given
        FeedMvp.ModelInteractor modelInteractor = new FeedModelInteractor(itemPresenter, client);
        given(client.search(null)).willReturn(call);

        // When
        modelInteractor.fetch();

        // Then
        verify(client).search((String) isNull());
    }

    @Test
    public void modelInteractor_fetch_nonNullQueryType() throws Exception {
        // Given
        FeedMvp.ModelInteractor modelInteractor = new FeedModelInteractor(itemPresenter, client);
        String searchQuery = "test";
        given(client.search(searchQuery)).willReturn(call);

        // When
        modelInteractor.fetch(searchQuery);

        // Then
        verify(client).search(searchQuery);
    }

    @Test
    public void modelInteractor_onFetched_searchResult() throws Exception {
        // Given
        FeedMvp.ModelInteractor modelInteractor = new FeedModelInteractor(itemPresenter, client);
        given(search.getResponse()).willReturn(response);
        given(response.getResults()).willReturn(results);

        // When
        modelInteractor.onFetched(search);

        // Then
        verify(itemPresenter).onLoadSuccess(search.getResponse().getResults());
    }

    @Test
    public void modelInteractor_fetchCached_nullQueryType() throws Exception {
        // Given
        FeedMvp.ModelInteractor modelInteractor = spy(new FeedModelInteractor(itemPresenter, client));
        willDoNothing().given(modelInteractor).fetch((String) isNull());

        // When
        modelInteractor.fetchCached(null);

        // Then
        verify(modelInteractor).fetch((String) isNull());
    }

    @Test
    public void modelInteractor_fetchCached_nonNullQueryType() throws Exception {
        // Given
        FeedMvp.ModelInteractor modelInteractor = spy(new FeedModelInteractor(itemPresenter, client));
        willDoNothing().given(modelInteractor).fetch(anyString());
        String queryType = "test";

        // When
        modelInteractor.fetchCached(queryType);

        // Then
        verify(modelInteractor).fetch(queryType);
    }

    @Test
    public void modelInteractor_onError() throws Exception {
        // Given
        FeedMvp.ModelInteractor modelInteractor = new FeedModelInteractor(itemPresenter, client);

        // When
        modelInteractor.onError();

        // Then
        verify(itemPresenter).onLoadError();
    }

    @Test
    @Ignore
    public void modelInteractor_onDestroy() throws Exception {
        // TODO: method cleans up a private member of the class. Not sure how to do this.
    }
}