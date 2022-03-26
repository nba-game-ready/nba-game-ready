package com.example.nbagameready.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.nbagameready.R
import com.example.nbagameready.databinding.FragmentTwitterBinding
import com.example.nbagameready.network.nbaapi.Games
import com.example.nbagameready.network.twitterapi.Tweets
import com.example.nbagameready.ui.adapters.TodayAdapter
import com.example.nbagameready.ui.adapters.TwitterAdapter
import com.example.nbagameready.viewmodels.TwitterViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TwitterFragment : Fragment() {

    private var _binding: FragmentTwitterBinding? = null
    val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    //lateinit var swipeContainer: SwipeRefreshLayout
    private val viewmodel: TwitterViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTwitterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerview
        // Lookup the swipe container view
        // swipeContainer = binding.swipeContainer


        // Setup refresh listener which triggers new data loading


        getTweets()


        // Configure the refreshing colors
//        swipeContainer.setColorSchemeResources(
//            android.R.color.holo_blue_bright,
//            android.R.color.holo_green_light,
//            android.R.color.holo_orange_light,
//            android.R.color.holo_red_light
//        )


    }

    private fun getTweets() {
        viewmodel.apiResponse.observe(viewLifecycleOwner) { response ->
            recyclerView.apply {
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.adapter = TwitterAdapter(response)
            }
        }
    }


}