package com.example.data_api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieApiBuilder {

    companion object {
        private val defaultClient: OkHttpClient = OkHttpClient()

        fun <S> createServiceApi(
            serviceClass: Class<S>,
            baseUrl: String,
            gson: Gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create(),
            vararg interceptors: Interceptor
        ): S {

            // create client
            val clientBuilder = defaultClient.newBuilder()

            // add interceptors
            interceptors.forEach { clientBuilder.addInterceptor(it) }

            //build retrofit and set client, Coroutine factory
            val retrofit = Retrofit
                .Builder()
                .client(clientBuilder.build())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit.create(serviceClass)
        }
    }

}