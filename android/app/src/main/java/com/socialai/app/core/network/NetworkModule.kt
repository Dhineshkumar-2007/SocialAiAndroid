package com.socialai.app.core.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.socialai.app.BuildConfig
import com.socialai.app.core.datastore.SessionManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {
    @Binds @Singleton abstract fun bindTokenProvider(sessionManager: SessionManager): TokenProvider

    companion object {
        private val json = Json { ignoreUnknownKeys = true; isLenient = true; coerceInputValues = true }

        @Provides
        @Singleton
        fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

        @Provides
        @Singleton
        fun provideOkHttpClient(
            authInterceptor: AuthInterceptor,
            loggingInterceptor: HttpLoggingInterceptor
        ): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(90, TimeUnit.SECONDS)
            .readTimeout(90, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()

        @Provides
        @Singleton
        fun provideRetrofit(okHttpClient: OkHttpClient, sessionManager: SessionManager): Retrofit {
            val savedUrl = sessionManager.getServerUrlBlocking()
            val rawBaseUrl = if (!savedUrl.isNullOrBlank()) savedUrl else BuildConfig.BASE_URL
            val formattedBaseUrl = if (!rawBaseUrl.endsWith("/")) "$rawBaseUrl/" else rawBaseUrl
            return Retrofit.Builder()
                .baseUrl(formattedBaseUrl)
                .client(okHttpClient)
                .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
                .build()
        }

        @Provides
        @Singleton
        fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
    }
}
