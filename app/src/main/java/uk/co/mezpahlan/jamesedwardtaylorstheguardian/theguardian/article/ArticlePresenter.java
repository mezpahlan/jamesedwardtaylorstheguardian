package uk.co.mezpahlan.jamesedwardtaylorstheguardian.theguardian.article;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

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

    @VisibleForTesting
    ArticlePresenter(@NonNull ArticleMvp.View articleView, ArticleMvp.ModelInteractor modelInteractor) {
        this.articleView = new WeakReference<>(articleView);
        this.modelInteractor = modelInteractor;
    }

    @Override
    public void load(String id) {
        articleView.get().showLoading();
        modelInteractor.fetch(id);
    }

    @Override
    public void onLoadSuccess(String articleHtml) {
        String webPageHtml = wrapArticleHtml(articleHtml);
        articleView.get().updateContent(webPageHtml);
        articleView.get().showContent();
    }

    private String wrapArticleHtml(String articleHtml) {
        return "<!DOCTYPE html> \n" +
                "<html>\n" +
                "<head>\n" +
                "  <meta charset=\"utf-8\">\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "  <link rel=\"stylesheet\" href=\"style.css\">\n" +
                "</head>\n" +
                "<body>" +
                articleHtml +
                "</body>\n" +
                "</html>";
    }

    @Override
    public void onLoadError() {
        articleView.get().showError();
    }

    @Override
    public void onDestroy(boolean isConfigChanging) {
        articleView = null;
        if (!isConfigChanging) {
            modelInteractor.onDestroy();
        }
    }

    @Override
    public void onConfigurationChanged(ArticleMvp.View view, String id) {
        articleView = new WeakReference<>(view);
        articleView.get().showLoading();
        modelInteractor.fetchCached(id);
    }
}