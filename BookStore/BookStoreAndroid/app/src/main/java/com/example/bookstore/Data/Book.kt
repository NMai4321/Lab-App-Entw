package com.example.bookstore.data

data class Book(
    val id: Long,
    val title: String,
    val author: String,
    val description: String,
    val reviews: List<String> = emptyList()
)

data class BookReview(
    val id: Long,
    val comment: String
)
