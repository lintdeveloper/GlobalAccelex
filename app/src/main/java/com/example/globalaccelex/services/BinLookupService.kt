package com.example.globalaccelex.services

import BinLookUpResponse
import retrofit2.http.GET
import retrofit2.http.Path

//https://lookup.binlist.net/45717360
interface BinLookupService {
    @GET("/{binLookUpNumber}")
    suspend fun getBinLookUp(
        @Path("binLookUpNumber") binLookUpNumber: String
    ): BinLookUpResponse
}