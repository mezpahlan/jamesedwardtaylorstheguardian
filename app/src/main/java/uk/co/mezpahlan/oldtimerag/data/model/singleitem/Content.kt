package uk.co.mezpahlan.oldtimerag.data.model.singleitem

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Content(val webUrl: String? = null,
              val webPublicationDate: String? = null,
              val webTitle: String? = null,
              val sectionName: String? = null,
              val apiUrl: String? = null,
              val id: String? = null,
              val isIsHosted: Boolean = false,
              val fields: Fields? = null,
              val sectionId: String? = null,
              val type: String? = null)