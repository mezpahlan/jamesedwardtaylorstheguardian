package uk.co.mezpahlan.oldtimerag.theguardian.data.models.singleitem

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Response(val userTier: String? = null,
               val total: Int = 0,
               val content: Content = Content(),
               val status: String? = null)