package com.dicoding.mysubmission2.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dicoding.mysubmission2.api.ItemsItem
import kotlinx.parcelize.Parcelize

@Entity(tableName = "user_table")
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val username: String,
    val avatarUrl: String
): Parcelable

fun List<User>.mapToListItem() : List<ItemsItem> =
    this.map { ItemsItem(it.avatarUrl, it.username) }
