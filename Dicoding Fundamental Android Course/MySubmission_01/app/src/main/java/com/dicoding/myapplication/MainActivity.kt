package com.dicoding.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var rvUser: RecyclerView
    private val list = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvUser = findViewById(R.id.rv_user)
        rvUser.setHasFixedSize(true)

        list.addAll(listUser)

        showRecyclerList()
    }

    private val listUser: ArrayList<User>
        get() {
            val dataUsername = resources.getStringArray(R.array.data_username)
            val dataName = resources.getStringArray(R.array.data_name)
            val dataAvatar = resources.obtainTypedArray(R.array.data_avatar)
            val dataFollowers = resources.getStringArray(R.array.data_followers)
            val dataFollowing = resources.getStringArray(R.array.data_following)
            val dataRepository = resources.getStringArray(R.array.data_repository)
            val dataLocation = resources.getStringArray(R.array.data_location)
            val dataCompany = resources.getStringArray(R.array.data_company)

            val listUser = ArrayList<User>()
            for (i in dataUsername.indices) {
                val user = User(
                    dataUsername[i],
                    dataName[i],
                    dataAvatar.getResourceId(i, -1),
                    dataFollowers[i],
                    dataFollowing[i],
                    dataRepository[i],
                    dataLocation[i],
                    dataCompany[i],
                    )
                listUser.add(user)
            }
            dataAvatar.recycle()
            return listUser
        }

    private fun showRecyclerList() {
        rvUser.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = ListUserAdapter(list)
        rvUser.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                showSelectedUser(data)

                val moveToDetailUserActivity = Intent(this@MainActivity, DetailUserActivity::class.java)
                    moveToDetailUserActivity.putExtra(DetailUserActivity.EXTRA_USER, data)
                startActivity(moveToDetailUserActivity)
            }
        })
    }

    private fun showSelectedUser(user: User) {
        Toast.makeText(this, "Detail of " + user.name, Toast.LENGTH_SHORT).show()
    }
}