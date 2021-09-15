package com.example.myapplication.interactors.auth

import com.example.myapplication.database.UserDao
import com.example.myapplication.datastore.EmailDataStore
import com.example.myapplication.presentation.ui.auth.state.AuthViewState
import com.example.myapplication.utils.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Attempts to login the user
 * - If the login credentials are correct, email is stored into [emailDataStore]
 */
class AttemptLogin(
    private val userDao: UserDao,
    private val emailDataStore: EmailDataStore
) {

    fun execute(
        stateEvent: StateEvent,
        email: String,
        password: String
    ): Flow<DataState<AuthViewState>?> = flow {

        val user = userDao.searchByEmail(email)

        if (user == null || user.password != password) {
            emit(
                DataState.error<AuthViewState>(
                    response = Response(
                        INVALID_CREDENTIALS,
                        UiType.SnackBar,
                        MessageType.Error
                    ),
                    stateEvent = stateEvent
                )
            )
        } else {

            emailDataStore.updateUserEmail(email)

            emit(
                DataState.data(
                    data = AuthViewState(),
                    response = Response(
                        message = "Successfully logged in",
                        uiType = UiType.None,
                        messageType = MessageType.Success
                    ),
                    stateEvent = stateEvent
                )
            )
        }
    }

    companion object {
        const val INVALID_CREDENTIALS = "Invalid credentials"
    }
}