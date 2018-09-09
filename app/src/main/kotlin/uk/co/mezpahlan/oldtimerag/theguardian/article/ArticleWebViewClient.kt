package uk.co.mezpahlan.oldtimerag.theguardian.article

import android.annotation.TargetApi
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

/**
 * WebViewClient for TheGuardian.Article. Customises the behaviour, look, and feel of the articles.
 */
internal class ArticleWebViewClient(private val articleFragment: ArticleFragment) : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.data = Uri.parse(url)
        if (intent.resolveActivity(articleFragment.requireActivity().packageManager) != null) {
            articleFragment.startActivity(intent)
        }
        return true
    }

    @TargetApi(Build.VERSION_CODES.N)
    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.data = Uri.parse(request.url.toString())
        if (intent.resolveActivity(articleFragment.requireActivity().packageManager) != null) {
            articleFragment.startActivity(intent)
        }
        return true
    }
}
