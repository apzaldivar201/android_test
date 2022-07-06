package com.dspot.dspotandroid.ui.finitelist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dspot.dspotandroid.data.model.Result
import com.dspot.dspotandroid.databinding.ItemUserBinding

class FiniteListAdapter(private val listener: UserItemListener) :
    RecyclerView.Adapter<UserViewHolder>() {

    interface UserItemListener {
        fun onClickedUser(item: Result)
    }

    private val items = ArrayList<Result>()

    fun setItems(items: ArrayList<Result>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding: ItemUserBinding =
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) =
        holder.bind(items[position])
}

class UserViewHolder(
    private val itemBinding: ItemUserBinding,
    private val listener: FiniteListAdapter.UserItemListener
) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var result: Result

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: Result) {
        this.result = item
        itemBinding.tvFullName.text = "${item.name.title} ${item.name.first} ${item.name.last}"
        itemBinding.tvEmail.text = item.email
        Glide.with(itemBinding.root)
            .load(item.picture.medium)
            .transform(CircleCrop())
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(itemBinding.profileImage)
    }

    override fun onClick(v: View?) {
        listener.onClickedUser(result)
    }
}

