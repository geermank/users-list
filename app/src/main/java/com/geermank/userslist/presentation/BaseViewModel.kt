package com.geermank.userslist.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onCoroutineError(throwable)
    }

    protected open fun onCoroutineError(ex: Throwable) {
        // override accordingly
        Log.e("GermancitoApp", ex.printStackTrace().toString())
    }

    protected fun runCoroutine(task: suspend () -> Unit) {
        viewModelScope.launch(coroutineExceptionHandler) { task() }
    }
}
