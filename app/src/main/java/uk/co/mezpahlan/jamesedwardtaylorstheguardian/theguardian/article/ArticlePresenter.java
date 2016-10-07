package uk.co.mezpahlan.jamesedwardtaylorstheguardian.theguardian.article;

import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
/**
 * Presenter for TheGuardian.Article.
 */
public class ArticlePresenter implements ArticleMvp.Presenter {
    private WeakReference<ArticleMvp.View> articleView;
    private final ArticleMvp.ModelInteractor modelInteractor;

    public ArticlePresenter(@NonNull ArticleMvp.View articleView) {
        this.articleView = new WeakReference<>(articleView);
        modelInteractor = new ArticleModelInteractor(this);
    }

    @Override
    public void load(String id) {
        articleView.get().showLoading(false);
        modelInteractor.fetch(id);
    }

    @Override
    public void onLoadSuccess(String articleHtml) {
        articleView.get().updateContent(articleHtml);
        articleView.get().showContent();
    }

    @Override
    public void onLoadError() {
        articleView.get().showError();
    }

    @Override
    public void onDestroy(boolean isConfigChanging) {

    }

    @Override
    public void onConfigurationChanged(ArticleMvp.View view, String id) {
        articleView = new WeakReference<>(view);
        articleView.get().showLoading(false);
        modelInteractor.fetchCached(id);
    }
}