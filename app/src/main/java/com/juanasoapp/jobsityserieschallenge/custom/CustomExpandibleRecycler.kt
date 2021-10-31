package com.juanasoapp.jobsityserieschallenge.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.juanasoapp.jobsityserieschallenge.R
import com.juanasoapp.jobsityserieschallenge.seriesdetail.Episode
import kotlinx.android.synthetic.main.custom_expandable_recycler.view.*
import kotlinx.android.synthetic.main.custom_expandable_recycler_item.view.*

class CustomExpandableRecycler @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var genericAdapter: GenericAdapter<Any>? = null
    var onItemClick: ((Episode) -> Unit)? = null

    init {
        inflate(getContext(), R.layout.custom_expandable_recycler, this)
        setUpListeners()
        setUpAdapter()
    }

    fun setSeasonTitle(title: String) {
        custom_expandable_recycler_title.text = title
    }

    private fun setUpListeners() {
        custom_expandable_recycler_title_root.setOnClickListener {
            custom_expandable_recycler_list.visibility =
                when (custom_expandable_recycler_list.visibility) {
                    View.VISIBLE -> {
                        View.GONE
                    }
                    View.GONE -> {
                        View.VISIBLE
                    }
                    else -> View.GONE
                }
        }
    }

    private fun setUpAdapter() {
        genericAdapter = object : GenericAdapter<Any>(emptyList()) {
            override fun getLayoutId(position: Int, obj: Any): Int {
                return R.layout.custom_expandable_recycler_item
            }

            override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
                return CustomExpandableRecyclerViewHolder(view) {
                    onItemClick?.invoke(it)
                }
            }
        }
        custom_expandable_recycler_list.adapter = genericAdapter
    }

    fun setEpisodes(episodes: List<Episode>) {
        genericAdapter?.setItems(episodes)
    }
}

class CustomExpandableRecyclerViewHolder(itemView: View, var onClick: (Episode) -> Unit) :
    RecyclerView.ViewHolder(itemView),
    GenericAdapter.Binder<Episode> {

    private var title: TextView = itemView.custom_expandable_recycler_item_title
    var root: View = itemView.custom_expandable_recycler_item_root

    override fun bind(data: Episode) {
        title.text = "${data.number} - ${data.name}"

        root.setOnClickListener { onClick.invoke(data) }
    }
}
