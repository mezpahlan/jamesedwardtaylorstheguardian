package uk.co.mezpahlan.oldtimerag.theguardian.feed

import androidx.recyclerview.widget.ListAdapter
import android.view.ViewGroup
import uk.co.mezpahlan.oldtimerag.R
import uk.co.mezpahlan.oldtimerag.extensions.inflate

class FeedItemAdapter(private val listener: (FeedItem) -> Unit) : ListAdapter<FeedItem, FeedItemViewHolder>(FeedItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedItemViewHolder = FeedItemViewHolder(parent.inflate(R.layout.list_item_rss_feed))

    override fun onBindViewHolder(holder: FeedItemViewHolder, position: Int) = holder.bind(getItem(position), listener)
}