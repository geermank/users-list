package com.geermank.userslist.domain.usecase

interface UseCase<ParamType, ReturnType> {
    suspend fun execute(param: ParamType): ReturnType
}