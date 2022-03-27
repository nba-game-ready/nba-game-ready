package com.example.nbagameready.ui.adapters

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nbagameready.R
import com.example.nbagameready.network.nbaapi_teams.Team
import com.example.nbagameready.network.nbaapi_teams.Teams
import com.example.nbagameready.ui.MainActivity
import com.example.nbagameready.viewmodels.FavoriteTeamsViewModel




class TeamFavoritesAdapter(team: Teams, private val viewModel: FavoriteTeamsViewModel) : RecyclerView.Adapter<TeamFavoritesAdapter.ViewHolder>()

{
    private var todayList: List<Team>? = team.api?.teams?.filter { it.nbaFranchise == "1" && it.allStar == "0"}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.favorite_teams_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bindView()
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val homeTeamImage: ImageView = itemView.findViewById(R.id.home_team_image)
        private val homeTeamName: TextView = itemView.findViewById(R.id.home_team)
        private val favoriteButton: CheckBox = itemView.findViewById(R.id.checkBox)


        fun bindView() {
            val todayFilter: Array<Team>? = todayList?.toTypedArray()

            homeTeamName.text = todayFilter?.get(bindingAdapterPosition)?.nickname
            Glide.with(itemView.context).load(todayFilter?.get(bindingAdapterPosition)?.logo)
                .into(homeTeamImage)


            val teamId = todayFilter?.get(bindingAdapterPosition)?.teamId?.toInt()
            val teamFavorited = if (teamId != null) {
                viewModel.teamExists(teamId)
            } else { false }

            // Check the team's box if the ID is in database
            if (teamFavorited) {
                favoriteButton.isChecked = true
            }

            favoriteButton.setOnClickListener {

                if (!favoriteButton.isChecked) {

                    todayFilter?.get(bindingAdapterPosition)
                        ?.let { it1 -> it1.teamId?.let { it2 -> viewModel.deleteTeam(it2.toInt()) } }
                    Toast.makeText(
                        itemView.context,
                        "Team Removed from Favorites",
                        Toast.LENGTH_SHORT
                    ).show()

                    Log.i("Yunis",favoriteButton.isChecked.toString())

                }

                else {
                    if (!viewModel.teamExists(todayFilter?.get(bindingAdapterPosition)?.teamId!!.toInt())) {

                        todayFilter[bindingAdapterPosition]
                            .let { it1 -> viewModel.addTeam(it1) }
                        Toast.makeText(
                            itemView.context,
                            "Team Added to Favorites",
                            Toast.LENGTH_SHORT
                        ).show()

                        Log.i("Yunis","checked: " +favoriteButton.isSelected.toString() + "\nid: " + bindingAdapterPosition)

                    } else {
                        Toast.makeText(itemView.context,"Team is already favorited", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int{

        return todayList!!.size

    }

}