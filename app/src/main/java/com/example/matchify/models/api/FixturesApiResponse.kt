package com.example.matchify.models.api

import com.example.matchify.models.FixtureModel
import com.google.gson.annotations.SerializedName

data class FixturesApiResponse(
    @SerializedName("response")
    val fixtures: List<FixtureModel>
)