package com.geermank.userslist.data.cache

import com.geermank.userslist.data.database.UsersDao
import com.geermank.userslist.data.model.UserDto
import javax.inject.Inject

class UsersCache @Inject constructor(
    private val usersDao: UsersDao
) : CacheStrategy<UserDto, Long> {

    override suspend fun loadAll(): List<UserDto> {
        return usersDao.getAll()
    }

    override suspend fun save(value: UserDto) {
        usersDao.save(value)
    }

    override suspend fun getById(id: Long): UserDto? {
        return usersDao.getById(id)
    }

    override suspend fun update(value: UserDto) {
        usersDao.update(value)
    }

    override suspend fun delete(id: Long) {
        usersDao.deleteById(id)
    }
}
