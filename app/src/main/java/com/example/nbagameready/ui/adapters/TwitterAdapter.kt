package com.example.nbagameready.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nbagameready.R
import com.example.nbagameready.network.twitterapi.Tweets

class TwitterAdapter (private val tweets: Tweets) :
    RecyclerView.Adapter<TwitterAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TwitterAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.twitter_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: TwitterAdapter.ViewHolder, position: Int) {
        return holder.bindView(tweets)
    }

    override fun getItemCount(): Int {
        return tweets.data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userTweet: TextView = itemView.findViewById(R.id.tweet)

        fun bindView(tweets: Tweets) {
            userTweet.text = tweets.data.get(bindingAdapterPosition).text

        }
    }
}