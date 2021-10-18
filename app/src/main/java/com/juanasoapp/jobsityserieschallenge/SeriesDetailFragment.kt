package com.juanasoapp.jobsityserieschallenge

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.juanasoapp.jobsityserieschallenge.serieslist.Series
import kotlinx.android.synthetic.main.fragment_series_detail.view.*


class SeriesDetailFragment : Fragment() {

    private val args : SeriesDetailFragmentArgs by navArgs()
    lateinit var currentSeries:Series

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_series_detail, container, false)
        currentSeries = args.currentSeries

        view.series_name.text = currentSeries.name
        view.series_sumary.text = currentSeries.summary?.let { setTextHTML(it) }
        view.series_airtime_time.text = currentSeries.schedule?.time
        view.series_airtime_days.text = getDays()
        view.series_genres.text = getGenres()

        return view
    }

    private fun getDays(): String {
        var auxText = ""
        currentSeries.schedule?.days?.forEach {
            auxText += if(auxText.isEmpty()){
                it
            }else{
                "- $it"
            }
        }
        return auxText
    }

    private fun getGenres(): String {
        var auxText = ""
        currentSeries.genres?.forEach {
            auxText += if(auxText.isEmpty()){
                it
            }else{
                " - $it"
            }
        }
        return auxText
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            SeriesDetailFragment().apply {
            }
    }
}