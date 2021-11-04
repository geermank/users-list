package com.geermank.userslist.domain.usecase

import com.geermank.userslist.data.UsersRepository
import com.geermank.userslist.domain.UserCreationManipulation
import com.geermank.userslist.domain.UserManipulation
import com.geermank.userslist.domain.UserModificationManipulation
import com.geermank.userslist.presentation.users.manipulation.EditableUser
import com.geermank.userslist.presentation.users.manipulation.NewUser
import javax.inject.Inject

class PerformUserManipulationUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) : UseCase<EditableUser, Boolean> {

    override suspend fun execute(param: EditableUser): Boolean {
        val dataManipulation = getManipulationToPerform(param)
        if (dataManipulation.validate(param)) {
            dataManipulation.performManipulation(usersRepository, param)
            return true
        }
        return false
    }

    private fun getManipulationToPerform(editableUser: EditableUser): UserManipulation {
        return if (editableUser is NewUser) {
            UserCreationManipulation()
        } else {
            UserModificationManipulation()
        }
    }
}