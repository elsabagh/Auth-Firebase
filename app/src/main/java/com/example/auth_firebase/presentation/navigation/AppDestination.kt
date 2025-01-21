package com.example.auth_firebase.presentation.navigation

interface AppDestination {
    val route: String

    object SignInDestination : AppDestination {
        override val route = "SignIn"
    }

    object SignUpDestination : AppDestination {
        override val route = "SignUp"
    }

    object HomeDestination : AppDestination {
        override val route = "Home"
    }
}