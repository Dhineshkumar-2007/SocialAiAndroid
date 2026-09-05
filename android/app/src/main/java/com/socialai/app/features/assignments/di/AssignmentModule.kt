package com.socialai.app.features.assignments.di
import com.socialai.app.features.assignments.data.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
@Module @InstallIn(SingletonComponent::class)
abstract class AssignmentModule { @Binds abstract fun bindRepo(impl: AssignmentRepositoryImpl): AssignmentRepository }
