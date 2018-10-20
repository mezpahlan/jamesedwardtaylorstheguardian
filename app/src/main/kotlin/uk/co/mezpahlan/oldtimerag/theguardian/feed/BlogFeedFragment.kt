package uk.co.mezpahlan.oldtimerag.theguardian.feed

import android.os.Bundle

class BlogFeedFragment : FeedFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.feedType = FeedType.LIVE_BLOG
    }
}