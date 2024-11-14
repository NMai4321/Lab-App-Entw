package com.example.bookstore

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookstore.data.Book
import com.example.bookstore.data.BookApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookViewModel : ViewModel() {
    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> get() = _books

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    init {
        fetchBooks()
    }

    private fun fetchBooks() {
        viewModelScope.launch {
            try {
                val bookApiClient = BookApiClient.create()
                val booksFromBackend = bookApiClient.getAllBooks()
                _books.value = booksFromBackend
                _error.value = null
            } catch (e: Exception) {
                _books.value = emptyList()
                _error.value = "Fehler beim Laden der Bücher: ${e.message}"
                Log.e("BookViewModel", "Fehler beim Laden der Bücher", e)
            }
        }
    }
}
