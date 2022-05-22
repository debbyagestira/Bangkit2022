package com.dicoding.mysubmission2.database

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class UserMainViewModel(application: Application) : ViewModel() {
    private val mUserRepository: UserRepository = UserRepository(application)

    fun getAllUser(): LiveData<List<User>> = mUserRepository.getAllUser()

    fun addUser(user: User) {
        mUserRepository.addUser(user)
    }

    fun deleteUser(username: String) {
        mUserRepository.deleteUser(username)
    }
}