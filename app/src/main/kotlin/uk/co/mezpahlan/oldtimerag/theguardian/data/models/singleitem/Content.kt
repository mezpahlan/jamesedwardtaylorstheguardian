package uk.co.mezpahlan.oldtimerag.theguardian.data.models.singleitem

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Content(val webUrl: String = "",
              val webPublicationDate: String = "",
              val webTitle: String = "",
              val sectionName: String = "",
              val apiUrl: String = "",
              val id: String = "",
              val isIsHosted: Boolean = false,
              val fields: Fields = Fields(),
              val sectionId: String = "",
              val type: String = "")