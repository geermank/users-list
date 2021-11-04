package com.geermank.userslist.presentation.users.manipulation

import android.os.Parcel
import android.os.Parcelable
import com.geermank.userslist.presentation.users.model.UserUiModel
import kotlinx.android.parcel.Parcelize

@Parcelize
open class EditableUser(
    var id: Long? = null,
    var newName: String = "",
    var newBio: String = "",
    var newProfilePic: String? = ""
) : Parcelable {

    constructor(userUiModel: UserUiModel) : this(
        userUiModel.id,
        userUiModel.name,
        userUiModel.bio,
        userUiModel.profilePic
    )
}

class NewUser(
    newName: String = "",
    newBio: String = "",
    newProfilePic: String? = null
) : EditableUser(null, newName, newBio, newProfilePic), Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
    )

    companion object CREATOR : Parcelable.Creator<NewUser> {
        override fun createFromParcel(parcel: Parcel): NewUser {
            return NewUser(parcel)
        }

        override fun newArray(size: Int): Array<NewUser?> {
            return arrayOfNulls(size)
        }
    }
}
