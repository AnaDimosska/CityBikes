package com.example.citybikes.model

data class BikesResponse(
    val networks: List<Network>
)

data class Network(
    val id: String,
    val name: String,
    val location: Location,
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