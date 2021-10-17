package com.juanasoapp.jobsityserieschallenge.serieslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.juanasoapp.jobsityserieschallenge.R
import com.juanasoapp.jobsityserieschallenge.SeriesAPI


class SeriesListFragment : Fragment() {

    lateinit var viewModel: SeriesListViewModel
    lateinit var viewModelFactory: SeriesListViewModelFactory
    lateinit var seriesListRepository: SeriesListRepository
    lateinit var seriesListService: SeriesListService
    lateinit var api : SeriesAPI

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_series_list, container, false)

        api = SeriesAPI()
        seriesListService = SeriesListService(api)

        seriesListRepository =
            SeriesListRepository(seriesListService)
        viewModelFactory =
            SeriesListViewModelFactory(
                seriesListRepository
            )
        viewModel = ViewModelProvider(this, viewModelFactory).get(SeriesListViewModel::class.java)

        viewModel.seriesList.observe(this as LifecycleOwner) { seriesList ->
            if (seriesList.getOrNull()!= null) {
                setUpList(view, seriesList.getOrNull()!!)
            }
        }

        return view
    }

    private fun setUpList(
        view: View?,
        seriesList: List<Series>
    ) {
        with(view as RecyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter =
                MySeriesRecyclerViewAdapter(
                    seriesList
                )
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SeriesListFragment()
    }
}