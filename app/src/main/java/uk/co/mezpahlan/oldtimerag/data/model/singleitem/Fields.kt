package uk.co.mezpahlan.oldtimerag.data.model.singleitem

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Fields(val body: String? = null)