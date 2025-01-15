package com.example.remotelogin.draw

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.remotelogin.LoginViewModel
import com.example.remotelogin.wrappers.placeholder.AuthProcessPlaceholder
import com.example.remotelogin.wrappers.states.AuthDestination
import com.example.remotelogin.wrappers.states.AuthEvent
import com.example.russian.login.draw.Login

@Composable
fun AuthNavigation(
    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel(),
    enterMainApplication: () -> Unit
) {

    val loginProcessPlaceholder: MutableState<AuthProcessPlaceholder> = remember {
        mutableStateOf(AuthProcessPlaceholder.None)
    }

    val accountProcessPlaceholder: MutableState<AuthProcessPlaceholder> = remember {
        mutableStateOf(AuthProcessPlaceholder.None)
    }

    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when (it) {
                is AuthEvent.LoginLoading -> loginProcessPlaceholder.value =
                    AuthProcessPlaceholder.Loading

                is AuthEvent.CreateLoading -> accountProcessPlaceholder.value =
                    AuthProcessPlaceholder.Loading

                is AuthEvent.LoginDeny -> {
                    loginProcessPlaceholder.value = AuthProcessPlaceholder.Error(it.error)

                }

                is AuthEvent.CreateDeny -> accountProcessPlaceholder.value =
                    AuthProcessPlaceholder.Error(it.error)

                is AuthEvent.CreateSuccess -> {
                    loginProcessPlaceholder.value = AuthProcessPlaceholder.None
                    accountProcessPlaceholder.value = AuthProcessPlaceholder.None
                    enterMainApplication()
                }

                is AuthEvent.LoginSuccess -> {
                    loginProcessPlaceholder.value = AuthProcessPlaceholder.None
                    accountProcessPlaceholder.value = AuthProcessPlaceholder.None
                    enterMainApplication()
                }

                is AuthEvent.Shimmer -> {
                    loginProcessPlaceholder.value = AuthProcessPlaceholder.None
                    accountProcessPlaceholder.value = AuthProcessPlaceholder.None
                }
            }
            if (it is AuthEvent.Shimmer) {
                navController.navigate(AuthDestination.Loading)
            } else {
                navController.popBackStack(AuthDestination.Loading, inclusive = true)
            }
        }
    }

    val animationTime = 350
    NavHost(
        navController = navController,
        startDestination = AuthDestination.Login,
    ) {
        composable<AuthDestination.Loading>(
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                fadeOut(
                    tween(300),
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        MaterialTheme.colorScheme.surface
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Loading()
            }
        }

        composable<AuthDestination.ForgetPassword> {
            ForgetPassword{}
        }

        composable<AuthDestination.Login>(
            enterTransition = {
                fadeIn(tween(animationTime, animationTime))
            },
            exitTransition = {
                fadeOut(tween(animationTime))
            }
        ) {
            accountProcessPlaceholder.value = AuthProcessPlaceholder.None

            Login(
                processPlaceholder = loginProcessPlaceholder,
                onForgetPassword = {
                    navController.navigate(AuthDestination.ForgetPassword)
                },
                onGoToCreate = {
                    navController.navigate(AuthDestination.NewAccount)
                },
                onSignIn = { credentials ->
                    viewModel.login(credentials)
                },
                onNoAccountContinue = {
                    viewModel.noAccountContinue()
                }
            )


        }
        composable<AuthDestination.NewAccount>(
            enterTransition = {
                fadeIn(tween(animationTime, animationTime))
            },
            exitTransition = {
                fadeOut(tween(animationTime))
            }
        ) {

            loginProcessPlaceholder.value = AuthProcessPlaceholder.None

            NewAccount(
                accountProcessPlaceholder,
                onCreate = {
                    viewModel.createNewAccount(it)
                }
            )
        }
    }
}


@Composable
private fun AuthScreen(destination: AuthDestination) {
    val placeholder: MutableState<AuthProcessPlaceholder> = remember {
        mutableStateOf(AuthProcessPlaceholder.None)
    }
    when (destination) {
        is AuthDestination.Loading -> Loading()
        is AuthDestination.Login -> Login(placeholder, "", {}, {}, {}, {})
        is AuthDestination.NewAccount -> NewAccount(placeholder) {}
        is AuthDestination.ForgetPassword -> ForgetPassword{}
    }
}


@Preview
@Composable
private fun Preview() {
    AuthScreen(AuthDestination.Login)
}