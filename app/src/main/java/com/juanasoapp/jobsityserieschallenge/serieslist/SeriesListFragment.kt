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
import com.google.gson.Gson
import com.juanasoapp.jobsityserieschallenge.R
import com.juanasoapp.jobsityserieschallenge.SeriesAPI
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@AndroidEntryPoint
class SeriesListFragment : Fragment() {

    lateinit var viewModel: SeriesListViewModel

    @Inject
    lateinit var viewModelFactory: SeriesListViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_series_list, container, false)

        setUpViewModel()
        setUpObservers(view)

        return view
    }

    private fun setUpObservers(view: View?) {
        viewModel.seriesList.observe(this as LifecycleOwner) { seriesList ->
            if (seriesList.getOrNull() != null) {
                setUpList(view, seriesList.getOrNull()!!)
            }
        }
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(SeriesListViewModel::class.java)
    }

    private fun setUpList(
        view: View?,
        seriesList: List<Series>
    ) {
        with(view as RecyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = MySeriesRecyclerViewAdapter(seriesList)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SeriesListFragment()
    }
}