package com.jencisov.osp_android_trial.ui.feature.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jencisov.osp_android_trial.domain.LoginInteractor
import com.jencisov.osp_android_trial.ui.ScreenState

class LoginViewModel(private val loginInteractor: LoginInteractor) : ViewModel(),
    LoginInteractor.OnLoginFinishedListener {

    override fun onUserNameError() {
        _loginState.value = ScreenState.Render(LoginState.WrongUserName)
    }

    override fun onPasswordError() {
        _loginState.value = ScreenState.Render(LoginState.WrongPassword)
    }

    override fun onSuccess() {
        _loginState.value = ScreenState.Render(LoginState.Success)
    }

    override fun onError() {
        _loginState.value = ScreenState.Render(LoginState.Error)
    }

    private val _loginState = MutableLiveData<ScreenState<LoginState>>()
    val loginState: LiveData<ScreenState<LoginState>>
        get() = _loginState

    fun login(userName: String, password: String) {
        _loginState.value = ScreenState.Loading
        loginInteractor.login(userName, password, this)
    }

    fun cancelAllRequests() = loginInteractor.cancel()

}