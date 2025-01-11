package com.example.remotelogin

import com.example.remotelogin.wrappers.Credentials
import com.example.remotelogin.wrappers.RequestResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

abstract class WebApi {
    protected val BASE_URL: String = "http://192.168.1.19:8000"

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        prettyPrint = true
        encodeDefaults = true
    }

    protected val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(json)
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 2500
        }
    }

    fun clear(){
        client.close()
    }
}

class AuthApi : WebApi() {
    suspend fun sendLogin(credentials: Credentials): RequestResult.Authentication {

        when (credentials) {
            is Credentials.Empty -> return RequestResult.Authentication.Denied("Заполните поля")
            is Credentials.Valid -> {
                val credentials: Credentials = credentials

                try {
                    val result = client.post("$BASE_URL/user/login") {
                        contentType(ContentType.Application.Json)
                        setBody(credentials)
                    }

                    val data: RequestResult.Authentication = result.body()
                    return data

                } catch (ex: Exception) {
                    return RequestResult.Authentication.Denied("Сетевая ошибка. ${ex.message}")
                }

            }
        }
    }

    suspend fun createAccount(credentials: Credentials): RequestResult {
        when (credentials) {
            is Credentials.Empty -> return RequestResult.Authentication.Denied("Заполните поля")
            is Credentials.Valid -> {
                val credentials: Credentials = credentials

                try {
                    client.use {
                        val result = it.post("$BASE_URL/user/register") {
                            contentType(ContentType.Application.Json)
                            setBody(credentials)
                        }

                        val data: RequestResult.CreateAccount = result.body()
                        return data
                    }
                } catch (ex: Exception) {
                    return RequestResult.CreateAccount.Error("Сетевая ошибка. ${ex.message}")
                }

            }
        }
    }
}