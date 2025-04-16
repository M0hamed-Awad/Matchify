package com.example.matchify.network

import com.example.matchify.models.api.FixturesApiResponse
import com.example.matchify.models.api.LeaguesApiResponse
import com.example.matchify.models.api.StandingsApiResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    @GET("fixtures")
    suspend fun getFixtures(
        @Query("league") leagueId: Int = 39,
        @Query("season") season: Int = 2023,
        @Query("round") round: String = "Regular Season - 1",
        @Header("x-apisports-key") apiKey: String
    ): FixturesApiResponse

    @GET("leagues")
    suspend fun getLeagues(
        @Header("x-apisports-key") apiKey: String
    ): LeaguesApiResponse

    @GET("standings")
    suspend fun getStandings(
        @Query("league") leagueId: Int = 39,
        @Query("season") season: Int = 2023,
        @Header("x-apisports-key") apiKey: String
    ): StandingsApiResponse
}