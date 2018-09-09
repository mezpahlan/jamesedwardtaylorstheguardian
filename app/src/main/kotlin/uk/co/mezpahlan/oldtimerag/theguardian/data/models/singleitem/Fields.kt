package uk.co.mezpahlan.oldtimerag.theguardian.data.models.singleitem

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Fields(val body: String = "")