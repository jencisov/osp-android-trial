package com.jencisov.osp_android_trial.ui.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jencisov.osp_android_trial.domain.LoginInteractor

class LoginViewModelFactory(private val loginInteractor: LoginInteractor) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(loginInteractor) as T
    }

}