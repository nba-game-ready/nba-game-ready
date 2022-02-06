package com.example.nbagameready

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nbagameready.adapters.TodayAdapter
import com.example.nbagameready.databinding.FragmentTodayBinding
import com.example.nbagameready.network.Today
import okhttp3.internal.concurrent.Task
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class TodayFragment : Fragment() {

    companion object {
        fun newInstance() = TodayFragment()
    }
    private var _binding: FragmentTodayBinding? = null
    val binding get() = _binding!!
    private lateinit var todayAdapter: TodayAdapter
    private lateinit var viewModel: TodayViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var games: Task
    private lateinit var currentDate: String
    private lateinit var ai: ApplicationInfo


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
        recyclerView.layoutManager = LinearLayoutManager(context)
        todayAdapter = TodayAdapter()
        recyclerView.
        adapter = todayAdapter

        getGames()

    }


    fun getGames() {
        ai = context?.packageManager?.getApplicationInfo(context!!.packageName, PackageManager.GET_META_DATA)!!
        val value = ai.metaData["keyValue"]
        val key = value.toString()
        currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
            Date()
        )

        val call = NbaApi.retrofitService.getGames(currentDate, key)

        call.enqueue(object : Callback<Today> {

            override fun onFailure(call: Call<Today>, t: Throwable) {

                Log.e("MainActivity", "Failed to get games", t)

            }

            override fun onResponse(

                call: Call<Today>,

                response: Response<Today>

            ) {
                if (response.isSuccessful) {


                    todayAdapter.notifyDataSetChanged()

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