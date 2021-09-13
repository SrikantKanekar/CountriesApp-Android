package com.example.myapplication.di

import com.example.myapplication.database.dao.TokenDao
import com.example.myapplication.database.dao.UserDao
import com.example.myapplication.datastore.EmailDataStore
import com.example.myapplication.interactors.auth.AttemptLogin
import com.example.myapplication.interactors.auth.AttemptRegistration
import com.example.myapplication.interactors.auth.AuthInteractors
import com.example.myapplication.interactors.auth.CheckPreviousUser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
object InteractorModule {

    @Provides
    fun provideAuthInteractors(
        authTokenDao: TokenDao,
        accountPropertiesDao: UserDao,
        emailDataStore: EmailDataStore
    ): AuthInteractors {
        return AuthInteractors(
            AttemptLogin(authTokenDao, accountPropertiesDao, emailDataStore),
            AttemptRegistration(authTokenDao, accountPropertiesDao, emailDataStore),
            CheckPreviousUser(authTokenDao, accountPropertiesDao, emailDataStore)
        )
    }


//    @Provides
//    fun provideHomeInteractors(
//        app: BaseApplication
//    ): HomeInteractors {
//        return HomeInteractors(
//            ConnectToFaircon(app),
//            DisconnectFromFaircon(app)
//        )
//    }
}