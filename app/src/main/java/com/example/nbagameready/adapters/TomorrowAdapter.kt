package com.example.nbagameready.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import androidx.recyclerview.widget.RecyclerView
import com.example.nbagameready.R
import com.example.nbagameready.network.Games
import java.lang.Exception
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

class TomorrowAdapter(private val game: Games) : RecyclerView.Adapter<TomorrowAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.tomorrows_list, parent, false)
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
        val awayTeamName: TextView = itemView.findViewById(R.id.away_team_name)
        val homeTeamName: TextView = itemView.findViewById(R.id.home_team_name)
        val homeTeamScore: TextView = itemView.findViewById(R.id.home_team_score)
        val awayTeamScore: TextView = itemView.findViewById(R.id.away_team_score)
        val gameStartTime: TextView = itemView.findViewById(R.id.time)


        fun bindView(tomorrow: Games) {
            awayTeamName.text = tomorrow.api.games.get(bindingAdapterPosition).vTeam.fullName
            awayTeamScore.text = tomorrow.api.games.get(bindingAdapterPosition).vTeam.score.points
            homeTeamScore.text = tomorrow.api.games.get(bindingAdapterPosition).hTeam.score.points
            homeTeamName.text = tomorrow.api.games.get(bindingAdapterPosition).hTeam.fullName
            gameStartTime.text = fmtDateTime(tomorrow.api.games.get(bindingAdapterPosition).startTimeUTC)
            Glide.with(itemView.context)
                .load(tomorrow.api.games.get(bindingAdapterPosition).vTeam.logo).into(awayTeamImage)
            Glide.with(itemView.context)
                .load(tomorrow.api.games.get(bindingAdapterPosition).hTeam.logo).into(homeTeamImage)

        }
        fun fmtDateTime(datetime: String): String? {
            val inDF: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            inDF.timeZone = TimeZone.getTimeZone("UTC")

            val aDate = inDF.parse(datetime)

            val outDF: DateFormat = SimpleDateFormat("hh:mm a")
            outDF.timeZone = TimeZone.getDefault()

            val strDateOut = outDF.format(aDate)
            return strDateOut
        }
    }

    override fun getItemCount(): Int {
        return game.api.games.size
    }


}





