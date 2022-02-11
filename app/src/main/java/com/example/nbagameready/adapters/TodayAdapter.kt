package com.example.nbagameready.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import androidx.recyclerview.widget.RecyclerView
import com.example.nbagameready.R
import com.example.nbagameready.network.Games

class TodayAdapter(private val game: Games) : RecyclerView.Adapter<TodayAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.todays_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bindView(game)


    }




    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val homeTeamImage: ImageView = itemView.findViewById(R.id.home_team_image)
        val awayTeamImage: ImageView = itemView.findViewById(R.id.away_team_image)
        val awayTeamName: TextView = itemView.findViewById(R.id.away_team)
        val homeTeamName: TextView = itemView.findViewById(R.id.home_team)
        val homeTeamScore: TextView = itemView.findViewById(R.id.home_team_score)
        val awayTeamScore: TextView = itemView.findViewById(R.id.away_team_score)

        fun bindView(today: Games){
            awayTeamName.text = today.api.games.get(bindingAdapterPosition).vTeam.fullName
            awayTeamScore.text = today.api.games.get(bindingAdapterPosition).vTeam.score.points
            homeTeamScore.text = today.api.games.get(bindingAdapterPosition).hTeam.score.points
            homeTeamName.text = today.api.games.get(bindingAdapterPosition).hTeam.fullName
            Glide.with(itemView.context).load(today.api.games.get(bindingAdapterPosition).vTeam.logo ).into(awayTeamImage)
            Glide.with(itemView.context).load(today.api.games.get(bindingAdapterPosition).hTeam.logo ).into(homeTeamImage)

        }
    }

    override fun getItemCount(): Int {
        return game.api.games.size
    }


}





