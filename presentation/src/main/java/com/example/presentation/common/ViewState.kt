package com.example.presentation.common

sealed class ViewState(val data: Any? = null, private val error: Exception? = null) {
    object Loading: ViewState()
    class Success<T>(private val value: T): ViewState(data = value)
    class Error(val error: Exception): ViewState(error = error)
}