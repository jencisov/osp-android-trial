package com.jencisov.osp_android_trial.domain

import android.os.Handler
import com.jencisov.osp_android_trial.data.service.ApiFactory
import com.jencisov.osp_android_trial.data.service.LoginRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

class LoginInteractor(private val handler: Handler) {

    interface OnLoginFinishedListener {
        fun onUserNameError()
        fun onPasswordError()
        fun onError()
        fun onSuccess()
    }

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)
    private val repository = LoginRepository(ApiFactory.api)

    fun login(userName: String, password: String, listener: OnLoginFinishedListener) {
        when {
            userName.isEmpty() -> listener.onUserNameError()
            password.isEmpty() -> listener.onPasswordError()
            else -> {
                scope.launch {
                    val user = repository.login()
                    handler.post {
                        user?.let { listener.onSuccess() } ?: listener.onError()
                    }
                }
            }
        }
    }

    fun cancel() {
        coroutineContext.cancel()
    }

}