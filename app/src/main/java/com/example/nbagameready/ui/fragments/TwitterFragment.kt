package com.example.nbagameready.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nbagameready.databinding.FragmentTwitterBinding
import com.example.nbagameready.network.twitterapi.Tweets
import com.example.nbagameready.ui.adapters.TodayAdapter
import com.example.nbagameready.ui.adapters.TwitterAdapter
import com.example.nbagameready.viewmodels.TwitterViewModel

class TwitterFragment : Fragment() {

    private var _binding: FragmentTwitterBinding? = null
    val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
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

        getTweets()
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