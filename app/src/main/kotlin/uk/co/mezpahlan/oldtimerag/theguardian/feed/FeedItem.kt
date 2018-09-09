package uk.co.mezpahlan.oldtimerag.theguardian.feed

data class FeedItem(
        val id: String = "",
        val thumbnailUrl: String = "",
        val headline: String = "",
        val trailText: String = "",
        val publishedOn: String = "",
        val section: String = "")
