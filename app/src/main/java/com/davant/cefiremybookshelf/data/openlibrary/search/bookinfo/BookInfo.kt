package com.davant.cefiremybookshelf.data.openlibrary.search.bookinfo

import com.davant.cefiremybookshelf.data.openlibrary.covers.OpenLibraryRepository
import com.davant.cefiremybookshelf.domain.model.Book
import com.davant.cefiremybookshelf.screens.addedit.AddEditViewModel.CoverUIState
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookInfo(
    @SerialName("author_key")
    val authorKey: List<String>,
    @SerialName("author_name")
    val authorName: List<String>,
    @SerialName("cover_edition_key")
    val coverEditionKey: String,
    @SerialName("cover_i")
    val coverId: Int,
    @SerialName("ebook_access")
    val ebookAccess: String,
    @SerialName("edition_count")
    val editionCount: Int,
    @SerialName("first_publish_year")
    val firstPublishYear: Int,
    @SerialName("has_fulltext")
    val hasFulltext: Boolean,
    val key: String,
    @SerialName("public_scan_b")
    val publicScanB: Boolean,
    val title: String
)

fun BookInfo.toBook() = Book(
    title = title,
    author = authorName.joinToString(", "),
    year = firstPublishYear,
    cover = OpenLibraryRepository.PATH +
            coverId +
            OpenLibraryRepository.EXTENSION
)