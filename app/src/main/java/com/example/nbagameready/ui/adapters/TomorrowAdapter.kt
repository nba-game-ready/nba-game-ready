package com.example.nbagameready.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import androidx.recyclerview.widget.RecyclerView
import com.example.nbagameready.network.nbaapi.Games
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import android.content.Intent
import android.net.Uri
import com.example.nbagameready.R


class TomorrowAdapter(private val game: Games) : RecyclerView.Adapter<TomorrowAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(com.example.nbagameready.R.layout.tomorrows_list, parent, false)
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
        //val homeTeamScore: TextView = itemView.findViewById(com.example.nbagameready.R.id.home_team_score)
        //val awayTeamScore: TextView = itemView.findViewById(com.example.nbagameready.R.id.away_team_score)
        val gameStartTime: TextView = itemView.findViewById(R.id.time)
        val buyTicket: TextView = itemView.findViewById(R.id.BuyTix)


        fun bindView(tomorrow: Games) {
            awayTeamName.text = tomorrow.api.games.get(bindingAdapterPosition).vTeam.nickName
            //awayTeamScore.text = tomorrow.api.games.get(bindingAdapterPosition).vTeam.score.points
            //homeTeamScore.text = tomorrow.api.games.get(bindingAdapterPosition).hTeam.score.points
            homeTeamName.text = tomorrow.api.games.get(bindingAdapterPosition).hTeam.nickName
            Glide.with(itemView.context)
                .load(tomorrow.api.games.get(bindingAdapterPosition).vTeam.logo).into(awayTeamImage)
            Glide.with(itemView.context)
                .load(tomorrow.api.games.get(bindingAdapterPosition).hTeam.logo).into(homeTeamImage)

            val starTime = fmtDateTime(tomorrow.api.games.get(bindingAdapterPosition).startTimeUTC)
            if(starTime?.get(0).toString() == "0"){

                gameStartTime.text = starTime?.substring(1,starTime.length)
            } else{
                gameStartTime.text = starTime
            }



            buyTicket.setOnClickListener{
                    val intent = Intent()
                    intent.action = Intent.ACTION_VIEW
                    intent.addCategory(Intent.CATEGORY_BROWSABLE)
                    intent.data = Uri.parse("https://www.stubhub.com/nba-tickets/grouping/115/")
                    itemView.context.startActivity(intent)
                }

        }
        fun fmtDateTime(datetime: String): String? {
            val inDF: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            inDF.timeZone = TimeZone.getTimeZone("UTC")

            val aDate = inDF.parse(datetime)

            val outDF: DateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
            outDF.timeZone = TimeZone.getDefault()

            return outDF.format(aDate)
        }
    }

    override fun getItemCount(): Int {
        return game.api.games.size
    }


}





