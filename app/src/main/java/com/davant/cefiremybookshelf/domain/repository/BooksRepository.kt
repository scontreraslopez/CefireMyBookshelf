package com.davant.cefiremybookshelf.domain.repository

import com.davant.cefiremybookshelf.domain.model.Book
import kotlinx.coroutines.flow.Flow

interface BooksRepository {
    fun getBooks(userId: String): Flow<List<Book>>
    suspend fun addBook(userId: String, book: Book)
    suspend fun updateBook(userId: String, book: Book)
    suspend fun deleteBook(userId: String, bookId: String)
}
