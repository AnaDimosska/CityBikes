package com.example.citybikes.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.citybikes.model.Network
import com.example.citybikes.util.Converters


@Database(
    entities = [Network::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class CompanyBikesDatabase: RoomDatabase() {

    abstract fun getCompanyBikesDao(): CompanyBikesDao

}