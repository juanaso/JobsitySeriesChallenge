package com.juanasoapp.jobsityserieschallenge.serieslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import com.juanasoapp.jobsityserieschallenge.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_series_list.view.*
import javax.inject.Inject

@AndroidEntryPoint
class SeriesListFragment : Fragment() {

    lateinit var viewModel: SeriesListViewModel

    @Inject
    lateinit var viewModelFactory: SeriesListViewModelFactory

    var isFirstTime = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_series_list, container, false)
        setUpViewModel()
        setUpObserversRecycler(view.series_list)
        setUpObserverLoader(view)
        setUpSearchView(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isFirstTime) {
            viewModel.onTextSet("")
            isFirstTime = false
        }
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

    private fun setUpSearchView(view: View) {
        view.series_list_searchview.setOnClickListener {
            (it as SearchView).isIconified = false;
        }

        view.series_list_searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.onTextSet(query ?: "")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.isNotEmpty() == true) {
                    viewModel.onTextSet(newText)
                }
                return false
            }
        })
    }

    private fun setUpObserversRecycler(view: View?) {
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
            adapter = MySeriesRecyclerViewAdapter(seriesList, context) {
                val action =
                    SeriesListFragmentDirections.actionSeriesListFragmentToSeriesDetailFragment(it)
                findNavController().navigate(action)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SeriesListFragment()
    }
}