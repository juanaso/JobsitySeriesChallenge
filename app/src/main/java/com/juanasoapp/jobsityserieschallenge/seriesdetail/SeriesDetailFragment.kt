package com.juanasoapp.jobsityserieschallenge.seriesdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.juanasoapp.jobsityserieschallenge.R
import com.juanasoapp.jobsityserieschallenge.custom.CustomExpandableRecycler
import com.juanasoapp.jobsityserieschallenge.serieslist.Series
import com.juanasoapp.jobsityserieschallenge.setTextHTML
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_series_detail.*
import kotlinx.android.synthetic.main.fragment_series_detail.view.*
import javax.inject.Inject

@AndroidEntryPoint
class SeriesDetailFragment : Fragment() {

    private val args: SeriesDetailFragmentArgs by navArgs()
    lateinit var currentSeries: Series

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
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.series_detail_root.isNestedScrollingEnabled = false
        setUpViewModel()
        setUpEpisodesObserver(view)
        setUpObserverLoader(view)
        viewModel.getEpisodes(currentSeries.id)
        setUpDetails(view)
    }

    private fun setUpEpisodesObserver(view: View) {
        viewModel.episodes.observe(this as LifecycleOwner) { seriesList ->
            val setSeasons = hashSetOf<String>()
            val seasons = arrayListOf<List<Episode>>()
            seriesList.getOrNull()?.let { episodeList ->
                episodeList.forEach {
                    setSeasons.add(it.season)
                }
                setSeasons.forEach { season ->
                    seasons.add(episodeList.filter { it.season == season })
                }
                setUpSeasonLinear(seasons, view)
            }
        }
    }

    private fun setUpSeasonLinear(seasons: ArrayList<List<Episode>>, view: View) {
        seasons.forEach { season ->
            val seasonTitle = "season ${season[0].season}"
            val customExpandableRecycler = this.context?.let { CustomExpandableRecycler(it) }
            customExpandableRecycler?.setEpisodes(season)
            customExpandableRecycler?.setSeasonTitle(seasonTitle)
            customExpandableRecycler?.onItemClick = {

            }

            view.linear_seasons_container.addView(customExpandableRecycler)
        }
    }

    private fun setUpDetails(view: View) {
        this.context?.let {
            Glide.with(it)
                .load(currentSeries.image?.original)
                .fallback(R.mipmap.no_image_placeholder)
                .into(view.series_detail_image)
        }

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
                    view.series_detail_loader.visibility = View.VISIBLE
                }
                false -> {
                    view.series_detail_loader.visibility = View.GONE
                }
            }
        }
    }

    private fun getDays(): String {
        var auxText = ""
        currentSeries.schedule?.days?.forEach {
            auxText += if (auxText.isEmpty()) {
                it
            } else {
                "- $it"
            }
        }
        return auxText
    }

    private fun getGenres(): String {
        var auxText = ""
        currentSeries.genres?.forEach {
            auxText += if (auxText.isEmpty()) {
                it
            } else {
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