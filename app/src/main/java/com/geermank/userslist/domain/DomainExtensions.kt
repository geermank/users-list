package com.geermank.userslist.domain

import com.geermank.userslist.data.model.UserDto
import com.geermank.userslist.presentation.users.model.UserUiModel

fun List<UserDto>.toUiModel(): List<UserUiModel> {
    return map { UserUiModel(it.id!!, it.name, it.bio, it.pic) }
}