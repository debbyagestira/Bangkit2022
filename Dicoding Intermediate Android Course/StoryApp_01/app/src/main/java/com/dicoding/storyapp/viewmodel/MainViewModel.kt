package com.dicoding.storyapp.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.dicoding.storyapp.api.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: UserPreference) : ViewModel() {

    private val _listStory = MutableLiveData<List<ListStoryItem>>()
    val listStory: LiveData<List<ListStoryItem>> = _listStory

    private val _isLogin = MutableLiveData<Boolean>()
    val isLogin: LiveData<Boolean> = _isLogin

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun userLogin(email: String, password: String) {
        val client = ApiConfig.getApiService().userLogin(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    _isLogin.value = true
                    response.body()?.let { saveUser(it.loginResult) }
                } else {
                    _isLogin.value = false
                    Log.e(TAG, "on failure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLogin.value = false
                Log.e(TAG, "on failure: ${t.message.toString()}")
            }
        })
    }

    fun userRegister(name: String, email: String, password: String) {
        val client = ApiConfig.getApiService().userRegister(name, email, password)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    userLogin(email, password)
                } else {
                    Log.e(TAG, "on failure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLogin.value = false
                Log.e(TAG, "on failure: ${t.message.toString()}")
            }
        })
    }

    fun getAllStories(token: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getAllStories(token)
        client.enqueue(object : Callback<StoryResponse> {
            override fun onResponse(call: Call<StoryResponse>, response: Response<StoryResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listStory.value = response.body()?.listStory
                } else {
                    Log.e(TAG, "on failure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "on failure: ${t.message.toString()}")
            }
        })
    }

    fun getUser(): LiveData<LoginResult> {
        return pref.getUser().asLiveData()
    }

    fun saveUser(loginResult: LoginResult) {
        viewModelScope.launch { pref.saveUser(loginResult) }
    }

    fun userLogout() {
        viewModelScope.launch { pref.logout() }
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }
}