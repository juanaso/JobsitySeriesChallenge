package com.juanasoapp.jobsityserieschallenge

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.juanasoapp.jobsityserieschallenge.seriesdetail.Episode
import kotlinx.android.synthetic.main.fragment_episode_detail.view.*


class EpisodeDetailFragment : Fragment() {

    private val args: EpisodeDetailFragmentArgs by navArgs()

    lateinit var currentEpisode: Episode


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_episode_detail, container, false)
        currentEpisode = args.currentEpisode
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)

    }

    private fun setUpView(view: View) {
        this.context?.let {
            Glide.with(it)
                .load(currentEpisode.image?.original)
                .fallback(R.mipmap.no_image_placeholder)
                .into(view.episode_detail_image)
        }

        view.episode_detail_name.text = currentEpisode.name
        view.episode_season.text = "Season ${currentEpisode.season}"
        view.episode_number.text = "Episode ${currentEpisode.number}"
        view.episode_sumary.text = currentEpisode.summary?.let { setTextHTML(it) }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            EpisodeDetailFragment()
    }
}