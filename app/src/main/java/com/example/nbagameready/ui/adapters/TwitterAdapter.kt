package com.example.nbagameready.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nbagameready.R
import com.example.nbagameready.network.twitterapi.Tweets
import com.example.nbagameready.network.twitterapi.User
import org.w3c.dom.Text
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

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
        return tweets.data?.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userTweet: TextView = itemView.findViewById(R.id.tweet)
        val userName: TextView = itemView.findViewById(R.id.userName)
        val fullName: TextView = itemView.findViewById(R.id.fullName)
        val time: TextView = itemView.findViewById(R.id.time)
        val profileImage: ImageView = itemView.findViewById(R.id.profileImage)


        fun bindView(tweets: Tweets) {
            userTweet.text = tweets.data.get(bindingAdapterPosition).text
            userName.text = tweets.includes?.users?.get(bindingAdapterPosition)?.username
            fullName.text = tweets.includes?.users?.get(bindingAdapterPosition)?.name
            time.text = fmtDateTime(tweets.data.get(bindingAdapterPosition).created_at)

            Glide.with(itemView.context)
                .load(tweets.includes.users.get(bindingAdapterPosition).profile_image_url)
                .circleCrop()
                .into(profileImage)


        }
    }

    fun fmtDateTime(datetime: String): String? {
        val inDF: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        inDF.timeZone = TimeZone.getTimeZone("UTC")

        val aDate = inDF.parse(datetime)

        val outDF: DateFormat = SimpleDateFormat("hh:mm a")
        outDF.timeZone = TimeZone.getDefault()

        return outDF.format(aDate)
    }
}