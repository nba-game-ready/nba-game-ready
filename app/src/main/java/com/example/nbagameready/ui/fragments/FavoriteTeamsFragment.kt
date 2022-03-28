package com.example.nbagameready.ui.fragments

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.getSystemService
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nbagameready.Application
import com.example.nbagameready.R
import com.example.nbagameready.databinding.FragmentFavoriteTeamsBinding
import com.example.nbagameready.databinding.FragmentTodayBinding
import com.example.nbagameready.network.nbaapi_teams.Team
import com.example.nbagameready.network.nbaapi_teams.Teams
import com.example.nbagameready.ui.MainActivity
import com.example.nbagameready.ui.adapters.TeamFavoritesAdapter
import com.example.nbagameready.ui.adapters.TodayAdapter
import com.example.nbagameready.viewmodels.FavoriteTeamsViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteTeamsFragment : Fragment() {



    private var _binding: FragmentFavoriteTeamsBinding? = null
    val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private val viewModel: FavoriteTeamsViewModel by viewModels {
        FavoriteTeamsViewModel.MainViewModelFactory((activity?.application as Application).database.favoriteTeamDao(),
            activity!!.application)
    }
    private lateinit var viewModel2: FavoriteTeamsViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFavoriteTeamsBinding.inflate(inflater, container, false)

        createChannel(
            getString(R.string.team_notifications_id),
            getString(R.string.team_notifications_name)
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerview



        getNBATeamsResponse()


    }

    override fun onResume() {
        super.onResume()

    }

    private fun getNBATeamsResponse() {
        viewModel.apiResponse.observe(this) { response ->
            response.clone().enqueue(object : Callback<Teams> {

                override fun onFailure(call: Call<Teams>, t: Throwable) {

                    Log.e("FavoriteTeamsFragment", "Failed to get games", t)
                }

                override fun onResponse(

                    call: Call<Teams>,
                    response: Response<Teams>
                ) {
                    if (response.isSuccessful) {

                        recyclerView.apply {
                            recyclerView.layoutManager = GridLayoutManager(context, 3)
                            viewModel2 = ViewModelProvider(this@FavoriteTeamsFragment)[FavoriteTeamsViewModel::class.java]
                            adapter = response.body()?.let { TeamFavoritesAdapter(it, viewModel2) }
                            recyclerView.adapter = adapter
                        }

                    } else {
                        Log.e(
                            "FavoriteTeamsFragment",
                            "Failed to get teams${response.errorBody()?.string() ?: ""}"
                        )
                    }
                }
            })
        }
    }

    private fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                setShowBadge(false)
            }
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.YELLOW
            notificationChannel.enableVibration(true)
            notificationChannel.description = getString(R.string.message_subscribe)

            val notificationManager = requireActivity().getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

//    private fun favoriteTeamsNotification() {
//        viewModel.apiResponse.observe(this) {
//            it.clone().enqueue(object : Callback<Teams>)
//
//    }
}