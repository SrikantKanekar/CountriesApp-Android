package com.example.myapplication.interactors.auth

import com.example.myapplication.database.dao.TokenDao
import com.example.myapplication.database.dao.UserDao
import com.example.myapplication.database.entity.User
import com.example.myapplication.datastore.EmailDataStore
import com.example.myapplication.presentation.ui.auth.state.AuthViewState
import com.example.myapplication.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

/**
 * Checks if user is logged in during app startUp.
 *
 *  - Check [EmailDataStore] for currently logged in user email
 *  - If No email found, user is navigated to login screen
 *  - If email is found, [AccountProperties] Database is scanned for that email.
 *  - If the user exists, then AuthToken Database is scanned corresponding to primary key and authToken is returned
 *  - If the user doesn't exist, user is navigated to login screen
 */
class CheckPreviousUser(
    private val tokenDao: TokenDao,
    private val userDao: UserDao,
    private val emailDataStore: EmailDataStore
) {

    fun execute(
        stateEvent: StateEvent
    ): Flow<DataState<AuthViewState>?> = flow {

        val email = emailDataStore.preferenceFlow.first()

        if (email.isNullOrBlank()) {
            emit(userNotFound(stateEvent))
        } else {

            val cacheResult = safeCacheCall(Dispatchers.IO) {
                userDao.searchByEmail(email)
            }

            emit(
                object : CacheResponseHandler<AuthViewState, User>(
                    response = cacheResult,
                    stateEvent = stateEvent
                ) {
                    override suspend fun handleSuccess(resultObj: User): DataState<AuthViewState> {
                        if (resultObj.pk > -1) {
                            tokenDao.searchByPk(resultObj.pk)?.let { token ->
                                if (token.token != null) {
                                    return DataState.data(
                                        data = AuthViewState(token = token),
                                        response = Response(
                                            message = USER_FOUND,
                                            uiType = UiType.None,
                                            messageType = MessageType.Success
                                        ),
                                        stateEvent = stateEvent
                                    )
                                }
                            }
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
        const val USER_FOUND = "previously authenticated user found"
        const val USER_NOT_FOUND = "No previously authenticated user found"
    }
}