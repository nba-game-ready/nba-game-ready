package com.example.nbagameready.ui.fragments

import android.content.Intent
import androidx.fragment.app.Fragment
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nbagameready.R
import com.example.nbagameready.ui.adapters.TomorrowAdapter
import com.example.nbagameready.databinding.FragmentTomorrowBinding
import com.example.nbagameready.network.nbaapi.Games
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class TomorrowFragment : Fragment() {

    companion object {
        fun newInstance() = TomorrowFragment()
    }
    private var _binding: FragmentTomorrowBinding? = null
    val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var currentDate: String
    private lateinit var ai: ApplicationInfo


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
        binding.twitterTweets.setOnClickListener{
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.addCategory(Intent.CATEGORY_BROWSABLE)
            intent.data = Uri.parse("https://twitter.com/search?q=nba&src=typed_query")
            startActivity(intent)
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
        val date = SimpleDateFormat("yyyy-MM-dd").format(
            System.currentTimeMillis()
        )

        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val c = Calendar.getInstance()
        //Setting the date to the given date
        c.time = sdf.parse(date)

        c.add(Calendar.DAY_OF_MONTH, 2)
        val newDate = sdf.format(c.time)

        ai = context?.packageManager
            ?.getApplicationInfo(requireContext().packageName, PackageManager.GET_META_DATA)!!

        val value = ai.metaData["keyValue"]

        val key = value.toString()
        val call =
            NbaApi.retrofitService.getGames(newDate, key)

        call.enqueue(object : Callback<Games> {

            override fun onFailure(call: Call<Games>, t: Throwable) {

                Log.e("MainActivity", "Failed to get games", t)

            }

            override fun onResponse(

                call: Call<Games>,

                response: Response<Games>

            ) {
                if (response.isSuccessful) {
                    if (response.body()?.api?.games?.size  == 0){
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

        })
    }

}
