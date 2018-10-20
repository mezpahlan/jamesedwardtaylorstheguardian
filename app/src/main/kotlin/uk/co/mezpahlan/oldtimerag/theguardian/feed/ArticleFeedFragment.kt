package uk.co.mezpahlan.oldtimerag.theguardian.feed

import android.os.Bundle

class ArticleFeedFragment : FeedFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.feedType = FeedType.ARTICLE
    }
}