package com.dicoding.storyapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.dicoding.storyapp.api.ListStoryItem
import com.dicoding.storyapp.databinding.ActivityDetailStoryBinding
import com.dicoding.storyapp.utils.DateHelper.withDateFormat

class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detail = intent.getParcelableExtra<ListStoryItem>(EXTRA_STORY) as ListStoryItem

        binding.apply {
            Glide.with(this@DetailStoryActivity)
                .load(detail.photoUrl)
                .into(imageView)
            tvName.text = detail.name
            tvCreatedAt.text = detail.createdAt.withDateFormat()
            tvDesription.text = detail.description
        }
    }

    companion object {
        const val EXTRA_STORY = "extra_story"
    }
}