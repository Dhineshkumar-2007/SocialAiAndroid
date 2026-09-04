package com.socialai.app.features.auth.di
import com.socialai.app.features.auth.data.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {
    @Binds abstract fun bindAuthRepo(impl: AuthRepositoryImpl): AuthRepository
}
