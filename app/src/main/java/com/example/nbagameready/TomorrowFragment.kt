package com.example.nbagameready

import androidx.fragment.app.Fragment
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nbagameready.adapters.TomorrowAdapter
import com.example.nbagameready.databinding.FragmentTomorrowBinding
import com.example.nbagameready.network.Games
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

                    recyclerView.apply {
                        recyclerView.layoutManager = LinearLayoutManager(context)
                        adapter = response.body()?.let { TomorrowAdapter(it) }
                        recyclerView.adapter = adapter
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

