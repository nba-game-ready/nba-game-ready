package com.example.nbagameready.ui.fragments


import androidx.fragment.app.Fragment
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
import com.example.nbagameready.ui.adapters.TomorrowAdapter
import com.example.nbagameready.databinding.FragmentTomorrowBinding
import com.example.nbagameready.network.nbaapi.Games
import com.example.nbagameready.viewmodels.TomorrowViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TomorrowFragment : Fragment() {

    private var _binding: FragmentTomorrowBinding? = null
    val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private val viewModel: TomorrowViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTomorrowBinding.inflate(inflater, container, false)

        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerview


        getNBAGameResponse()

        binding.button.setOnClickListener {
            findNavController().navigate(TomorrowFragmentDirections.actionTomorrowFragmentToYesterdayFragment())
        }

        binding.button2.setOnClickListener {
            findNavController().navigate(TomorrowFragmentDirections.actionTomorrowFragmentToTodayFragment())
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
                            binding.noGamesToday.visibility = View.VISIBLE
                            binding.recyclerview.visibility = View.INVISIBLE

                        } else {
                            binding.noGamesToday.visibility = View.INVISIBLE
                            binding.recyclerview.visibility = View.VISIBLE
                            recyclerView.apply {
                                recyclerView.layoutManager = LinearLayoutManager(context)
                                adapter = response.body()?.let { TomorrowAdapter(it) }
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

