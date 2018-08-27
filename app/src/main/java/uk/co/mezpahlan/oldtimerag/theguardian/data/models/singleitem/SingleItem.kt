package uk.co.mezpahlan.oldtimerag.theguardian.data.models.singleitem

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SingleItem(val response: Response)