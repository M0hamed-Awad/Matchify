package com.example.matchify.models.api

import com.google.gson.annotations.SerializedName

data class LeaguesApiResponse(
    @SerializedName("response")
    val leagues: List<LeagueModel>
)
