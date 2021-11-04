package com.geermank.userslist.data.cache

interface CacheStrategy<CachedType, IdType> {
    suspend fun loadAll(): List<CachedType>
    suspend fun save(value: CachedType)
    suspend fun update(value: CachedType)
    suspend fun getById(id: IdType): CachedType?
    suspend fun delete(id: IdType)
}