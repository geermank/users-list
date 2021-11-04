package com.geermank.userslist.presentation.users.manipulation

import android.net.Uri
import androidx.annotation.StringRes
import androidx.lifecycle.*
import com.geermank.userslist.R
import com.geermank.userslist.domain.usecase.PerformUserManipulationUseCase
import com.geermank.userslist.presentation.BaseViewModel
import com.geermank.userslist.presentation.models.UiMessageModel
import com.geermank.userslist.presentation.users.model.UserUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val EDITABLE_USER_ARGUMENT_KEY = "USER_MANIPULATION_ARGUMENT_KEY"
private const val EDITION_ENABLE_STATE = "EDITION_ENABLE_STATE"

@HiltViewModel
class UserManipulationViewModel @Inject constructor(
    private val performUserManipulationUseCase: PerformUserManipulationUseCase,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _editableUser = MutableLiveData(getEditableUser())
    val editableUser: LiveData<EditableUser> = _editableUser

    /**
     *  only new users are editable at the beginning of the flow
     */
    private val _editionModeEnable = MutableLiveData(getEditionModeState())
    val editionModeEnable: LiveData<Boolean> = _editionModeEnable

    private val _userManipulationError = MutableLiveData<UiMessageModel>()
    val userManipulationError: LiveData<UiMessageModel> = _userManipulationError

    private val _userCreatedSuccessfully = MutableLiveData<UiMessageModel>()
    val userCreatedSuccessfully: LiveData<UiMessageModel> = _userCreatedSuccessfully

    fun shouldShowOptionsMenu(): Boolean {
        return editableUser.value !is NewUser
    }

    fun changeFormEnableState() {
        val formEnabledValue = editionModeEnable.value!!.not()
        _editionModeEnable.value = formEnabledValue
        savedStateHandle.set(EDITION_ENABLE_STATE, formEnabledValue)
    }

    fun changeUserProfilePic(profilePicUriString: String) {
        val editableUserValue = editableUser.value!!.also { it.newProfilePic = profilePicUriString }
        _editableUser.value = editableUserValue
        savedStateHandle.set(EDITABLE_USER_ARGUMENT_KEY, editableUserValue)
    }

    fun saveUserModifications(name: String, bio: String) {
        val editableUserValue = _editableUser.value!!

        editableUserValue.newName = name
        editableUserValue.newBio = bio
        runCoroutine {
            val manipulationResult = performUserManipulationUseCase.execute(editableUserValue)
            if (!manipulationResult) {
                notifyError(R.string.error_cannot_save_user)
            } else {
                notifySuccessAndGoToUsersList()
            }
        }
    }

    private fun getEditableUser(): EditableUser {
        val savedEditableUser: EditableUser? = savedStateHandle.get(EDITABLE_USER_ARGUMENT_KEY)
        return savedEditableUser ?: createNewEditableUser()
    }

    private fun createNewEditableUser(): EditableUser {
        val selectedUser = getSelectedUserOrNull(savedStateHandle)
        return if (selectedUser == null) {
            NewUser()
        } else {
            EditableUser(selectedUser)
        }.also {
            // if we reach this point, it means we haven't saved
            // an editable user in our saved state handle object yet
            // so save it to have it in future occasions
            savedStateHandle.set(EDITABLE_USER_ARGUMENT_KEY, it)
        }
    }

    private fun getSelectedUserOrNull(savedStateHandle: SavedStateHandle): UserUiModel? {
        return savedStateHandle.get(USER_ARGUMENT_KEY)
    }

    private fun notifyError(@StringRes errorMessageRes: Int) {
        _userManipulationError.value = UiMessageModel(errorMessageRes)
    }

    private fun notifySuccessAndGoToUsersList() {
        _userCreatedSuccessfully.value = UiMessageModel(R.string.user_manipulation_fragment_success)
    }

    private fun getEditionModeState(): Boolean {
        val savedFormEnableState = savedStateHandle.get<Boolean>(EDITION_ENABLE_STATE)
        // I know that I can use elvis operator here, but for some reason it's not working right
        return if (savedFormEnableState == null) {
            editableUser.value is NewUser
        } else {
            savedFormEnableState
        }
    }
}