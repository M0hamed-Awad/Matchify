package com.example.matchify.models

data class FixtureModel(
    val id: Int,
    val referee: String?,
    val timezone: String?,
    val date: String?,
    val stadium: String?,
    val city: String?,
    val statusShort: String?,
    val elapsed: Int?,
    val extra: Int?,
    val fixtureLeague: FixtureLeague?,
    val homeTeam: Team?,
    val awayTeam: Team?,
    val fixtureScore: FixtureScore?,
)