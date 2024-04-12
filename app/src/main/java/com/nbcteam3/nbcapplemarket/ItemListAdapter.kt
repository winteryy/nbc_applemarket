package com.nbcteam3.nbcapplemarket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.nbcteam3.nbcapplemarket.databinding.ItemSellingListBinding
import com.nbcteam3.nbcapplemarket.model.Post
import com.nbcteam3.nbcapplemarket.util.toPriceText

class ItemListAdapter(private val itemTouchListener: ItemTouchListener): ListAdapter<Post, ItemListAdapter.ItemViewHolder>(DIFF_UTIL) {

    interface ItemTouchListener {
        fun onClick(item: Post)
        fun onLongClick(item: Post)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemSellingListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class ItemViewHolder(
        private val binding: ItemSellingListBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun onBind(post: Post) {
            binding.apply {
                itemImageView.load(post.img)
                itemTitleTextView.text = post.title
                sellerLocationTextView.text = post.location
                itemPriceTextView.text = post.price.toPriceText()
                chatNumTextView.text = post.chat.toString()
                favoriteNumTextView.text = post.favorite.toString()
                root.setOnClickListener {
                    itemTouchListener.onClick(post)
                }
                root.setOnLongClickListener {
                    itemTouchListener.onLongClick(post)
                    true
                }
            }
        }
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem.id==newItem.id
            }

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem==newItem
            }
        }
    }
}