package com.juanasoapp.jobsityserieschallenge.serieslist

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.juanasoapp.jobsityserieschallenge.R

class MySeriesRecyclerViewAdapter(
    private val values: List<Series>,
    private val context: Context,
    private var listener:(Series) -> Unit
) : RecyclerView.Adapter<MySeriesRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.series_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.seriesName.text = item.name
        holder.root.setOnClickListener { listener.invoke(item)}
        Glide.with(context)
            .load(item.image?.original)
            .fallback(R.mipmap.no_image_placeholder)
//            .placeholder()
            .into(holder.imageView)

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.series_image)
        val seriesName: TextView = view.findViewById(R.id.series_name)
        val root: View = view.findViewById(R.id.series_list_item_root)

    }
}