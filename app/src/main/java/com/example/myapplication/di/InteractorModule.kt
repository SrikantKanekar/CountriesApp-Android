package com.example.myapplication.di

import com.example.myapplication.database.dao.TokenDao
import com.example.myapplication.database.dao.UserDao
import com.example.myapplication.datastore.EmailDataStore
import com.example.myapplication.interactors.auth.AttemptLogin
import com.example.myapplication.interactors.auth.AttemptRegistration
import com.example.myapplication.interactors.auth.AuthInteractors
import com.example.myapplication.interactors.auth.CheckPreviousUser
import com.example.myapplication.interactors.main.GetCountries
import com.example.myapplication.interactors.main.MainInteractors
import com.example.myapplication.network.CountriesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object InteractorModule {

    @Provides
    fun provideAuthInteractors(
        tokenDao: TokenDao,
        userDao: UserDao,
        emailDataStore: EmailDataStore
    ): AuthInteractors {
        return AuthInteractors(
            AttemptLogin(userDao, emailDataStore),
            AttemptRegistration(tokenDao, userDao, emailDataStore),
            CheckPreviousUser(tokenDao, userDao, emailDataStore)
        )
    }


    @Provides
    fun provideHomeInteractors(
        countriesApi: CountriesApi
    ): MainInteractors {
        return MainInteractors(
            GetCountries(countriesApi)
        )
    }
}