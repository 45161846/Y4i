package com.example.remotelogin

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remotelogin.util.checkCorrectCredentials
import com.example.remotelogin.values.strings.CREDENTIALS_SAVED
import com.example.remotelogin.values.strings.EMAIL_KEY
import com.example.remotelogin.values.strings.LOGIN_KEY
import com.example.remotelogin.values.strings.PASSWORD_KEY
import com.example.remotelogin.wrappers.Credentials
import com.example.remotelogin.wrappers.RequestResult
import com.example.remotelogin.wrappers.states.AuthEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private lateinit var sharedPreferences: SharedPreferences
    private val RemoteLoginRepository = RemoteLoginRepository()

    private var authJob: Job = Job()

    private val _event: MutableStateFlow<AuthEvent> = MutableStateFlow(AuthEvent.Shimmer)
    val event: StateFlow<AuthEvent> = _event

    fun checkLocalCredentials(sharedPreferences: SharedPreferences) {

        if(::sharedPreferences.isInitialized){
            return
        }

        this.sharedPreferences = sharedPreferences

        val credentialsSaved = sharedPreferences.getBoolean(CREDENTIALS_SAVED, false)
        if (!credentialsSaved) {
            login(Credentials.Empty)
        }

        val login = sharedPreferences.getString(LOGIN_KEY, "") ?: ""
        val email = sharedPreferences.getString(EMAIL_KEY, "") ?: ""
        val password = sharedPreferences.getString(PASSWORD_KEY, "") ?: ""

        val savedCredentials = Credentials.Valid(login, email, password)

        login(savedCredentials)
    }


    private fun onReceivedResponse(
        sentCredentials: Credentials,
        response: RequestResult
    ) {
        when (response) {

            is RequestResult.Authentication -> {
                _event.value = when (response) {
                    is RequestResult.Authentication.Granted -> {
                        authJob.cancel()
                        saveCredentials(sentCredentials)
                        AuthEvent.LoginSuccess
                    }

                    is RequestResult.Authentication.Denied -> AuthEvent.LoginDeny(response.message)
                }
            }

            is RequestResult.CreateAccount -> {
                _event.value = when (response) {
                    is RequestResult.CreateAccount.Created -> {
                        authJob.cancel()
                        saveCredentials(sentCredentials)
                        AuthEvent.CreateSuccess
                    }

                    is RequestResult.CreateAccount.Error -> AuthEvent.CreateDeny(response.errorMessage)
                }
            }

            else -> TODO()
        }
    }

    fun login(credentials: Credentials) {
//        authJob.cancel()

        _event.value.let {
            if(it !is AuthEvent.Shimmer){
                _event.value = AuthEvent.LoginLoading
            }
        }

//        authJob =
            viewModelScope.launch {
            delay(1500L)
            val error = checkCorrectCredentials(credentials)

            if (error == null) {
                val response = RemoteLoginRepository.loginWith(credentials)
                onReceivedResponse(credentials, response)
            } else {
                onReceivedResponse(
                    credentials,
                    RequestResult.Authentication.Denied(error)
                )
            }
        }
//        authJob.start()
    }

    fun createNewAccount(credentials: Credentials) {
        authJob.cancel()

        _event.value = AuthEvent.CreateLoading
        authJob = viewModelScope.launch {
            delay(1500L)
            val error = checkCorrectCredentials(credentials)

            if (error == null) {
                val response = RemoteLoginRepository.createAccount(credentials)
                onReceivedResponse(credentials, response)
            } else {
                onReceivedResponse(
                    credentials,
                    RequestResult.CreateAccount.Error(error)
                )
            }
        }
        authJob.start()
    }

    fun noAccountContinue() {
        _event.value = AuthEvent.LoginSuccess
    }

    private fun saveCredentials(credentials: Credentials) {
        when (credentials) {
            is Credentials.Empty -> return
            is Credentials.Valid -> {
                if (::sharedPreferences.isInitialized) {
                    sharedPreferences.edit {
                        putString(LOGIN_KEY, credentials.login)
                        putString(EMAIL_KEY, credentials.email)
                        putString(PASSWORD_KEY, credentials.password)
                        putBoolean(CREDENTIALS_SAVED, true)
                        apply()
                    }
                }
            }
        }
    }

    override fun onCleared() {
        RemoteLoginRepository.clear()
        super.onCleared()
    }
}