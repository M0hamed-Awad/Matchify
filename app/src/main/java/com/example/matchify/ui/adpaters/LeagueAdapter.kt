package com.example.matchify.ui.adpaters

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.matchify.R
import com.example.matchify.databinding.LeagueItemBinding
import com.example.matchify.models.LeagueData
import com.example.matchify.utils.HelperFunctions

class LeagueAdapter(
    private var leagues: List<LeagueData>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<LeagueAdapter.LeagueViewHolder>() {

    interface OnItemClickListener {
        fun onItemClicked(leagueData: LeagueData)
    }

    inner class LeagueViewHolder(private val binding: LeagueItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(leagueData: LeagueData) {
            binding.apply {
                // Getting League Color
                val leagueColor = HelperFunctions.getLeagueColor(itemView.context, leagueData.id)

                // Setting the League Name with the League Color
                leagueNameTv.text = leagueData.name
                leagueNameTv.setTextColor(leagueColor)

                // Setting the League Logo
                Glide
                    .with(itemView.context)
                    .load(leagueData.logo)
                    .into(leagueLogoIv)

                // Preparing the League Card Stroke and Background Style
                val drawable = GradientDrawable().apply {
                    shape = GradientDrawable.RECTANGLE
                    cornerRadius = 200f
                    setStroke(8, leagueColor)
                    color = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.league_item_bg_color
                        )
                    )
                }

                leagueInnerLayout.clipToOutline = true

                // Setting League Card Stroke and Background Style
                leagueInnerLayout.background = drawable

                root.setOnClickListener{
                    listener.onItemClicked(leagueData)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LeagueViewHolder {
        val binding = LeagueItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LeagueViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bindData(leagues[position])
    }

    override fun getItemCount(): Int = leagues.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newLeagues: List<LeagueData>) {
        this.leagues = newLeagues
        notifyDataSetChanged()
    }
}