package com.dicoding.storyapp.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.storyapp.ui.DetailStoryActivity
import com.dicoding.storyapp.api.ListStoryItem
import com.dicoding.storyapp.databinding.ItemRowStoryBinding

class ListStoryAdapter : RecyclerView.Adapter<ListStoryAdapter.ViewHolder>() {

    private val listStory = ArrayList<ListStoryItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<ListStoryItem>) {
        listStory.clear()
        listStory.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowStoryBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listStory[position])
    }

    override fun getItemCount(): Int = listStory.size

    inner class ViewHolder(private var binding: ItemRowStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListStoryItem) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(item.photoUrl)
                    .into(imgStory)
                tvUsername.text = item.name

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailStoryActivity::class.java)
                    intent.putExtra(DetailStoryActivity.EXTRA_STORY, item)

                    itemView.context.startActivity(
                        intent,
                        ActivityOptionsCompat.makeSceneTransitionAnimation(itemView.context as Activity)
                            .toBundle()
                    )
                }
            }
        }
    }






}