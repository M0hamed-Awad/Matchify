package com.example.matchify.network

import com.example.matchify.models.FixtureModel
import com.example.matchify.models.FixtureLeague
import com.example.matchify.models.FixtureScore
import com.example.matchify.models.Team
import com.example.matchify.models.api.FixturesApiResponse
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class CustomFixtureDeserializer : JsonDeserializer<FixturesApiResponse> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): FixturesApiResponse {
        val jsonObject = json.asJsonObject
        val responseArray = jsonObject.getAsJsonArray("response")
        val fixtures:MutableList<FixtureModel> = mutableListOf()

        for (item in responseArray) {
            val obj = item.asJsonObject

            val fixtureObj = obj.getAsJsonObject("fixture")
            val venueObj = fixtureObj.getAsJsonObject("venue")
            val statusObj = fixtureObj.getAsJsonObject("status")

            val homeTeamObj = obj.getAsJsonObject("teams").getAsJsonObject("home")
            val awayTeamObj = obj.getAsJsonObject("teams").getAsJsonObject("away")

            val fixtureModel = FixtureModel(
                id = fixtureObj.get("id")?.asInt ?: -1,
                referee = if (fixtureObj.get("referee")?.isJsonNull == true) "Unknown"
                else fixtureObj.get("referee")?.asString ?: "Unknown",
                timezone = fixtureObj.get("timezone")?.asString ?: "Unknown",
                date = fixtureObj.get("date")?.asString ?: "Unknown",
                stadium = venueObj.get("name")?.asString ?: "Unknown",
                city = venueObj.get("city")?.asString ?: "Unknown",
                statusShort = statusObj.get("short")?.asString ?: "Unknown",
                elapsed = if (statusObj.get("elapsed")?.isJsonNull == true) 0
                else statusObj.get("elapsed")?.asInt ?: 0,
                extra = if (statusObj.get("extra")?.isJsonNull == true) -1
                else statusObj.get("extra")?.asInt ?: -1,

                fixtureLeague = context.deserialize(obj.get("league"), FixtureLeague::class.java),

                homeTeam = Team(
                    id = homeTeamObj.get("id")?.asInt ?: -1,
                    name = homeTeamObj.get("name")?.asString ?: "",
                    logo = homeTeamObj.get("logo")?.asString ?: "",
                    isWinner = if (homeTeamObj.get("winner")?.isJsonNull == true) false
                    else homeTeamObj.get("winner")?.asBoolean ?: false
                ),

                awayTeam = Team(
                    id = awayTeamObj.get("id")?.asInt ?: -1,
                    name = awayTeamObj.get("name")?.asString ?: "",
                    logo = awayTeamObj.get("logo")?.asString ?: "",
                    isWinner = if (awayTeamObj.get("winner")?.isJsonNull == true) false
                    else awayTeamObj.get("winner")?.asBoolean ?: false
                ),

                fixtureScore = FixtureScore(
                    homeTeamScore = if (obj.getAsJsonObject("goals").get("home")?.isJsonNull == true) 0
                    else obj.getAsJsonObject("goals").get("home")?.asInt ?: 0,
                    awayTeamScore = if (obj.getAsJsonObject("goals").get("away")?.isJsonNull == true) 0
                    else obj.getAsJsonObject("goals").get("away")?.asInt ?: 0
                )
            )

            fixtures.add(fixtureModel)
        }

        return FixturesApiResponse(fixtures)
    }
}