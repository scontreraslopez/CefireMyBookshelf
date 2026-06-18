package com.davant.cefiremybookshelf.data.openlibrary.search.bookinfo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookSearch(
    val docs: List<BookInfo>,
    @SerialName("documentation_url")
    val documentationUrl: String,
    val numFound: Int,
    val numFoundExact: Boolean,
    @SerialName("num_found")
    val numFound2: Int,
    val q: String,
    val start: Int
)