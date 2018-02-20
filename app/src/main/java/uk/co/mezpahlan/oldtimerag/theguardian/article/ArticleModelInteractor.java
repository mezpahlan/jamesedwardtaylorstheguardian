package uk.co.mezpahlan.oldtimerag.theguardian.article;

import android.support.annotation.VisibleForTesting;

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
        client.singleItem(id)
                .subscribe(this::onFetched, throwable -> onError());
    }

    @Override
    public void onFetched(SingleItem singleItem) {
        cachedSingleItem = singleItem;
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