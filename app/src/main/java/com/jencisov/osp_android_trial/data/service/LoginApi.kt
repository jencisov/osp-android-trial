package com.jencisov.osp_android_trial.data.service

import com.jencisov.osp_android_trial.data.model.User
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface LoginApi {

    @GET("osp/login/user")
    fun login(): Deferred<Response<List<User>>>

}