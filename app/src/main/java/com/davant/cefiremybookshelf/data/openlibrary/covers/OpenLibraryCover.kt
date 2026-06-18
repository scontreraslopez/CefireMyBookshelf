package com.davant.cefiremybookshelf.data.openlibrary.covers

import com.davant.cefiremybookshelf.domain.model.Cover
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpenLibraryCover(
    val archived: Boolean,
    val author: String,
    @SerialName("category_id")
    val categoryId: Int,
    val created: String,
    val deleted: Boolean,
    val failed: Boolean,
    val filename: String,
    val filename_l: String,
    val filename_m: String,
    val filename_old: String?,
    val filename_s: String,
    val height: Int,
    val id: Int,
    val ip: String,
    val isbn: String?,
    val isbn13: String?,
    val last_modified: String,
    val olid: String,
    val source: String?,
    val source_url: String,
    val uploaded: Boolean,
    val width: Int
)

fun OpenLibraryCover.toCover() =
    Cover(
        id = id.toString(),
        olid = olid,
        width = width,
        height = height
    )