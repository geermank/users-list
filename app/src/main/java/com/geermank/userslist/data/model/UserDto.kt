package com.geermank.userslist.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "Users")
@Parcelize
data class UserDto(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val name: String,
    val bio: String,
    val pic: String?
) : Parcelable