package com.geermank.userslist.presentation.users.list

import com.geermank.userslist.data.UsersRepository
import com.geermank.userslist.domain.usecase.UseCase
import com.geermank.userslist.presentation.users.model.UserUiModel
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val repository: UsersRepository
) : UseCase<UserUiModel, Unit> {

    override suspend fun execute(param: UserUiModel) {
        repository.deleteByUserId(param.id!!)
    }
}