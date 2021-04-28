package com.vsca.vsnapvoicecollege.Rest

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object APIClient {

    var BASE_URL = "http://13.235.134.1/"

    public var retrofit: Retrofit? = null

    fun getOK_Client(): OkHttpClient? {
        return OkHttpClient.Builder()
            .connectTimeout(300, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .build()
    }

    public fun getApiClient(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .client(getOK_Client())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()

        }
        return retrofit
    }

    @JvmStatic
    public fun getClient(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .client(getOK_Client())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()

        }
        return retrofit
    }

    fun changeBaseUrl(url: String) {
        BASE_URL = url
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .client(getOK_Client())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
        }
    }


    ///github checking
    // added

}