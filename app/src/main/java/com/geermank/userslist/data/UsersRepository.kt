package com.geermank.userslist.data

import com.geermank.userslist.data.cache.UsersCache
import com.geermank.userslist.data.model.UserDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRepository @Inject constructor(
    private val usersCache: UsersCache
) {

    suspend fun getAllUsers(): List<UserDto> = usersCache.loadAll()

    suspend fun saveUser(user: UserDto) {
        usersCache.save(user)
    }

    suspend fun getUser(id: Long): UserDto? {
        return usersCache.getById(id)
    }

    suspend fun update(user: UserDto) {
        usersCache.update(user)
    }

    suspend fun deleteByUserId(userId: Long) {
        usersCache.delete(userId)
    }
}
