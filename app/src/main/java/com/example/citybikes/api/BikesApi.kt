package com.example.citybikes.api

import com.example.citybikes.model.BikesResponse
import retrofit2.Response
import retrofit2.http.GET

interface BikesApi {

    @GET("networks")
    suspend fun getAllCityBikes(): Response<BikesResponse>
}