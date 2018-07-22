package uk.co.mezpahlan.oldtimerag.data.model.search

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Result(val webUrl: String? = null,
             val webPublicationDate: String? = null,
             val webTitle: String? = null,
             val sectionName: String? = null,
             val apiUrl: String? = null,
             val id: String? = null,
             val isIsHosted: Boolean = false,
             val fields: Fields? = null,
             val sectionId: String? = null,
             val type: String? = null)