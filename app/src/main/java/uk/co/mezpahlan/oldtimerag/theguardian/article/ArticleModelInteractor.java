package uk.co.mezpahlan.oldtimerag.theguardian.article;

import android.support.annotation.VisibleForTesting;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.mezpahlan.oldtimerag.data.GuardianOpenPlatformClient;
import uk.co.mezpahlan.oldtimerag.data.GuardianOpenPlatformServiceGenerator;
import uk.co.mezpahlan.oldtimerag.data.model.singleitem.SingleItem;

/**
 * ModelInteractor for TheGuardian.Article. Part of the Model layer.
 */
public class ArticleModelInteractor implements ArticleMvp.ModelInteractor {

    private final GuardianOpenPlatformClient client;

    private ArticleMvp.Presenter articlePresenter;
    private SingleItem cachedSingleItem;

    public ArticleModelInteractor(ArticleMvp.Presenter articlePresenter) {
        this.articlePresenter = articlePresenter;
        client = GuardianOpenPlatformServiceGenerator.createSingleItemService(GuardianOpenPlatformClient.class);
    }

    @VisibleForTesting
    ArticleModelInteractor(ArticleMvp.Presenter articlePresenter, GuardianOpenPlatformClient client) {
        this.articlePresenter = articlePresenter;
        this.client = client;
    }

    @Override
    public void fetch(String id) {
        Call<SingleItem> call = client.singleItem(id);

        call.enqueue(new Callback<SingleItem>() {
            @Override
            public void onResponse(Call<SingleItem> call, Response<SingleItem> response) {
                if (response.isSuccessful()) {
                    // We just want the response. Don't do any conversions here.
                    // Cache response as we might need it later
                    cachedSingleItem = response.body();
                    onFetched(cachedSingleItem);
                } else {
                    // We just want the error. Don't do any conversions here.
                    onError();
                }
            }

            @Override
            public void onFailure(Call<SingleItem> call, Throwable t) {
                // We just want the error. Don't do any conversions here.
                onError();
            }
        });
    }

    @Override
    public void onFetched(SingleItem singleItem) {
        articlePresenter.onLoadSuccess(singleItem.getResponse().getContent().getFields().getBody());
    }

    @Override
    public void fetchCached(String id) {
        if (cachedSingleItem == null) {
            fetch(id);
        } else {
            onFetched(cachedSingleItem);
        }
    }

    @Override
    public void onError() {
        articlePresenter.onLoadError();
    }

    @Override
    public void onDestroy() {
        cachedSingleItem = null;
    }
}