package com.dicoding.myapplication2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.myapplication2.databinding.ActivityDetailUserBinding

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detail = intent.getParcelableExtra<ItemsItem>(EXTRA_USER) as ItemsItem

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        mainViewModel.detailUser.observe(this) {
            binding.tvName.text = it.name
            binding.tvUsername.text = StringBuilder("@"). append(it.login)
            binding.tvFollowers.text = it.followers.toString()
            binding.tvFollowing.text = it.following.toString()
            binding.tvRepository.text = StringBuilder("Repository • "). append(it.publicRepos.toString())
            binding.tvLocation.text = StringBuilder("Location • "). append(it.location)
            binding.tvCompany.text = StringBuilder("Company • "). append(it.company)
            Glide.with(this@DetailUserActivity)
                .load(detail.avatarUrl)
                .circleCrop()
                .into(binding.imgAvatar)
        }

        mainViewModel.findUserDetail(detail.username)
    }
}