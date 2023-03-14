package com.example.citybikes.repo

import com.example.citybikes.api.BikesApi
import com.example.citybikes.db.CompanyBikesDatabase
import com.example.citybikes.model.Network
import javax.inject.Inject

class Repository @Inject constructor(
    private val bikesApi: BikesApi,
    private val companiesDb: CompanyBikesDatabase
) {

    suspend fun getAllCityBikes() =
        bikesApi.getAllCityBikes()

    suspend fun insertBikeCompanies(movie:List<Network>) =
        companiesDb.getCompanyBikesDao().insertBikeCompanies(movie)
}