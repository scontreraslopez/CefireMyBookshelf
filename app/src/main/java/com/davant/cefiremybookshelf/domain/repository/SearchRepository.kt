package com.davant.cefiremybookshelf.domain.repository

import com.davant.cefiremybookshelf.domain.model.Book

interface SearchRepository {
    suspend fun getBookInfoByIsbn(isbn:String): Book
}