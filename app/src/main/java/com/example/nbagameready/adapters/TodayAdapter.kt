package com.example.nbagameready.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.nbagameready.R
import com.example.nbagameready.network.Today
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView



class TodayAdapter: ListAdapter<Today, TodayAdapter.TodayViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.todays_list, parent, false)
        return TodayViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodayViewHolder, position: Int) {
        val game = getItem(position)
        holder.awayTeamName.text = game.api.games[position].vTeam.fullName
        holder.awayTeamScore.text = game.api.games[position].vTeam.score.points
        holder.homeTeamScore.text = game.api.games[position].hTeam.score.points
        holder.homeTeamName.text = game.api.games[position].hTeam.fullName
        Glide.with(holder.itemView.context).load(game.api.games[position].vTeam.logo ).into(holder.awayTeamImage)
        Glide.with(holder.itemView.context).load(game.api.games[position].hTeam.logo ).into(holder.homeTeamImage)




    }



    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Today>() {
            override fun areItemsTheSame(oldItem: Today, newItem: Today): Boolean{
                return oldItem.api == newItem.api
            }
            override fun areContentsTheSame(oldItem: Today, newItem: Today): Boolean
            {
                return oldItem == newItem
            }

        }
    }
    inner class TodayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val homeTeamImage: ImageView = itemView.findViewById(R.id.home_team_image)
        val awayTeamImage: ImageView = itemView.findViewById(R.id.away_team_image)
        val awayTeamName: TextView = itemView.findViewById(R.id.away_team)
        val homeTeamName: TextView = itemView.findViewById(R.id.home_team)
        val homeTeamScore: TextView = itemView.findViewById(R.id.home_team_score)
        val awayTeamScore: TextView = itemView.findViewById(R.id.away_team_score)

    }

}




