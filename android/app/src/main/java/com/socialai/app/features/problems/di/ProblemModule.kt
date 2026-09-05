package com.socialai.app.features.problems.di
import com.socialai.app.features.problems.data.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
@Module @InstallIn(SingletonComponent::class)
abstract class ProblemModule { @Binds abstract fun bindRepo(impl: ProblemRepositoryImpl): ProblemRepository }
