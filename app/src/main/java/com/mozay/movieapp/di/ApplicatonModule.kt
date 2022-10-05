package com.mozay.movieapp.di

import com.mozay.movieapp.common.builder.DefaultRequestInterceptor
import com.mozay.movieapp.data.remote.TheMovieDatabaseAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    @Named(NAME_URL)
    fun provideBaseUrl(): String = TheMovieDatabaseAPI.BASE_API_URL


    @Provides
    @Singleton
    fun provideOkHttpClient(
        requestInterceptor: DefaultRequestInterceptor
    ): OkHttpClient =
        with(OkHttpClient.Builder()) {
            addInterceptor(requestInterceptor)
            connectTimeout(TIMEOUT_MILIS, TimeUnit.MILLISECONDS)
            build()
        }

    @Provides
    @Singleton
    fun provideRequestInterceptor(): Interceptor =
        DefaultRequestInterceptor()

    @Provides
    @Singleton
    fun provideRetrofit(@Named(NAME_URL) baseUrl: String, client: OkHttpClient): Retrofit =
        with(Retrofit.Builder()) {
            baseUrl(baseUrl)
            client(client)
            addConverterFactory(GsonConverterFactory.create())
            build()
        }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): TheMovieDatabaseAPI.MovieService = retrofit.create(
        TheMovieDatabaseAPI.MovieService::class.java)


    companion object {
        private const val NAME_URL = "url"
        private const val TIMEOUT_MILIS = 20000L
    }
}