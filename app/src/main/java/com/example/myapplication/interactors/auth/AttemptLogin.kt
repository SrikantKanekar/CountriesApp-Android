package com.example.myapplication.interactors.auth

import com.example.myapplication.database.dao.TokenDao
import com.example.myapplication.database.dao.UserDao
import com.example.myapplication.database.entity.Token
import com.example.myapplication.datastore.EmailDataStore
import com.example.myapplication.interactors.auth.AttemptLogin.Companion.ERROR_SAVE_AUTH_TOKEN
import com.example.myapplication.interactors.auth.AttemptLogin.Companion.GENERIC_AUTH_ERROR
import com.example.myapplication.interactors.auth.AttemptLogin.Companion.INVALID_CREDENTIALS
import com.example.myapplication.presentation.ui.auth.state.AuthViewState
import com.example.myapplication.utils.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Attempts to login the user
 * - make login attempt
 * - If the login credentials are Invalid, [GENERIC_AUTH_ERROR] string is returned from the server and [INVALID_CREDENTIALS] is returned
 * - If the login credentials are correct, pk and email is inserted into [AccountProperties] database
 * - Similarly, pk and token is inserted into [AuthToken] database
 * - If -1 is returned, [ERROR_SAVE_AUTH_TOKEN] is shown to user
 * - email is stored in [EmailDataStore]
 * - [AuthToken] is returned to [AuthViewState]
 */
class AttemptLogin(
    private val tokenDao: TokenDao,
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

            val authToken = Token(
                user.pk,
                "token"
            )

            emailDataStore.updateAuthenticatedUserEmail(email)

            emit(
                DataState.data(
                    data = AuthViewState(
                        token = authToken
                    ),
                    response = Response(
                        message = "Logged in User",
                        uiType = UiType.None,
                        messageType = MessageType.Success
                    ),
                    stateEvent = stateEvent
                )
            )
        }
    }

    companion object {
        const val ERROR_SAVE_AUTH_TOKEN =
            "Error saving authentication token.\nTry restarting the app."
        const val GENERIC_AUTH_ERROR = "Error"
        const val INVALID_CREDENTIALS = "Invalid credentials"
    }
}