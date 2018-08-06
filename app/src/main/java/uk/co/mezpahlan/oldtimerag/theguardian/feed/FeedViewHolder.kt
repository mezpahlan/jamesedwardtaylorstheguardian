package uk.co.mezpahlan.oldtimerag.theguardian.feed

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.list_item_rss_feed.view.*

/**
 * RecyclerView.ViewHolder for the Feed view.
 */
class FeedViewHolder(private val feedRecyclerViewAdapter: FeedRecyclerViewAdapter, itemView: View, private val clickListener: FeedFragment.ResultClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    val thumbnailView: ImageView = itemView.thumbnailView
    val headlineView: TextView = itemView.headlineView
    val trailTextView: TextView = itemView.trailTextView
    val publishedOnView: TextView = itemView.publishedOnView
    val sectionView: TextView = itemView.sectionView

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val layoutPosition = layoutPosition
        val result = feedRecyclerViewAdapter.getResultWithPosition(layoutPosition)
        clickListener.onResultClick(result)
    }
}
