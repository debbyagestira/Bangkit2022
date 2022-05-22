package com.dicoding.myapplication

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val userName: String,
    val name: String,
    val avatar: Int,
    val followers: String,
    val following: String,
    val repository: String,
    val location: String,
    val company: String,
): Parcelable