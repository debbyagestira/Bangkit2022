package com.dicoding.mysubmission2.database

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application) {
    private val mUserDao: UserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserDatabase.getDatabase(application)
        mUserDao = db.userDao()
    }

    fun getAllUser(): LiveData<List<User>> = mUserDao.getAllUser()

    fun addUser (user: User) {
        executorService.execute { mUserDao.addUser(user) }
    }

    fun deleteUser (username: String) {
        executorService.execute { mUserDao.deleteUser(username) }
    }

}