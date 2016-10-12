package uk.co.mezpahlan.oldtimerag.theguardian.article;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * WebViewClient for TheGuardian.Article. Customises the behaviour, look, and feel of the articles.
 */

class ArticleWebViewClient extends WebViewClient {
    private final ArticleFragment articleFragment;

    public ArticleWebViewClient(ArticleFragment articleFragment) {
        this.articleFragment = articleFragment;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        if (intent.resolveActivity(articleFragment.getActivity().getPackageManager()) != null) {
            articleFragment.startActivity(intent);
        }
        return true;
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(request.getUrl().toString()));
        if (intent.resolveActivity(articleFragment.getActivity().getPackageManager()) != null) {
            articleFragment.startActivity(intent);
        }
        return true;
    }
}
