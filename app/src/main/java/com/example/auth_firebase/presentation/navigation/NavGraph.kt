package com.example.auth_firebase.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.auth_firebase.ContainerAppState
import com.example.auth_firebase.presentation.navigation.AppDestination.SignInDestination
import com.example.auth_firebase.presentation.screens.auth.newPassword.NewPasswordScreen
import com.example.auth_firebase.presentation.screens.auth.otpCode.OtpCodeScreen
import com.example.auth_firebase.presentation.screens.home.HomeScreen
import com.example.auth_firebase.presentation.screens.auth.resetPassword.ForgetPasswordScreen
import com.example.auth_firebase.presentation.screens.auth.resetPassword.ForgetPasswordViewModel
import com.example.auth_firebase.presentation.screens.auth.signin.SignInScreen
import com.example.auth_firebase.presentation.screens.auth.signup.SignUpScreen

@Composable
fun NavGraph(
    appState: ContainerAppState,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = appState.navController,
        startDestination = SignInDestination.route,
        modifier = modifier
    ) {
        composable(route = SignInDestination.route) {
            SignInScreen(
                onSignInClick = {
                    appState.navigateSingleTopToAndPopupTo(
                        route = AppDestination.HomeDestination.route,
                        popUpToRoute = AppDestination.HomeDestination.route
                    )
                },
                onSignInClickGuest = {
                    appState.navigateSingleTopToAndPopupTo(
                        route = AppDestination.HomeDestination.route,
                        popUpToRoute = AppDestination.HomeDestination.route
                    )
                },
                onSignUpClickNav = {
                    appState.navigateSingleTopToAndPopupTo(
                        route = AppDestination.SignUpDestination.route,
                        popUpToRoute = AppDestination.SignUpDestination.route
                    )
                },
                onForgotPasswordClickNav = {
                    appState.navigateSingleTopToAndPopupTo(
                        route = AppDestination.ForgotPasswordDestination.route,
                        popUpToRoute = AppDestination.ForgotPasswordDestination.route
                    )
                },
                onAppStart = {
                    appState.navigateSingleTopToAndPopupTo(
                        route = AppDestination.HomeDestination.route,
                        popUpToRoute = AppDestination.HomeDestination.route
                    )
                }

            )
        }
        composable(route = AppDestination.SignUpDestination.route) {
            SignUpScreen(
                onSignUpClick = {
                    appState.navigateSingleTopToAndPopupTo(
                        route = AppDestination.HomeDestination.route,
                        popUpToRoute = AppDestination.HomeDestination.route
                    )
                },
                onSignInClickNav = {
                    appState.navigateSingleTopToAndPopupTo(
                        route = SignInDestination.route,
                        popUpToRoute = SignInDestination.route
                    )
                },
            )
        }
        composable(route = AppDestination.ForgotPasswordDestination.route) {
            val viewModel: ForgetPasswordViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            ForgetPasswordScreen(
                onSendClick = {
                    appState.navigateSingleTopToAndPopupTo(
                        route = "${AppDestination.OtpCodeDestination.route}/${uiState.email}",
                        popUpToRoute = AppDestination.OtpCodeDestination.route
                    )
                },
                onBackClick = {
                    appState.navigateSingleTopToAndPopupTo(
                        route = SignInDestination.route,
                        popUpToRoute = SignInDestination.route
                    )
                },
            )
        }
        composable(
            route = "${AppDestination.OtpCodeDestination.route}/{email}",
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            OtpCodeScreen(
                email = email,
                onVerifyOtpCodeClick = { /* Verify OTP code logic */
                },
                onResendOtpCodeClick = { /* Resend OTP code logic */ },
                onBackClick = {
                    appState.navigateSingleTopToAndPopupTo(
                        route = AppDestination.ForgotPasswordDestination.route,
                        popUpToRoute = AppDestination.ForgotPasswordDestination.route
                    )
                },
                onConfirmDialogClick = {
                    appState.navigateSingleTopToAndPopupTo(
                        route = AppDestination.NewPasswordDestination.route,
                        popUpToRoute = AppDestination.NewPasswordDestination.route
                    )
                }
            )
        }
        composable(route = AppDestination.NewPasswordDestination.route) {
            NewPasswordScreen(
                onSavaClick = {
                    appState.navigateSingleTopToAndPopupTo(
                        route = SignInDestination.route,
                        popUpToRoute = SignInDestination.route
                    )
                },
                onBackClick = {
                    appState.navigateSingleTopToAndPopupTo(
                        route = SignInDestination.route,
                        popUpToRoute = SignInDestination.route
                    )
                }
            )
        }
        composable(route = AppDestination.HomeDestination.route) {
            HomeScreen(
                onSignOutClick = {
                    appState.navigateSingleTopToAndPopupTo(
                        route = SignInDestination.route,
                        popUpToRoute = SignInDestination.route
                    )
                },
                onDeleteMyAccountClick = {
                    appState.navigateSingleTopToAndPopupTo(
                        route = SignInDestination.route,
                        popUpToRoute = SignInDestination.route
                    )
                },
                onChangePassClickNav = {
                    appState.navigateSingleTopToAndPopupTo(
                        route = AppDestination.NewPasswordDestination.route,
                        popUpToRoute = AppDestination.NewPasswordDestination.route
                    )
                }
            )
        }

    }

}