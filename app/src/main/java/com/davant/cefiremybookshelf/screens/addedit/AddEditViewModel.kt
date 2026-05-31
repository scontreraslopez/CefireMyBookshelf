package com.davant.cefiremybookshelf.screens.addedit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davant.cefiremybookshelf.domain.model.Book
import com.davant.cefiremybookshelf.domain.repository.BooksRepository
import kotlinx.coroutines.launch

class AddEditViewModel(
    private val inBook: Book,
    private val repository: BooksRepository,
    private val userId: String,
    val navigateBack: () -> Unit
) : ViewModel() {
    private val _book = MutableLiveData(inBook)
    val book: LiveData<Book> = _book

    private val _newBook = MutableLiveData(true)
    val newBook: LiveData<Boolean> = _newBook

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean> = _isError

    init {
        _newBook.value = inBook.title.isEmpty()
    }

    fun updateBook(book: Book) {
        _isError.value = false
        _book.value = book
    }

    fun addBookFirebase() {
        if (checkBook())
            viewModelScope.launch {
                repository.addBook(userId, _book.value!!)
                navigateBack()
            }
    }

    fun updateBookFirebase() {
        if (checkBook())
            viewModelScope.launch {
                repository.updateBook(userId, _book.value!!)
                navigateBack()
            }
    }

    fun deleteBookFirebase() {
        viewModelScope.launch {
            repository.deleteBook(userId, _book.value!!.id)
            navigateBack()
        }
    }

    private fun checkBook():Boolean {
        if(_book.value == null)
            _isError.value = true
        else  {
            if(_book.value!!.title.isEmpty())
                _isError.value = true
            if(_book.value!!.isbn.isEmpty())
                _isError.value = true
            if(_book.value!!.author.isEmpty())
                _isError.value = true
            if(_book.value!!.cover.isEmpty())
                _isError.value = true
            if(_book.value!!.year == null)
                _isError.value = true
        }
        return _isError.value == false
    }
}