package com.example.myapplication.interactors.main

import com.example.myapplication.database.dao.UserDao
import com.example.myapplication.datastore.EmailDataStore
import com.example.myapplication.model.Country
import com.example.myapplication.network.CountriesApi
import com.example.myapplication.presentation.ui.main.state.MainViewState
import com.example.myapplication.utils.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class DeleteUser(
    private val userDao: UserDao,
    private val emailDataStore: EmailDataStore
) {

    fun execute(
        stateEvent: StateEvent,
    ): Flow<DataState<MainViewState>?> = flow {

        val email = emailDataStore.preferenceFlow.first()
        emailDataStore.updateAuthenticatedUserEmail("")

        val cacheResult = safeCacheCall(IO) {
            userDao.deleteByEmail(email!!)
        }
        val response = object : CacheResponseHandler<MainViewState, Unit>(
            response = cacheResult,
            stateEvent = null
        ) {
            override suspend fun handleSuccess(resultObj: Unit): DataState<MainViewState> {
                return DataState.data(
                    response = Response(
                        message = "Successfully Deleted user",
                        uiType = UiType.Dialog,
                        messageType = MessageType.Success
                    ),
                    data = null,
                    stateEvent = stateEvent
                )
            }
        }.getResult()

        emit(response)
    }
}