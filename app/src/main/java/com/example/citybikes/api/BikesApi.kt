package com.example.citybikes.api

import com.example.citybikes.model.BikesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BikesApi {

    @GET("/v2/networks")
    suspend fun getAllCityBikes(): Response<BikesResponse>
}