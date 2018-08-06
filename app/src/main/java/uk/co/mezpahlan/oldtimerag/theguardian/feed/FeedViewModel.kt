package uk.co.mezpahlan.oldtimerag.theguardian.feed

import android.arch.lifecycle.ViewModel

class FeedViewModel : ViewModel() {
    var feedType : FeedType = FeedType.ALL
    var isTwoPane : Boolean = false
}