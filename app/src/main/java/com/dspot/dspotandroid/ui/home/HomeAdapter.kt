package com.dspot.dspotandroid.ui.home

import android.annotation.SuppressLint
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dspot.dspotandroid.data.model.DashboardItem
import com.dspot.dspotandroid.databinding.HomeItemBinding


class HomeAdapter(private val listener: HomeItemListener) : RecyclerView.Adapter<HomeViewHolder>() {

    interface HomeItemListener {
        fun onClickedItem(item: DashboardItem)
    }

    private val items = ArrayList<DashboardItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: ArrayList<DashboardItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding: HomeItemBinding =
            HomeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) =
        holder.bind(items[position])
}

class HomeViewHolder(
    private val itemBinding: HomeItemBinding,
    private val listener: HomeAdapter.HomeItemListener
) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {
    private lateinit var homeItem: DashboardItem

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: DashboardItem) {
        this.homeItem = item
        itemBinding.tvTitle.text = item.title

        if (homeItem.cant != 0) {
            itemBinding.cant.visibility = View.VISIBLE
            itemBinding.cant.text = "${homeItem.cant} Categorías"
        }

        if (homeItem.subtitle == "") {
            itemBinding.tvSubtitle.visibility = View.GONE
        } else {
            itemBinding.tvSubtitle.visibility = View.VISIBLE
            itemBinding.tvSubtitle.text = item.subtitle
        }

        itemBinding.backCard?.setBackgroundResource(item.background)

        Glide.with(itemBinding.root)
            .load(item.image)
            .into(itemBinding.ivIcon)

        when (homeItem.id) {
            0 -> {
                val params = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(16.toPx(), 30.toPx(), 8.toPx(), 8.toPx())
                itemBinding.root.layoutParams = params
            }
            1 -> {
                val params = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(8.toPx(), 30.toPx(), 16.toPx(), 8.toPx())
                itemBinding.root.layoutParams = params
            }
            2 -> {
                val params = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(8.toPx(), 8.toPx(), 16.toPx(), 45.toPx())
                itemBinding.root.layoutParams = params
            }
            3 -> {
                val params = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(16.toPx(), 8.toPx(), 8.toPx(), 45.toPx())
                itemBinding.root.layoutParams = params
            }
        }
    }

    override fun onClick(v: View?) {
        listener.onClickedItem(homeItem)
    }

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}

