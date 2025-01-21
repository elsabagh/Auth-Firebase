package com.example.auth_firebase.domain.repository

import com.example.auth_firebase.data.model.User
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    val currentUser: Flow<User>

    val currentUserId: String

    val isUserSignedIn: Boolean

    suspend fun authenticate(email: String, password: String)

    suspend fun createAnonymousAccount()

    suspend fun createAccount(email: String, password: String)

    suspend fun linkAccount(email: String, password: String)

    suspend fun deleteAccount()

    suspend fun signOut()
}