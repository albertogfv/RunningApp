package com.albertogfv.runningapp.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.albertogfv.runningapp.R
import com.albertogfv.runningapp.other.CustomMarkerView
import com.albertogfv.runningapp.other.TrackingUtility
import com.albertogfv.runningapp.ui.viewmodels.MainViewModel
import com.albertogfv.runningapp.ui.viewmodels.StatisticsViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
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
        setupBarChart()
    }

    private fun setupBarChart() {
        barChart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            setDrawLabels(false)
            axisLineColor = Color.WHITE
            textColor = Color.WHITE
            setDrawGridLines(false)
        }

        barChart.axisLeft.apply{
            axisLineColor = Color.WHITE
            textColor = Color.WHITE
            setDrawGridLines(false)
        }
        barChart.axisRight.apply{
            axisLineColor = Color.WHITE
            textColor = Color.WHITE
            setDrawGridLines(false)
        }
        barChart.apply{
            description.text = "Avg Speed Over Time"
            legend.isEnabled = false
        }
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

        viewModel.runsSortedByDate.observe(viewLifecycleOwner, androidx.lifecycle.Observer{
            it?.let {
                val allAvgSpeeds = it.indices.map{i -> BarEntry(i.toFloat(), it[i].avgSpeedInKMH) }
                val barDataSet = BarDataSet(allAvgSpeeds, "Avg Speed Over Time").apply{
                    valueTextColor = Color.WHITE
                    color = ContextCompat.getColor(requireContext(), R.color.colorAccent)
                }
                barChart.data = BarData(barDataSet)
                barChart.marker = CustomMarkerView(it.reversed(), requireContext(), R.layout.marker_view)
                barChart.invalidate()

            }
        })
    }
}
