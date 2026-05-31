package com.davant.cefiremybookshelf.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davant.cefiremybookshelf.domain.model.Book
import com.davant.cefiremybookshelf.domain.repository.BooksRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: BooksRepository,
    val userName: String,
    private val userId: String,
    val goToAddEditScreen: (Book) -> Unit,
    val goBack: () -> Unit
) : ViewModel() {

    private val _contentIndex = MutableStateFlow(0)
    val contentIndex: StateFlow<Int> = _contentIndex

    val bookList: StateFlow<List<Book>> = repository.getBooks(userId)
        .combine(_contentIndex) { books, index ->
            filterBookList(books, index)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onContentIndexChange(index: Int) {
        _contentIndex.value = index
    }
}
