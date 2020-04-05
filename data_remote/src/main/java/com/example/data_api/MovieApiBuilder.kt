package com.example.data_api

import com.example.data_api.interceptors.AuthInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieApiBuilder {

    companion object {
        private val defaultClient: OkHttpClient = OkHttpClient()

        fun <S> createService(
            serviceClass: Class<S>
        ): S {
            val clientBuilder = defaultClient.newBuilder()
            clientBuilder.addInterceptor(AuthInterceptor())
            val retrofit = Retrofit
                .Builder()
                .client(clientBuilder.build())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(serviceClass)
        }
    }

}