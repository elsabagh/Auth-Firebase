package com.example.auth_firebase.data.network.repository

import androidx.compose.ui.util.trace
import com.example.auth_firebase.data.model.User
import com.example.auth_firebase.domain.repository.AccountRepository
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AccountRepository {
    override val currentUser: Flow<User>
        get() = callbackFlow {
            val listener = FirebaseAuth.AuthStateListener { auth ->
                this.trySend(
                    auth.currentUser?.let {
                        User(
                            userId = it.uid,
                            isAnonymous = it.isAnonymous
                        )
                    } ?: User()
                )
            }
            firebaseAuth.addAuthStateListener(listener)
            awaitClose {
                firebaseAuth.removeAuthStateListener(listener)
            }
        }

    override val currentUserId: String
        get() = firebaseAuth.currentUser?.uid.orEmpty()

    override val isUserSignedIn: Boolean
        get() = firebaseAuth.currentUser != null

    override suspend fun authenticate(email: String, password: String) {
        try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
        } catch (e: Exception) {
            throw Exception("Authentication failed: ${e.message}", e)
        }catch (e: IOException) {
            throw IOException("Network error occurred during account creation: ${e.message}", e)
        }
    }

    override suspend fun createAnonymousAccount() {
        try {
            firebaseAuth.signInAnonymously().await()
        } catch (e: Exception) {
            throw Exception("Failed to create anonymous account: ${e.message}", e)
        } catch (e: IOException) {
            throw IOException("Network error occurred during account creation: ${e.message}", e)
        }
    }

    override suspend fun createAccount(email: String, password: String) {
        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        } catch (e: Exception) {
            throw Exception("Failed to create account: ${e.message}", e)
        } catch (e: IOException) {
            throw IOException("Network error occurred during account creation: ${e.message}", e)
        }
    }

    override suspend fun linkAccount(email: String, password: String) {
        try {
            trace(LINK_ACCOUNT_TRACE) {
                val credential = EmailAuthProvider.getCredential(email, password)
                firebaseAuth.currentUser!!.linkWithCredential(credential).await()
            }
        } catch (e: Exception) {
            throw Exception("Failed to link account: ${e.message}", e)
        } catch (e: IOException) {
            throw IOException("Network error occurred during account linking: ${e.message}", e)
        }
    }

    override suspend fun deleteAccount() {
        try {
            firebaseAuth.currentUser!!.delete().await()
        } catch (e: Exception) {
            throw Exception("Failed to delete account: ${e.message}", e)
        } catch (e: IOException) {
            throw IOException("Network error occurred during account deletion: ${e.message}", e)
        }
    }

    override suspend fun signOut() {
        try {
            if (firebaseAuth.currentUser!!.isAnonymous) {
                firebaseAuth.currentUser!!.delete()
            }
            firebaseAuth.signOut()
        } catch (e: Exception) {
            throw Exception("Failed to sign out: ${e.message}", e)
        } catch (e: IOException) {
            throw IOException("Network error occurred during sign-out: ${e.message}", e)
        }
    }


    override suspend fun changePassword(newPassword: String) {
        val user = firebaseAuth.currentUser
        if (user != null) {
            try {
                user.updatePassword(newPassword).await()
            } catch (e: Exception) {
                throw Exception("Failed to change password: ${e.message}", e)
            } catch (e: IOException) {
                throw IOException("Network error occurred during password change: ${e.message}", e)
            }
        } else {
            throw Exception("No authenticated user found")
        }
    }

    override suspend fun sendPasswordResetEmail(email: String) {
        try {
            firebaseAuth.sendPasswordResetEmail(email).await()
        } catch (e: Exception) {
            throw Exception("Failed to send password reset email: ${e.message}", e)
        } catch (e: IOException) {
            throw IOException("Network error occurred during password reset: ${e.message}", e)
        }
    }

    override suspend fun verifyPasswordResetCode(code: String): String {
        return try {
            firebaseAuth.verifyPasswordResetCode(code).await()
        } catch (e: Exception) {
            throw Exception("Failed to verify password reset code: ${e.message}", e)
        } catch (e: IOException) {
            throw IOException("Network error occurred during password reset verification: ${e.message}", e)
        }
    }

    companion object {
        const val LINK_ACCOUNT_TRACE = "linkAccount"

    }

}