package com.socialai.app.features.projects.di
import com.socialai.app.features.projects.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
@Module @InstallIn(SingletonComponent::class)
abstract class ProjectModule { @Binds abstract fun bindRepo(impl: ProjectRepositoryImpl): ProjectRepository }
