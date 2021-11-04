package com.geermank.userslist.presentation.users.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.geermank.userslist.domain.usecase.GetAllUsersUseCase
import com.geermank.userslist.presentation.BaseViewModel
import com.geermank.userslist.presentation.users.model.UserUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UsersListViewModel @Inject constructor(
    getAllUsersUseCase: GetAllUsersUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) : BaseViewModel() {

    val users: LiveData<List<UserUiModel>> = liveData {
        emit(getAllUsersUseCase.execute(Unit))
    }

    fun delete(item: UserUiModel) {
        runCoroutine {
            deleteUserUseCase.execute(item)
        }
    }
}
