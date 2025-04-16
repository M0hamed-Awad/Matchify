package com.example.matchify.ui.adpaters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.matchify.R
import com.example.matchify.databinding.MatchFixtureItemBinding
import com.example.matchify.models.FixtureModel
import com.example.matchify.utils.HelperFunctions

class FixtureAdapter(private var fixtures: List<FixtureModel>) :
    RecyclerView.Adapter<FixtureAdapter.FixtureViewHolder>() {

    inner class FixtureViewHolder(private val binding: MatchFixtureItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(fixtureModel: FixtureModel) {

            binding.apply {

                // Setting Fixture Card Background Color based on the League
                val backgroundColor = fixtureModel.fixtureLeague?.let {
                    HelperFunctions.getLeagueColor(
                        itemView.context,
                        it.id
                    )
                } ?: Color.GRAY

                root.setCardBackgroundColor(backgroundColor)

                // Setting Fixture Info Text Values
                homeTeamNameTv.text = fixtureModel.homeTeam?.name
                matchHomeTeamResultTv.text = fixtureModel.fixtureScore?.homeTeamScore.toString()

                awayTeamNameTv.text = fixtureModel.awayTeam?.name
                matchAwayTeamResultTv.text = fixtureModel.fixtureScore?.awayTeamScore.toString()

                matchDateTv.text =
                    HelperFunctions.formatFixtureDate(fixtureModel.date ?: "Unknown")
                matchTimeTv.text =
                    HelperFunctions.formatFixtureTime(fixtureModel.date ?: "Unknown")
                matchStadiumTv.text = fixtureModel.stadium

                // Setting Fixture Home & Away Teams Logos
                // Home Team Logo
                Glide.with(itemView.context)
                    .load(fixtureModel.homeTeam?.logo)
                    .into(homeTeamLogoIv)

                // Home Team Logo
                Glide.with(itemView.context)
                    .load(fixtureModel.awayTeam?.logo)
                    .into(awayTeamLogoIv)

                if (fixtureModel.homeTeam!!.isWinner) {
                    homeTeamLogoIv.setBackgroundResource(R.drawable.winner_team_logo_bg)
                    awayTeamLogoIv.setBackgroundResource(R.drawable.default_team_logo_bg)
                } else if (fixtureModel.awayTeam!!.isWinner) {
                    awayTeamLogoIv.setBackgroundResource(R.drawable.winner_team_logo_bg)
                    homeTeamLogoIv.setBackgroundResource(R.drawable.default_team_logo_bg)
                }
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FixtureViewHolder {
        val binding =
            MatchFixtureItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FixtureViewHolder(binding)
    }


    override fun onBindViewHolder(holder: FixtureViewHolder, position: Int) {
        holder.bind(fixtures[position])
    }


    override fun getItemCount(): Int = fixtures.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newFixtures: List<FixtureModel>) {
        this.fixtures = newFixtures
        notifyDataSetChanged()
    }
}