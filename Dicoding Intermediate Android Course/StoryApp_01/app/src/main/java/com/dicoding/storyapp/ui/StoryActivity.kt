package com.dicoding.storyapp.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.storyapp.*
import com.dicoding.storyapp.api.ListStoryItem
import com.dicoding.storyapp.databinding.ActivityStoryBinding
import com.dicoding.storyapp.viewmodel.MainViewModel
import com.dicoding.storyapp.adapter.ListStoryAdapter
import com.dicoding.storyapp.viewmodel.UserPreference
import com.dicoding.storyapp.viewmodel.ViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class StoryActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvStory.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvStory.addItemDecoration(itemDecoration)

        val addStory: FloatingActionButton = binding.btnAdd
        addStory.setOnClickListener {
            val intent = Intent(this@StoryActivity, AddStoryActivity::class.java)
            startActivity(intent)
        }

        setupViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        mainViewModel.userLogout()
        return true
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[MainViewModel::class.java]

        mainViewModel.getUser().observe(this) {
            val token = "Bearer ${it.token}"
            mainViewModel.getAllStories(token)
            if (it.token=="") {
                val intent = Intent(this@StoryActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Welcome ${it.name}!", Toast.LENGTH_SHORT).show()
            }
        }

        mainViewModel.listStory.observe(this) { items ->
            getAllStories(items)
        }
    }

    private fun getAllStories(items: List<ListStoryItem>){
        val listStoryAdapter = ListStoryAdapter()
        listStoryAdapter.setList(items)
        binding.rvStory.adapter = listStoryAdapter
    }
}