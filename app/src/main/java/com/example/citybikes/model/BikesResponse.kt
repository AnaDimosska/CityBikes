package com.example.citybikes.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nullable

data class BikesResponse(
    val networks: List<Network>
)


@Entity(tableName = "company")
data class Network(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "location")
    val location: Location,
    @ColumnInfo(name = "company")
    val company: Any,
    val href: String?,
    val gbfs_href: String?
)

data class Location(
    val city: String,
    val country: String,
    val latitude: Double,
    val longitude: Double
)