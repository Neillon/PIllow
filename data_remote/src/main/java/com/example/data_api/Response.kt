package com.example.data_api

data class Response<T>(
    val page: Int,
    val results: T,
    val totalPages: Int,
    val totalResults: Int
)