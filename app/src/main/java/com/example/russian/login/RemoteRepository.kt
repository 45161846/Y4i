package com.example.remotelogin

import com.example.remotelogin.wrappers.Credentials
import com.example.remotelogin.wrappers.RequestResult

class RemoteLoginRepository: AuthRepositoryAPI() {

    override suspend fun loginWith(loginData: Credentials): RequestResult.Authentication {
        return api.sendLogin(loginData)
    }

    override suspend fun createAccount(loginData: Credentials): RequestResult {
        return api.createAccount(loginData)
    }

    override fun clear() {
        api.clear()
    }
}

abstract class AuthRepositoryAPI{

    protected val api = AuthApi()

    abstract suspend fun loginWith(loginData: Credentials): RequestResult.Authentication
    abstract suspend fun createAccount(loginData: Credentials): RequestResult
    abstract fun clear()

}