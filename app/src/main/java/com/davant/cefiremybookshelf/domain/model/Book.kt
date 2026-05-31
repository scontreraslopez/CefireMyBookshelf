package com.davant.cefiremybookshelf.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val id: String = "",
    val title: String = "",
    val author: String = "",
    val year: Int? = 1900,
    val isbn: String = "",
    val cover: String = "",
    val fav: Boolean = false,
    val read: Boolean = false
)
