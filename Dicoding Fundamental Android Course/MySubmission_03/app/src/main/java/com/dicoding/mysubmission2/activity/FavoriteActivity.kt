package com.dicoding.mysubmission2.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mysubmission2.api.ItemsItem
import com.dicoding.mysubmission2.adapter.ListUserAdapter
import com.dicoding.mysubmission2.R
import com.dicoding.mysubmission2.database.ViewModelFactory
import com.dicoding.mysubmission2.database.mapToListItem
import com.dicoding.mysubmission2.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.favorite_user)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val layoutManager = LinearLayoutManager(this)
        binding.rvFavuser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvFavuser.addItemDecoration(itemDecoration)

        val favoriteViewModel = ViewModelFactory.obtainViewModel(this@FavoriteActivity)

        favoriteViewModel.getAllUser().observe(this) {
            setFavoriteUser(it.mapToListItem())
        }
    }

    private fun setFavoriteUser(listFavorite: List<ItemsItem>) {
        val listUserAdapter = ListUserAdapter(mutableListOf())
        listUserAdapter.setList(listFavorite)
        binding.rvFavuser.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {

                val favoriteToDetail = Intent(this@FavoriteActivity, DetailUserActivity::class.java)
                favoriteToDetail.putExtra(DetailUserActivity.EXTRA_USER, data)
                startActivity(favoriteToDetail)
            }
        })
    }
}