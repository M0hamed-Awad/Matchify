package com.example.matchify.utils

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.example.matchify.R
import com.example.matchify.models.LeagueData
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class HelperFunctions {
    companion object {
        fun formatFixtureDate(dateString: String): String {
            return try {
                val inputFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
                val parsedDate = OffsetDateTime.parse(dateString, inputFormatter)

                val outputFormatter = DateTimeFormatter.ofPattern("EEEE M/dd")
                parsedDate.format(outputFormatter)

            } catch (e: Exception) {
                "Invalid Date"
            }
        }

        fun formatFixtureTime(dateString: String): String {
            return try {
                val inputFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
                val parsedDate = OffsetDateTime.parse(dateString, inputFormatter)

                val outputFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.getDefault())
                parsedDate.format(outputFormatter)
            } catch (e: Exception) {
                "Invalid Time"
            }
        }

        private val TOP_LEAGUE_IDS = listOf(39, 140, 78, 61, 135)
        // (Premier League - Laliga - Bundesliga - Ligue 1 - Serie A)

        fun getTop5Leagues(leagues: List<LeagueData>) : List<LeagueData>{
            val top5Leagues = mutableListOf<LeagueData>()
            for ( league in leagues){
                if (league.id in  TOP_LEAGUE_IDS) top5Leagues.add(league)
            }

            top5Leagues.sortBy { league -> league.id }
            return top5Leagues
        }

        fun getLeagueColor(context: Context, leagueId: Int) : Int{
            return when (leagueId){
                TOP_LEAGUE_IDS[0] -> ContextCompat.getColor(context, R.color.premier_league_color)
                TOP_LEAGUE_IDS[1] -> ContextCompat.getColor(context, R.color.la_liga_color)
                TOP_LEAGUE_IDS[2] -> ContextCompat.getColor(context, R.color.bundesliga_color)
                TOP_LEAGUE_IDS[3] -> ContextCompat.getColor(context, R.color.ligue1_color)
                TOP_LEAGUE_IDS[4] -> ContextCompat.getColor(context, R.color.serie_a_color)
                else -> Color.GRAY
            }
        }

        fun getTeamStandingColorRes(teamRank: Int, leagueId: Int): Int{
            return when (leagueId){
                TOP_LEAGUE_IDS[0] -> getPremierLeagueStandingsColors(teamRank)
                TOP_LEAGUE_IDS[1] -> getLaligaStandingsColors(teamRank)
                TOP_LEAGUE_IDS[2] -> getBundesligaStandingsColors(teamRank)
                TOP_LEAGUE_IDS[3] -> getLigue1StandingsColors(teamRank)
                TOP_LEAGUE_IDS[4] -> getSerieAStandingsColors(teamRank)
                else -> Color.GRAY
            }
        }

        private fun getPremierLeagueStandingsColors(teamRank: Int): Int{
            return when (teamRank) {
                1 -> R.color.league_winner_divider_color
                in 2..5 -> R.color.champions_league_group_stage_qualified_teams_divider_color
                6 -> R.color.europa_league_group_stage_qualified_teams_divider_color
                in 18..20 -> R.color.league_relegation_teams_divider_color
                else -> R.color.default_divider_color
            }
        }
        private fun getLaligaStandingsColors(teamRank: Int): Int{
            return when (teamRank) {
                1 -> R.color.league_winner_divider_color
                in 2..4 -> R.color.champions_league_group_stage_qualified_teams_divider_color
                5 -> R.color.europa_league_group_stage_qualified_teams_divider_color
                6 -> R.color.europa_conference_league_qualifiers_teams_divider_color
                in 18..20 -> R.color.league_relegation_teams_divider_color
                else -> R.color.default_divider_color
            }
        }
        private fun getBundesligaStandingsColors(teamRank: Int): Int{
            return when (teamRank) {
                1 -> R.color.league_winner_divider_color
                in 2..4 -> R.color.champions_league_group_stage_qualified_teams_divider_color
                5 -> R.color.europa_league_group_stage_qualified_teams_divider_color
                6 -> R.color.europa_conference_league_qualifiers_teams_divider_color
                16 -> R.color.league_relegation_play_offs_teams_divider_color
                in 17..18 -> R.color.league_relegation_teams_divider_color
                else -> R.color.default_divider_color
            }
        }
        private fun getLigue1StandingsColors(teamRank: Int): Int{
            return when (teamRank) {
                1 -> R.color.league_winner_divider_color
                in 2..3 -> R.color.champions_league_group_stage_qualified_teams_divider_color
                4 -> R.color.champions_league_qualifiers_teams_divider_color
                5 -> R.color.europa_league_group_stage_qualified_teams_divider_color
                6 -> R.color.europa_conference_league_qualifiers_teams_divider_color
                16 -> R.color.league_relegation_play_offs_teams_divider_color
                in 17..18 -> R.color.league_relegation_teams_divider_color
                else -> R.color.default_divider_color
            }
        }
        private fun getSerieAStandingsColors(teamRank: Int): Int{
            return when (teamRank) {
                1 -> R.color.league_winner_divider_color
                in 2..4 -> R.color.champions_league_group_stage_qualified_teams_divider_color
                5 -> R.color.europa_league_group_stage_qualified_teams_divider_color
                6 -> R.color.europa_conference_league_qualifiers_teams_divider_color
                in 18..20 -> R.color.league_relegation_teams_divider_color
                else -> R.color.default_divider_color
            }
        }
    }
}