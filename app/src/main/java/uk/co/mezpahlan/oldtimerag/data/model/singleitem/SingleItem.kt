package uk.co.mezpahlan.oldtimerag.data.model.singleitem

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SingleItem(val response: Response? = null)