package com.example.matchify.ui.adpaters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.matchify.R
import com.example.matchify.databinding.StandingItemBinding
import com.example.matchify.models.StandingModel
import com.example.matchify.utils.Constants
import com.example.matchify.utils.HelperFunctions

class StandingsAdapter(
    private var standings: List<StandingModel>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // Main View Holder (Standings)
    inner class StandingViewHolder(private val binding: StandingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(standingModel: StandingModel) {
            binding.apply {
                teamRankTv.text = "${standingModel.teamRank}"
                teamNameTv.text = standingModel.teamName

                Glide
                    .with(itemView.context)
                    .load(standingModel.teamLogo)
                    .into(teamLogoIv)

                matchesPlayedTv.text = "${standingModel.matchesPlayed}"
                matchesWonTv.text = "${standingModel.matchesWon}"
                matchesDrawnTv.text = "${standingModel.matchesDrawn}"
                matchesLostTv.text = "${standingModel.matchesLost}"
                goalsForTv.text = "${standingModel.goalsFor}"
                goalsAgainstTv.text = "${standingModel.goalsAgainst}"
                goalDifferenceTv.text = "${standingModel.goalsDifference}"
                teamPointsTv.text = "${standingModel.points}"

                val colorRes = HelperFunctions.getTeamStandingColorRes(
                    teamRank = standingModel.teamRank,
                    leagueId = standingModel.leagueId
                )

                teamStandingDivider.dividerColor =
                    ContextCompat.getColor(itemView.context, colorRes)
            }
        }

    }

    // Footer View Holder
    inner class FooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        // Inflating Standing Item Layout
        return if (viewType == Constants.VIEW_TYPE_STANDING){
            val binding =
                StandingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            StandingViewHolder(binding)
        }
        // Inflating Footer Item Layout (Standings Colors Meanings)
        else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.standings_footer, parent, false)
            FooterViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // Binding only the Standings Items
        // NO Binding needed for Footer
        if (holder is StandingViewHolder && position < standings.size) {
                holder.bindData(standings[position])
        }
    }

    // Adding 1 for the recycler view Footer
    override fun getItemCount(): Int = standings.size + 1

    override fun getItemViewType(position: Int): Int {
        // If it more than or equal the standings list size then it's the Footer
        return if (position < standings.size) Constants.VIEW_TYPE_STANDING else Constants.VIEW_TYPE_FOOTER
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newStandings: List<StandingModel>) {
        this.standings = newStandings
        notifyDataSetChanged()
    }
}