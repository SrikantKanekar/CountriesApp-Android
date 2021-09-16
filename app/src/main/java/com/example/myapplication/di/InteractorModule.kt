package com.example.myapplication.di

import com.example.myapplication.database.UserDao
import com.example.myapplication.datastore.EmailDataStore
import com.example.myapplication.interactors.auth.AttemptLogin
import com.example.myapplication.interactors.auth.AttemptRegistration
import com.example.myapplication.interactors.auth.AuthInteractors
import com.example.myapplication.interactors.auth.CheckPreviousUser
import com.example.myapplication.interactors.main.DeleteUser
import com.example.myapplication.interactors.main.GetCountries
import com.example.myapplication.interactors.main.MainInteractors
import com.example.myapplication.network.CountriesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object InteractorModule {

    @ViewModelScoped
    @Provides
    fun provideAuthInteractors(
        userDao: UserDao,
        emailDataStore: EmailDataStore
    ): AuthInteractors {
        return AuthInteractors(
            AttemptLogin(userDao, emailDataStore),
            AttemptRegistration(userDao, emailDataStore),
            CheckPreviousUser(userDao, emailDataStore)
        )
    }

    @ViewModelScoped
    @Provides
    fun provideHomeInteractors(
        countriesApi: CountriesApi,
        userDao: UserDao,
        emailDataStore: EmailDataStore
    ): MainInteractors {
        return MainInteractors(
            GetCountries(countriesApi),
            DeleteUser(userDao, emailDataStore)
        )
    }
}