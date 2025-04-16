package com.example.matchify.network

import com.example.matchify.models.StandingModel
import com.example.matchify.models.api.StandingsApiResponse
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class CustomStandingDeserializer : JsonDeserializer<StandingsApiResponse> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): StandingsApiResponse {
        val jsonObject = json?.asJsonObject
        val responseArray = jsonObject?.getAsJsonArray("response")

        val standings: MutableList<StandingModel> = mutableListOf()

        if (responseArray != null && responseArray.size() > 0) {
            val leagueObject = responseArray[0].asJsonObject.getAsJsonObject("league")
            val leagueId = leagueObject.get("id").asInt
            val standingsOuterArray = leagueObject.getAsJsonArray("standings")

            if (standingsOuterArray != null && standingsOuterArray.size() > 0) {
                val standingsArray = standingsOuterArray[0].asJsonArray

                for (item in standingsArray) {
                    val obj = item.asJsonObject

                    val teamObj = obj.getAsJsonObject("team")
                    val allObj = obj.getAsJsonObject("all")
                    val goalsObj = allObj.getAsJsonObject("goals")

                    val standingModel = StandingModel(
                        leagueId = leagueId,
                        teamRank = obj.get("rank")?.asInt ?: -1,
                        teamName = teamObj.get("name")?.asString ?: "Unknown",
                        teamLogo = teamObj.get("logo")?.asString ?: "",
                        matchesPlayed = allObj.get("played")?.asInt ?: 0,
                        matchesWon = allObj.get("win")?.asInt ?: 0,
                        matchesLost = allObj.get("lose")?.asInt ?: 0,
                        matchesDrawn = allObj.get("draw")?.asInt ?: 0,
                        goalsFor = goalsObj.get("for")?.asInt ?: 0,
                        goalsAgainst = goalsObj.get("against")?.asInt ?: 0,
                        goalsDifference = obj.get("goalsDiff")?.asInt ?: 0,
                        points = obj.get("points")?.asInt ?: 0,
                    )

                    standings.add(standingModel)
                }
            }
        }

        return StandingsApiResponse(standings)
    }
}
