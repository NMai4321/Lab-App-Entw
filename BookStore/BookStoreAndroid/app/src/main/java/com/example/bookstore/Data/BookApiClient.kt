package com.example.bookstore.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface BookApiClient {
    @GET("list")
    suspend fun getAllBooks(): List<Book>

    @GET("details/{bookId}")
    suspend fun getBookDetails(@Path("bookId") bookId: Long): Book

    @GET("reviews/{bookId}")
    suspend fun getReviewsByBook(@Path("bookId") bookId: Long): List<BookReview>

    companion object {
        private const val BASE_URL = "http://10.0.2.2:8080/books/"

        fun create(): BookApiClient {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(BookApiClient::class.java)
        }
    }
}
