package com.dicoding.mysubmission2.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _listUser = MutableLiveData<List<ItemsItem>>()
    val listUser: LiveData<List<ItemsItem>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    private val _listFollowers = MutableLiveData<ArrayList<ItemsItem>>()
    val listFollowers: LiveData<ArrayList<ItemsItem>> = _listFollowers

    private val _listFollowing = MutableLiveData<ArrayList<ItemsItem>>()
    val listFollowing: LiveData<ArrayList<ItemsItem>> = _listFollowing

    companion object {
        private const val TAG = "MainViewModel"
    }

    fun findUsername(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsername(username)
        client.enqueue(object : Callback<UsernameResponse> {
            override fun onResponse(
                call: Call<UsernameResponse>,
                response: Response<UsernameResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUser.value = response.body()?.items
                } else {
                    Log.e(TAG, "on failure: ${response.message()}")
                }
             }

            override fun onFailure(call: Call<UsernameResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "on failure: ${t.message.toString()}")
            }
        })
    }

    fun findUserProfile(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserProfile(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailUser.value = response.body()
                } else {
                    Log.e(TAG, "on failure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "on failure: ${t.message.toString()}")
            }
        })
    }

    fun findFollowers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<ArrayList<ItemsItem>> {
            override fun onResponse(
                call: Call<ArrayList<ItemsItem>>,
                response: Response<ArrayList<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollowers.value = response.body()
                } else {
                    Log.e(TAG, "on failure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<ItemsItem>>, t: Throwable) {
                Log.e(TAG, "on failure: ${t.message.toString()}")
            }
        })
    }

    fun findFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<ArrayList<ItemsItem>> {
            override fun onResponse(
                call: Call<ArrayList<ItemsItem>>,
                response: Response<ArrayList<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollowing.value = response.body()
                } else {
                    Log.e(TAG, "on failure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<ItemsItem>>, t: Throwable) {
                Log.e(TAG, "on failure: ${t.message.toString()}")
            }
        })
    }

}