package com.davant.cefiremybookshelf.screens.addedit

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davant.cefiremybookshelf.data.openlibrary.covers.OpenLibraryRepository
import com.davant.cefiremybookshelf.data.openlibrary.search.bookinfo.BookInfo
import com.davant.cefiremybookshelf.domain.model.Book
import com.davant.cefiremybookshelf.domain.model.Cover
import com.davant.cefiremybookshelf.domain.repository.BooksRepository
import com.davant.cefiremybookshelf.domain.repository.CoversRepository
import com.davant.cefiremybookshelf.domain.repository.SearchRepository
import kotlinx.coroutines.launch

class AddEditViewModel(
    private val inBook: Book,
    private val repository: BooksRepository,
    private val coversRepository: CoversRepository,
    private val searchRepository: SearchRepository,
    private val userId: String,
    val navigateBack: () -> Unit
) : ViewModel() {
    private val _book = MutableLiveData(inBook)
    val book: LiveData<Book> = _book

    private val _newBook = MutableLiveData(true)
    val newBook: LiveData<Boolean> = _newBook

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean> = _isError

    var coverUIState: CoverUIState by mutableStateOf(CoverUIState.Idle)
    var bookInfoUIState: BookInfoUIState by mutableStateOf(BookInfoUIState.Idle)


    init {
        _newBook.value = inBook.title.isEmpty()
    }

    sealed interface CoverUIState {
        data class Success(val cover: Cover) : CoverUIState
        object Error : CoverUIState
        object Loading : CoverUIState
        object Idle : CoverUIState
    }

    sealed interface BookInfoUIState {
        data class Success(val bookInfo: Book) : BookInfoUIState
        object Error : BookInfoUIState
        object Loading : BookInfoUIState
        object Idle : BookInfoUIState
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

    fun getCoverId() {
        coverUIState = CoverUIState.Loading
        viewModelScope.launch {
            try {
                coverUIState = CoverUIState.Success(
                    coversRepository.getCoverByIsbn(_book.value!!.isbn)
                )
                _book.value = _book.value!!.copy(
                    cover = OpenLibraryRepository.PATH +
                            (coverUIState as CoverUIState.Success).cover.id +
                            OpenLibraryRepository.EXTENSION
                )
            } catch (e: Exception) {
                coverUIState = CoverUIState.Error
                Log.e("AddEditViewModel",
                    "Error getting cover: ${e.message}")
            }
        }
    }

    fun getBookInfo() {
        bookInfoUIState = BookInfoUIState.Loading
        viewModelScope.launch {
            try {
                bookInfoUIState = BookInfoUIState.Success(
                    searchRepository
                        .getBookInfoByIsbn(_book.value!!.isbn)
                )
                _book.value = (bookInfoUIState as BookInfoUIState.Success)
                    .bookInfo.copy(isbn = _book.value!!.isbn,
                        id = _book.value!!.id,
                        fav = _book.value!!.fav,
                        read = _book.value!!.read)
            }
            catch (e: Exception) {
                bookInfoUIState = BookInfoUIState.Error
                Log.e("AddEditViewModel",
                    "Error getting book info: ${e.message}")
            }
        }
    }
}