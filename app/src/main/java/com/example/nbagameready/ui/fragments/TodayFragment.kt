package com.example.nbagameready.ui.fragments

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nbagameready.R
import com.example.nbagameready.ui.adapters.TodayAdapter
import com.example.nbagameready.databinding.FragmentTodayBinding
import com.example.nbagameready.network.nbaapi.Games
import com.example.nbagameready.viewmodels.TodayViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TodayFragment : Fragment() {
    /***
     * Today Fragment to showcase the games that are airing today
     */


    private var _binding: FragmentTodayBinding? = null
    val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private val viewModel: TodayViewModel by activityViewModels()


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


        getNBAGameResponse()

        binding.button.setOnClickListener {
            findNavController().navigate(TodayFragmentDirections.actionTodayFragmentToYesterdayFragment())
        }

        binding.button3.setOnClickListener {
            findNavController().navigate(TodayFragmentDirections.actionTodayFragmentToTomorrowFragment())
        }
    }

    override fun onResume() {
        super.onResume()

    }

    private fun getNBAGameResponse() {

        viewModel.apiResponse.observe(viewLifecycleOwner) { response ->
            response.clone().enqueue(object : Callback<Games> {
                override fun onResponse(call: Call<Games>, response: Response<Games>) {
                    if (response.isSuccessful) {
                        if (response.body()?.api?.games?.size == 0) {
                            binding.noGamesToday.visibility = View.VISIBLE
                            binding.recyclerview.visibility = View.INVISIBLE

                        } else {
                            binding.noGamesToday.visibility = View.INVISIBLE
                            binding.recyclerview.visibility = View.VISIBLE
                            recyclerView.apply {
                                recyclerView.layoutManager = LinearLayoutManager(context)
                                adapter = response.body()?.let { TodayAdapter(it) }
                                recyclerView.adapter = adapter

                            }
                        }


                    } else {

                        Log.e(

                            "MainActivity",

                            "Failed to get games${response.errorBody()?.string() ?: ""}"
                        )

                    }
                }

                override fun onFailure(call: Call<Games>, t: Throwable) {
                    Log.e("MainActivity", "Failed to get games", t)
                }

            })
        }
    }
}



