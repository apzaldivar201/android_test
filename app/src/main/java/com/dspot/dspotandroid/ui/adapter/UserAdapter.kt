package com.dspot.dspotandroid.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dspot.dspotandroid.data.model.Result
import com.dspot.dspotandroid.databinding.ItemUserBinding

class UserAdapter : PagingDataAdapter<Result,
        UserAdapter.ImageViewHolder>(diffCallback) {


    inner class ImageViewHolder(
        val binding: ItemUserBinding
    ) :
        ViewHolder(binding.root)

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currChar = getItem(position)

        holder.binding.apply {

            holder.itemView.apply {
                tvFullName.text = "${currChar?.name?.title} ${currChar?.name?.first} ${currChar?.name?.last}"
                tvEmail.text = currChar?.email

                val imageLink = currChar?.picture?.thumbnail
                Glide.with(holder.binding.root)
                    .load(imageLink)
                    .transform(CircleCrop())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(profileImage)
            }
        }
    }
}