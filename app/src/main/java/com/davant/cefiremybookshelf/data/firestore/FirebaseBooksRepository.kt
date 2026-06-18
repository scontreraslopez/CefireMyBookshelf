package com.davant.cefiremybookshelf.data.firestore

import android.util.Log
import com.davant.cefiremybookshelf.domain.model.Book
import com.davant.cefiremybookshelf.domain.repository.BooksRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class FirebaseBooksRepository(
    private val firestore: FirebaseFirestore
) : BooksRepository {

    override fun getBooks(userId: String): Flow<List<Book>> {
        return firestore.collection("users")
            .document(userId)
            .collection("books")
            .snapshots()
            .map { snapshot ->
                snapshot.toObjects(Book::class.java)
            }
    }

    override suspend fun addBook(userId: String, book: Book) {
        val docRef = firestore.collection("users")
            .document(userId)
            .collection("books")
            .document()
        
        val bookWithId = book.copy(id = docRef.id)
        docRef.set(bookWithId).await()
    }

    override suspend fun updateBook(userId: String, book: Book) {
        firestore.collection("users")
            .document(userId)
            .collection("books")
            .document(book.id)
            .set(book)
            .await()
    }

    override suspend fun deleteBook(userId: String, bookId: String) {
        firestore.collection("users")
            .document(userId)
            .collection("books")
            .document(bookId)
            .delete()
            .await()
    }
}
