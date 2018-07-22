package uk.co.mezpahlan.oldtimerag.data.model.search

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Search(val response: Response? = null)