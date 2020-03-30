package com.example.data_api.interceptors

import com.example.data_api.API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var newUrl = chain.request()
            .url()
            .newBuilder()
            .addQueryParameter("api_key", API_KEY).build()

        var request = chain.request().newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(request)
    }
}