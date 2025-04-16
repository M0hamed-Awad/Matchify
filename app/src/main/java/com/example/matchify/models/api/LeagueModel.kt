package com.example.matchify.models.api

import com.example.matchify.models.LeagueData
import com.google.gson.annotations.SerializedName

data class LeagueModel (
    @SerializedName("league")
    val leagueData: LeagueData
)