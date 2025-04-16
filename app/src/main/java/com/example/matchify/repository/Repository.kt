package com.example.matchify.repository

import com.example.matchify.models.FixtureModel
import com.example.matchify.models.LeagueData
import com.example.matchify.models.StandingModel
import com.example.matchify.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository {

    suspend fun getFixtures(
        leagueId: Int = 39,
        season: Int = 2023,
        apiKey: String
    ): List<FixtureModel>{
        return  withContext(Dispatchers.IO) {
            RetrofitClient.apiService.getFixtures(
                leagueId = leagueId,
                season = season,
                apiKey = apiKey
            ).fixtures
        }
    }


    suspend fun getLeagues(
        apiKey: String
    ): List<LeagueData> = withContext(Dispatchers.IO) {
        RetrofitClient.apiService.getLeagues(
            apiKey = apiKey
        ).leagues.map { it.leagueData }
    }

    suspend fun getStandings(
        leagueId: Int = 39,
        season: Int = 2023,
        apiKey: String
    ): List<StandingModel> = withContext(Dispatchers.IO) {
        RetrofitClient.apiService.getStandings(
            leagueId = leagueId,
            season = season,
            apiKey = apiKey
        ).standings
    }

}