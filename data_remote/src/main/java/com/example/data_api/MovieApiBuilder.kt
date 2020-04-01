package com.example.data_api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieApiBuilder {

    companion object {
        private val defaultClient: OkHttpClient = OkHttpClient()

        fun <S> createServiceApi(
            serviceClass: Class<S>,
            vararg interceptors: Interceptor
        ): S {
            val clientBuilder = defaultClient.newBuilder()
            interceptors.forEach { clientBuilder.addInterceptor(it) }

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