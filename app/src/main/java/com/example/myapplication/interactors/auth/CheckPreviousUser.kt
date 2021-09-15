package com.example.myapplication.interactors.auth

import com.example.myapplication.database.User
import com.example.myapplication.database.UserDao
import com.example.myapplication.datastore.EmailDataStore
import com.example.myapplication.presentation.ui.auth.state.AuthViewState
import com.example.myapplication.utils.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

/**
 * Checks if user is logged in during app startUp.
 *  - Check [EmailDataStore] for currently logged in user email
 *  - If No email found, user is navigated to login screen
 *  - If email is found, [User] Database is scanned for that email.
 *  - If the user doesn't exist, email is removed from [EmailDataStore] and user is navigated to login screen
 */
class CheckPreviousUser(
    private val userDao: UserDao,
    private val emailDataStore: EmailDataStore
) {

    fun execute(
        stateEvent: StateEvent
    ): Flow<DataState<AuthViewState>?> = flow {

        val email = emailDataStore.preferenceFlow.first()

        if (email.isBlank()) {
            emit(userNotFound(stateEvent))
        } else {

            val cacheResult = safeCacheCall(IO) {
                userDao.searchByEmail(email)
            }

            emit(
                object : CacheResponseHandler<AuthViewState, User?>(
                    response = cacheResult,
                    stateEvent = stateEvent
                ) {
                    override suspend fun handleSuccess(resultObj: User?): DataState<AuthViewState> {
                        if (resultObj != null) {
                            return DataState.data(
                                data = AuthViewState(),
                                response = Response(
                                    message = USER_FOUND,
                                    uiType = UiType.None,
                                    messageType = MessageType.Success
                                ),
                                stateEvent = stateEvent
                            )
                        }
                        return userNotFound(stateEvent)
                    }
                }.getResult()
            )
        }
    }

    private fun userNotFound(
        stateEvent: StateEvent
    ): DataState<AuthViewState> {
        return DataState.data(
            data = AuthViewState(previousUserCheck = true),
            response = Response(
                USER_NOT_FOUND,
                UiType.None,
                MessageType.Error
            ),
            stateEvent = stateEvent
        )
    }

    companion object {
        const val USER_FOUND = "Previously authenticated user found"
        const val USER_NOT_FOUND = "No previously authenticated user found"
    }
}