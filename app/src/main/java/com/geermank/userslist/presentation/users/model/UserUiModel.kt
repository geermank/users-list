package com.geermank.userslist.presentation.users.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserUiModel(
    val id: Long?,
    val name: String,
    val bio: String,
    val profilePic: String?
) : Parcelable