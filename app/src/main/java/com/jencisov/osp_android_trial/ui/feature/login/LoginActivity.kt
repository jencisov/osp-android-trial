package com.jencisov.osp_android_trial.ui.feature.login

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.jencisov.osp_android_trial.R
import com.jencisov.osp_android_trial.domain.LoginInteractor
import com.jencisov.osp_android_trial.extensions.dismissKeyboard
import com.jencisov.osp_android_trial.ui.ScreenState
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProviders.of(
            this,
            LoginViewModelFactory(LoginInteractor(Handler(Looper.getMainLooper())))
        )[LoginViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(toolbar)

        loginViewModel.loginState.observe(::getLifecycle, ::updateUI)
        login_bt.setOnClickListener { _ -> onLoginClicked() }
    }

    override fun onDestroy() {
        loginViewModel.cancelAllRequests()
        super.onDestroy()
    }

    private fun onLoginClicked() {
        error_tv.visibility = View.GONE
        dismissKeyboard(currentFocus ?: password_et)
        loginViewModel.login(username_et.text.toString().trim(), password_et.text.toString().trim())
    }

    private fun updateUI(screenState: ScreenState<LoginState>) {
        when (screenState) {
            ScreenState.Loading -> progress.visibility = View.VISIBLE
            is ScreenState.Render -> processLoginState(screenState.renderState)
        }
    }

    private fun processLoginState(renderState: LoginState) {
        progress.visibility = View.GONE
        when (renderState) {
            LoginState.Success -> {
                welcome_fl.visibility = View.VISIBLE
            }
            LoginState.Error -> {
                error_tv.visibility = View.VISIBLE
                error_tv.text = getString(R.string.login_error)
            }
            LoginState.WrongUserName -> {
                error_tv.visibility = View.VISIBLE
                error_tv.text = getString(R.string.wrong_user_name)
            }
            LoginState.WrongPassword -> {
                error_tv.visibility = View.VISIBLE
                error_tv.text = getString(R.string.wrong_password)
            }
        }
    }

}