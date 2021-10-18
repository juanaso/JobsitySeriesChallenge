package com.juanasoapp.jobsityserieschallenge

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.juanasoapp.jobsityserieschallenge.serieslist.Series


class SeriesDetailFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    val args : SeriesDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_series_detail, container, false)
        val currentSeries = args.currentSeries

        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            SeriesDetailFragment().apply {
            }
    }
}