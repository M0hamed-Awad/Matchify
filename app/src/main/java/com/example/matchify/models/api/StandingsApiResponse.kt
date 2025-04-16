package com.example.matchify.models.api

import com.example.matchify.models.StandingModel
import com.google.gson.annotations.SerializedName

data class StandingsApiResponse(
    @SerializedName("response")
    val standings: List<StandingModel>
)
