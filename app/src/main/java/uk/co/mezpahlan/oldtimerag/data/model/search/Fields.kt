package uk.co.mezpahlan.oldtimerag.data.model.search

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Fields(val thumbnail: String? = null,
             val trailText: String? = null,
             val headline: String? = null)