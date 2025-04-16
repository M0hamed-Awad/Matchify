package com.example.matchify.models

data class FixtureLeague(
    val id: Int,
    val name: String?,
    val country: String?,
    val logo: String?,
    val flag: String?,
    val season: Int?,
    val round: String?,
)