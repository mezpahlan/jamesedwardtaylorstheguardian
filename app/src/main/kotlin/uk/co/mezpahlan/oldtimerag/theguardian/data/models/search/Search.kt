package uk.co.mezpahlan.oldtimerag.theguardian.data.models.search

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Search(val response: Response)