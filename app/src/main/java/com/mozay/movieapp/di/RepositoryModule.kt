package com.mozay.movieapp.di

import com.mozay.movieapp.data.remote.TheMovieDatabaseAPI
import com.mozay.movieapp.data.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideMovieRepository(apiService: TheMovieDatabaseAPI.MovieService) : MovieRepository = MovieRepository(apiService)
}