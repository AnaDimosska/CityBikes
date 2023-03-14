package com.example.citybikes.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.citybikes.model.Network
import kotlinx.coroutines.flow.Flow

@Dao
interface CompanyBikesDao {

    @Insert
    suspend fun insertBikeCompanies(movies:List<Network>)

    @Query("SELECT * FROM company")
    fun getBikeCompanies(): Flow<List<Network>>

}