package com.twins.testapp.data.repository

import com.twins.testapp.data.remote.api.TestService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(
        service: TestService,
    ): MovieRepository {
        return MovieRepository(service)
    }
}