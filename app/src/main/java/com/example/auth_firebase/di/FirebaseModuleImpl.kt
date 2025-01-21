package com.example.auth_firebase.di

import com.example.auth_firebase.data.network.repository.AccountRepositoryImpl
import com.example.auth_firebase.domain.repository.AccountRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FirebaseModuleImpl {

    @Binds
    abstract fun bindAccountRepository(impl: AccountRepositoryImpl): AccountRepository

}