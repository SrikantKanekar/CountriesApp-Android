package com.example.myapplication.interactors.main

import com.example.myapplication.database.dao.TokenDao
import com.example.myapplication.database.dao.UserDao
import com.example.myapplication.database.entity.Token
import com.example.myapplication.datastore.EmailDataStore
import com.example.myapplication.interactors.auth.AttemptLogin.Companion.ERROR_SAVE_AUTH_TOKEN
import com.example.myapplication.interactors.auth.AttemptLogin.Companion.GENERIC_AUTH_ERROR
import com.example.myapplication.interactors.auth.AttemptLogin.Companion.INVALID_CREDENTIALS
import com.example.myapplication.presentation.ui.auth.state.AuthViewState
import com.example.myapplication.presentation.ui.main.state.MainViewState
import com.example.myapplication.utils.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCountries(

) {

    fun execute(
        stateEvent: StateEvent,
    ): Flow<DataState<MainViewState>?> = flow {

    }
}