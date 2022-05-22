package com.dicoding.myapplication2

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

    fun findUserDetail(username: String) {
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
}