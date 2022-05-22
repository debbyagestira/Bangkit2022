package com.dicoding.mysubmission2.database

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ViewModelFactory private constructor(private val mApplication: Application) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE : ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application) : ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application)
                }
            }
            return INSTANCE as ViewModelFactory
        }

        fun obtainViewModel (activity: AppCompatActivity) : UserMainViewModel {
            val factory = getInstance(activity.application)
            return ViewModelProvider(activity, factory)[UserMainViewModel::class.java]
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserMainViewModel::class.java)) {
            return UserMainViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class ${modelClass.name}")
    }

}