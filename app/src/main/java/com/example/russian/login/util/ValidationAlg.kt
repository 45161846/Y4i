package com.example.remotelogin.util

import com.example.remotelogin.wrappers.Credentials

fun checkCorrectCredentials(credentials: Credentials): String? {
    when (credentials) {
        is Credentials.Empty -> return "Заполните необходимые поля"
        is Credentials.Valid -> {
            val isLogin = isValidLogin(credentials.login)
            val isEmail = isValidEmail(credentials.email)
            val isPassword = isValidPassword(credentials.password)

            if (credentials.email.isEmpty() && credentials.login.isEmpty() && credentials.password.isEmpty()) {
                return ""
            }

            return if (isPassword && (isLogin || isEmail)) {
                null
            } else if ((isLogin || isEmail).not()) {
                "Некорректный логин"
            } else if (isPassword.not()) {
                "Некорректный пароль"
            } else {
                "Некорректные данные"
            }
        }
    }
}

fun isValidLogin(login: String): Boolean {
    val reg = Regex("[a-zA-Z0-9_-]{4,16}")

    return reg.matches(login)
}

fun isValidEmail(email: String): Boolean {
    val reg = Regex("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")

    return reg.matches(email)
}

fun isValidPassword(password: String): Boolean {

    val reg = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{8,16}$")

    return reg.matches(password)
}