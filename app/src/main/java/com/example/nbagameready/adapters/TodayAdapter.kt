package com.example.nbagameready.adapters

import android.content.Intent
import android.net.Uri
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
        val liveImage: ImageView = itemView.findViewById(R.id.live_image)
        val info: TextView = itemView.findViewById(R.id.info)
        val buyTicket: TextView = itemView.findViewById(R.id.BuyTix)


        fun bindView(today: Games){
            awayTeamName.text = today.api.games.get(bindingAdapterPosition).vTeam.nickName
            awayTeamScore.text = today.api.games.get(bindingAdapterPosition).vTeam.score.points
            homeTeamScore.text = today.api.games.get(bindingAdapterPosition).hTeam.score.points
            homeTeamName.text = today.api.games.get(bindingAdapterPosition).hTeam.nickName
            val currentPeriod = today.api.games.get(bindingAdapterPosition).currentPeriod
            var clock = today.api.games.get(bindingAdapterPosition).clock

            val halfOrClock = if(clock.isEmpty() && clock.isBlank()){

                "halftime".also { clock = it }
            } else{
                clock
            }
            "Q${currentPeriod[0]}: $halfOrClock".also { info.text = it }

            Glide.with(itemView.context).load(today.api.games.get(bindingAdapterPosition).vTeam.logo ).into(awayTeamImage)
            Glide.with(itemView.context).load(today.api.games.get(bindingAdapterPosition).hTeam.logo ).into(homeTeamImage)

            val gameStatus = today.api.games.get(bindingAdapterPosition).statusGame
            when (gameStatus) {
                "Scheduled" -> {
                    liveImage.visibility = View.INVISIBLE
                    awayTeamScore.visibility = View.INVISIBLE
                    homeTeamScore.visibility = View.INVISIBLE
                    info.visibility = View.INVISIBLE

                }
                "Finished" -> {
                    liveImage.visibility = View.INVISIBLE
                    awayTeamScore.visibility = View.VISIBLE
                    homeTeamScore.visibility = View.VISIBLE
                    info.visibility = View.INVISIBLE
                }
                else -> {
                    liveImage.visibility = View.VISIBLE
                    info.visibility = View.VISIBLE
                }
            }

            buyTicket.setOnClickListener{
                val intent = Intent()
                intent.action = Intent.ACTION_VIEW
                intent.addCategory(Intent.CATEGORY_BROWSABLE)
                intent.data = Uri.parse("https://www.stubhub.com/nba-tickets/grouping/115/")
                itemView.context.startActivity(intent)
            }


        }
    }

    override fun getItemCount(): Int {
        return game.api.games.size
    }


}





