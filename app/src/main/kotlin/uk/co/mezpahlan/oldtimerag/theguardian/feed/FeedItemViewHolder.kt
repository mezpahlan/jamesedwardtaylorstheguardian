package uk.co.mezpahlan.oldtimerag.theguardian.feed

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.list_item_rss_feed.view.*
import uk.co.mezpahlan.oldtimerag.extensions.loadUrl

/**
 * RecyclerView.ViewHolder for the Feed view.
 */
class FeedItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: FeedItem, listener: (FeedItem) -> Unit) = with(itemView) {
        thumbnailView.loadUrl(item.thumbnailUrl)

        headlineView.text = item.headline // TODO: stripHTML

        when (item.trailText.isNullOrBlank()) {
            true -> trailTextView.visibility = View.GONE
            false -> trailTextView.text = item.trailText
        }

        publishedOnView.text = item.publishedOn // TODO: convertDateFormat
        sectionView.text = item.section

        setOnClickListener { listener(item) }
    }
}
