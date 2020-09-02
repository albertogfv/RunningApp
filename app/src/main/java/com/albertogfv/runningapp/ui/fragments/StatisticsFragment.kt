package com.albertogfv.runningapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.albertogfv.runningapp.R
import com.albertogfv.runningapp.other.TrackingUtility
import com.albertogfv.runningapp.ui.viewmodels.MainViewModel
import com.albertogfv.runningapp.ui.viewmodels.StatisticsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_statistics.*
import kotlinx.android.synthetic.main.item_run.*
import java.lang.Math.round
import java.util.Observer
import kotlin.math.roundToInt

@AndroidEntryPoint
class StatisticsFragment : Fragment(R.layout.fragment_statistics) {

    private val viewModel: StatisticsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToObservers()
    }

    private  fun subscribeToObservers(){
        viewModel.totalTimeRun.observe(viewLifecycleOwner, androidx.lifecycle.Observer{
            it?.let{
                val totalTimeRun = TrackingUtility.getFormattedStopWatchTimer(it)
                tvTotalTime.text = totalTimeRun
            }
        })
        viewModel.totalDistance.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let{
                val km = it / 1000f
                val totalDistance = round(km * 10f) / 10f
                val totalDistanceString = "${totalDistance}km"
                tvTotalDistance.text = totalDistanceString
            }
        })

        viewModel.totalAvgSpeed.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let{
                val avgSpeed = round(it * 10f) /10f
                val avgSpeedString = "${avgSpeed}km/h"
                tvAvgSpeed.text =avgSpeedString
            }
        })

        viewModel.totalCaloriesBurned.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let{
                val totalCalories = "${it}kcal"
                tvTotalCalories.text  = totalCalories
            }
        })
    }
}
