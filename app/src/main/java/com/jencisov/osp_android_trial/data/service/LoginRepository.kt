package com.jencisov.osp_android_trial.data.service

import android.util.Log
import com.jencisov.osp_android_trial.data.model.User

class LoginRepository(private val api: LoginApi) {

    suspend fun login(): User? {
        var user: User? = null
        val loggedUser = api.login()

        try {
            val response = loggedUser.await()

            if (response.isSuccessful) {
                response.body()?.let { userList ->
                    user = userList[0]
                }
            } else {
                Log.d("Login", response.errorBody().toString())
            }
        } catch (e: Exception) {
        }

        return user
    }

}