package com.example.auth_firebase.presentation.navigation

interface AppDestination {
    val route: String

    object SignInDestination : AppDestination {
        override val route = "SignIn"
    }

    object SignUpDestination : AppDestination {
        override val route = "SignUp"
    }

    object ForgotPasswordDestination : AppDestination {
        override val route = "ForgotPassword"
    }

    object OtpCodeDestination : AppDestination {
        override val route = "OtpCode"
    }

    object NewPasswordDestination : AppDestination {
        override val route = "NewPassword"
    }

    object HomeDestination : AppDestination {
        override val route = "Home"
    }
}