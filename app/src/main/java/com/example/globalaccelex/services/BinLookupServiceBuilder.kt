package com.example.globalaccelex.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BinLookupServiceBuilder {
    const val BASE_URL = "https://lookup.binlist.net"

    val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val binLookupService: BinLookupService by lazy {
        retrofitBuilder
            .build()
            .create(BinLookupService::class.java)
    }

}