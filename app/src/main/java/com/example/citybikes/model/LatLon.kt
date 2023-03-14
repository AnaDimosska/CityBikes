package com.example.citybikes.model

import java.io.Serializable

data class LatLon(
    var lat: Double = 0.0,
    var long: Double = 0.0
) : Serializable