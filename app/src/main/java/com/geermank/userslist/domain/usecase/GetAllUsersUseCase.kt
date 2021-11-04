package com.geermank.userslist.domain.usecase

import com.geermank.userslist.data.UsersRepository
import com.geermank.userslist.domain.toUiModel
import com.geermank.userslist.presentation.users.model.UserUiModel
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) : UseCase<Unit, List<UserUiModel>> {

    override suspend fun execute(param: Unit): List<UserUiModel> {
        return usersRepository.getAllUsers().toUiModel()
    }
}
