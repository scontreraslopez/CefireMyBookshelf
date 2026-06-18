package com.davant.cefiremybookshelf.data.openlibrary.search

import com.davant.cefiremybookshelf.data.openlibrary.search.bookinfo.toBook
import com.davant.cefiremybookshelf.domain.model.Book
import com.davant.cefiremybookshelf.domain.repository.SearchRepository

class OLSearchRepository(val apiService: OLSearchService): SearchRepository {

    override suspend fun getBookInfoByIsbn(isbn: String): Book {
        return apiService.getBookInfoByIsbn(isbn).docs[0].toBook()
    }
}