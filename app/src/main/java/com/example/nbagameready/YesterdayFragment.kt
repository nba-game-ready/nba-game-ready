package com.example.nbagameready

import androidx.fragment.app.Fragment
import com.example.nbagameready.databinding.FragmentYesterdayBinding
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
import com.example.nbagameready.adapters.YesterdayAdapter
import com.example.nbagameready.network.Games
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class YesterdayFragment : Fragment() {

    companion object {
        fun newInstance() = YesterdayFragment()
    }
    private var _binding: FragmentYesterdayBinding? = null
    val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var currentDate: String
    private lateinit var ai: ApplicationInfo


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


    private fun getNBAGameResponse() {
        currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
            Date()
        )
        ai = context?.packageManager
            ?.getApplicationInfo(requireContext().packageName, PackageManager.GET_META_DATA)!!

        val value = ai.metaData["keyValue"]

        val key = value.toString()
        val call =
            NbaApi.retrofitService.getGames(currentDate, key)

        call.enqueue(object : Callback<Games> {

            override fun onFailure(call: Call<Games>, t: Throwable) {

                Log.e("Yesterday Fragment", "Failed to get games", t)

            }

            override fun onResponse(

                call: Call<Games>,

                response: Response<Games>

            ) {
                if (response.isSuccessful) {

                    recyclerView.apply {
                        recyclerView.layoutManager = LinearLayoutManager(context)
                        adapter = response.body()?.let { YesterdayAdapter(it) }
                        recyclerView.adapter = adapter
                    }

                } else {

                    Log.e(

                        "Yesterday Fragment",

                        "Failed to get games${response.errorBody()?.string() ?: ""}"
                    )

                }

            }

        })
    }

}

