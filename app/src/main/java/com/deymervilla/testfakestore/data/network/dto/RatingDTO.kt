package com.deymervilla.testfakestore.data.network.dto

import com.google.gson.annotations.SerializedName

data class RatingDTO(
    @SerializedName("rate")
    val rate: Double?,
    @SerializedName("count")
    val count: Int?
)