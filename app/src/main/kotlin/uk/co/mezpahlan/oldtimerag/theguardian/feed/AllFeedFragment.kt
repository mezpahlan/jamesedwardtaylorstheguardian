package uk.co.mezpahlan.oldtimerag.theguardian.feed

import android.os.Bundle

class AllFeedFragment : FeedFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.feedType = FeedType.ALL
    }
}