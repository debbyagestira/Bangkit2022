package com.dicoding.mysubmission2.activity

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.mysubmission2.*
import com.dicoding.mysubmission2.adapter.SectionsPagerAdapter
import com.dicoding.mysubmission2.api.ItemsItem
import com.dicoding.mysubmission2.api.MainViewModel
import com.dicoding.mysubmission2.database.User
import com.dicoding.mysubmission2.database.ViewModelFactory
import com.dicoding.mysubmission2.databinding.ActivityDetailUserBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.detail_user)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val detail = intent.getParcelableExtra<ItemsItem>(EXTRA_USER) as ItemsItem
        val bundle = Bundle()
        bundle.putString(EXTRA_USER, detail.toString())


        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]
        val favoriteViewModel = ViewModelFactory.obtainViewModel(this@DetailUserActivity)

        var exist = false
        favoriteViewModel.getAllUser().observe(this) { listFavorite ->
            exist = listFavorite.any { it.username == detail.username }
            if (exist) {
                binding.btnFav.changeIconColor(R.color.red)
            }
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

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

            if (it.company.isNullOrEmpty()||it.location.isNullOrEmpty()) {
                binding.tvCompany.visibility = View.GONE
                binding.tvLocation.visibility = View.GONE
            }
        }

        mainViewModel.findUserProfile(detail.username)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, detail.username)
        val viewPager2: ViewPager2 = findViewById(R.id.view_pager)
        viewPager2.adapter = sectionsPagerAdapter

        val tabLayout: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabLayout, viewPager2) { tabs, position ->
            tabs.text = resources.getString(TAB_TITLES[position])
        }.attach()

        val btnFavorite: FloatingActionButton = binding.btnFav
        btnFavorite.setOnClickListener {
            if (exist) {
                Toast.makeText(this, "Removed from Favorite", Toast.LENGTH_SHORT).show()
                favoriteViewModel.deleteUser(detail.username)
                binding.btnFav.changeIconColor(R.color.white)
            } else {
                Toast.makeText(this, "Added to Favorite", Toast.LENGTH_SHORT).show()
                favoriteViewModel.addUser(User(0, detail.username, detail.avatarUrl))
                binding.btnFav.changeIconColor(R.color.red)
            }
        }
    }

    private fun FloatingActionButton.changeIconColor(@ColorRes color: Int) {
        val colour = ContextCompat.getColor(this.context, color)
        imageTintList = ColorStateList.valueOf(colour)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_USER = "extra_user"
        private val TAB_TITLES = intArrayOf(
            R.string.tab_1,
            R.string.tab_2
        )
    }

    override fun onClick(view: View?) {

    }
}