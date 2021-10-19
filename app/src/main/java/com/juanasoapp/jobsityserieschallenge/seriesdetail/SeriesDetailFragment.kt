package com.juanasoapp.jobsityserieschallenge.seriesdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.juanasoapp.jobsityserieschallenge.R
import com.juanasoapp.jobsityserieschallenge.serieslist.Series
import com.juanasoapp.jobsityserieschallenge.setTextHTML
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_series_detail.view.*
import kotlinx.android.synthetic.main.fragment_series_list.view.*
import javax.inject.Inject

@AndroidEntryPoint
class SeriesDetailFragment : Fragment() {

    private val args : SeriesDetailFragmentArgs by navArgs()
    lateinit var currentSeries:Series

    lateinit var viewModel: SeriesDetailViewModel

    @Inject
    lateinit var viewModelFactory: SeriesDetailViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_series_detail, container, false)
        currentSeries = args.currentSeries

        setUpViewModel()

        viewModel.episodes.observe(this as LifecycleOwner) { seriesList ->
            if (seriesList.getOrNull() != null) {
                //setUpList(view, seriesList.getOrNull()!!)
            }
        }
        setUpDetails(view)
        setUpObserverLoader(view)

        return view
    }

    private fun setUpDetails(view: View) {
        view.series_name.text = currentSeries.name
        view.series_sumary.text = currentSeries.summary?.let { setTextHTML(it) }
        view.series_airtime_time.text = currentSeries.schedule?.time
        view.series_airtime_days.text = getDays()
        view.series_genres.text = getGenres()
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(SeriesDetailViewModel::class.java)
    }

    private fun setUpObserverLoader(view: View) {
        viewModel.loader.observe(this as LifecycleOwner) { loading ->
            when (loading) {
                true -> {
                    view.series_list_loader.visibility = View.VISIBLE
                }
                false -> {
                    view.series_list_loader.visibility = View.GONE
                }
            }
        }
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