package com.geermank.userslist.data.database

import androidx.room.*
import com.geermank.userslist.data.model.UserDto

@Dao
interface UsersDao {

    @Query("SELECT * FROM Users")
    suspend fun getAll(): List<UserDto>

    @Query("SELECT * FROM Users WHERE id = :id")
    suspend fun getById(id: Long): UserDto?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(userDto: UserDto)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(userDto: UserDto)

    @Delete
    suspend fun delete(userDto: UserDto)

    @Query("DELETE FROM users WHERE id = :userId")
    suspend fun deleteById(userId: Long)
}