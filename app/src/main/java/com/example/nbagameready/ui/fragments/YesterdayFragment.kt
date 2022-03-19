package com.example.nbagameready.ui.fragments


import androidx.fragment.app.Fragment
import com.example.nbagameready.databinding.FragmentYesterdayBinding
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nbagameready.R
import com.example.nbagameready.ui.adapters.YesterdayAdapter
import com.example.nbagameready.network.nbaapi.Games
import com.example.nbagameready.viewmodels.YesterdayViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class YesterdayFragment : Fragment() {

    private var _binding: FragmentYesterdayBinding? = null
    val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private val viewModel: YesterdayViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentYesterdayBinding.inflate(inflater, container, false)

        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerview


        getNBAGameResponse()

        binding.todayButton.setOnClickListener {
            findNavController().navigate(YesterdayFragmentDirections.actionYesterdayFragmentToTodayFragment())
        }

        binding.tomorrowButton.setOnClickListener {
            findNavController().navigate(YesterdayFragmentDirections.actionYesterdayFragmentToTomorrowFragment())
        }
    }
    
    override fun onResume() {
        super.onResume()
        animateGlobe()


    }
    private fun animateGlobe() {
        val rotate = AnimationUtils.loadAnimation(context, R.anim.rotate_animation)

        binding.ballBounce.animation = rotate
    }


    private fun getNBAGameResponse() {
        viewModel.apiResponse.observe(viewLifecycleOwner) { response ->
            response.clone().enqueue(object : Callback<Games> {
                override fun onResponse(call: Call<Games>, response: Response<Games>) {
                    if (response.isSuccessful) {
                        if (response.body()?.api?.games?.size == 0) {
                            binding.noGamesYesterday.visibility = View.VISIBLE
                            binding.recyclerview.visibility = View.INVISIBLE

                        } else {
                            binding.noGamesYesterday.visibility = View.INVISIBLE
                            binding.recyclerview.visibility = View.VISIBLE
                            recyclerView.apply {
                                recyclerView.layoutManager = LinearLayoutManager(context)
                                adapter = response.body()?.let { YesterdayAdapter(it) }
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

