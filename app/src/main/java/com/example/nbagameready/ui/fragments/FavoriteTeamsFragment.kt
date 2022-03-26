package com.example.nbagameready.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nbagameready.databinding.FragmentTodayBinding
import com.example.nbagameready.network.nbaapi_teams.Teams
import com.example.nbagameready.ui.adapters.TeamFavoritesAdapter
import com.example.nbagameready.ui.adapters.TodayAdapter
import com.example.nbagameready.viewmodels.FavoriteTeamsViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteTeamsFragment : Fragment() {
    /***
     * Today Fragment to showcase the games that are airing today
     */


    private var _binding: FragmentTodayBinding? = null
    val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private val viewModel: FavoriteTeamsViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTodayBinding.inflate(inflater, container, false)
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
                            adapter = response.body()?.let { TeamFavoritesAdapter(it) }
                            recyclerView.adapter = adapter
                        }

                    } else {
                        Log.e(
                            "FavoriteTeamsFragment",
                            "Failed to get games${response.errorBody()?.string() ?: ""}"
                        )
                    }
                }
            })
        }
    }
}