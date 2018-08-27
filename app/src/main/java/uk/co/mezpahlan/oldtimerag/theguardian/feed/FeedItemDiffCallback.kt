package uk.co.mezpahlan.oldtimerag.theguardian.feed

import android.support.v7.util.DiffUtil

class FeedItemDiffCallback : DiffUtil.ItemCallback<FeedItem>() {
    override fun areItemsTheSame(oldItem: FeedItem, newItem: FeedItem) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: FeedItem, newItem: FeedItem) = oldItem == newItem
}
