package com.example.citybikes.repo

import com.example.citybikes.api.BikesApi
import javax.inject.Inject

class Repository @Inject constructor(
    val bikesApi: BikesApi
) {

    suspend fun getAllCityBikes() =
        bikesApi.getAllCityBikes()
}