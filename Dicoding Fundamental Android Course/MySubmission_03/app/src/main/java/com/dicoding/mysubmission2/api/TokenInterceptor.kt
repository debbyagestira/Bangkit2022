package com.dicoding.mysubmission2.api

import com.dicoding.mysubmission2.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {
    private val mySuperSecretKey = BuildConfig.KEY
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("Authorization", "token $mySuperSecretKey")
                .build()
        )
    }
}