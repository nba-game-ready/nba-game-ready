package com.example.nbagameready.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nbagameready.R

class TomorrowAdapter : ListAdapter<Tomorrow, TomorrowAdapter.TomorrowViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.tomorrows_list, parent, false)
        return TodayViewHolder(view)
    }

    override fun onBindViewHolder(holder: TomorrowViewHolder, position: Int) {
        val game = getItem(position)
        holder.bind(game)
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Tomorrow>() {
            override fun areItemsTheSame(oldItem: Tomorrow, newItem: Tomorrow): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Tomorrow, newItem: Tomorrow): Boolean {
                return oldItem == newItem
            }

        }
    }


    class TodayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(tomorrow: Tomorrow) {
            val homeTeamImage = itemView.findViewById<ImageView>(R.id.home_image)
            val awayTeamImage = itemView.findViewById<ImageView>(R.id.away_image)

            Glide.with(itemView.context).load(tomorrow.homeUrl).centerCrop().into(homeTeamImage)
            Glide.with(itemView.context).load(tomorrow.awayUrl).centerCrop().into(awayTeamImage)
        }
    }
}