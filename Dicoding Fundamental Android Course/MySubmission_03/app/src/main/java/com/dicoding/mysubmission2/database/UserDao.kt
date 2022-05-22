package com.dicoding.mysubmission2.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUser (user: User)

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun getAllUser(): LiveData<List<User>>

    @Query("DELETE FROM user_table WHERE username = :username")
    fun deleteUser (username: String)

}