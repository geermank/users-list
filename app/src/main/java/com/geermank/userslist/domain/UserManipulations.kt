package com.geermank.userslist.domain

import com.geermank.userslist.data.UsersRepository
import com.geermank.userslist.data.model.UserDto
import com.geermank.userslist.presentation.users.manipulation.EditableUser

interface UserManipulation {
    fun validate(editableUser: EditableUser): Boolean
    suspend fun performManipulation(repository: UsersRepository, editableUser: EditableUser)
}

class UserModificationManipulation : UserManipulation {

    override fun validate(editableUser: EditableUser): Boolean {
        return editableUser.newName.isNotEmpty() && editableUser.id != null
    }

    override suspend fun performManipulation(repository: UsersRepository, editableUser: EditableUser) {
        repository.update(createUser(editableUser))
    }

    private fun createUser(editable: EditableUser): UserDto {
        return with(editable) { UserDto(id, newName, newBio, newProfilePic) }
    }
}

class UserCreationManipulation : UserManipulation {

    override fun validate(editableUser: EditableUser): Boolean {
        return editableUser.newName.isNotEmpty()
    }

    override suspend fun performManipulation(repository: UsersRepository, editableUser: EditableUser) {
        repository.saveUser(createUser(editableUser))
    }

    private fun createUser(editableUser: EditableUser): UserDto {
        return with(editableUser) {
            UserDto(null, newName, newBio, newProfilePic)
        }
    }
}
