package com.example.matchify.models

data class StandingModel (
    val leagueId: Int,
    val teamRank: Int,
    val teamName: String,
    val teamLogo: String,
    val matchesPlayed: Int,
    val matchesWon: Int,
    val matchesLost: Int,
    val matchesDrawn: Int,
    val goalsFor: Int,
    val goalsAgainst: Int,
    val goalsDifference: Int,
    val points: Int,
)