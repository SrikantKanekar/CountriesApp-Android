package com.example.myapplication.interactors.auth

import com.example.myapplication.database.dao.TokenDao
import com.example.myapplication.database.dao.UserDao
import com.example.myapplication.database.entity.Token
import com.example.myapplication.database.entity.User
import com.example.myapplication.datastore.EmailDataStore
import com.example.myapplication.interactors.auth.AttemptLogin.Companion.ERROR_SAVE_AUTH_TOKEN
import com.example.myapplication.interactors.auth.AttemptLogin.Companion.GENERIC_AUTH_ERROR
import com.example.myapplication.interactors.auth.AttemptRegistration.Companion.ERROR_SAVE_ACCOUNT_PROPERTIES
import com.example.myapplication.interactors.auth.AttemptRegistration.Companion.ERROR_SAVE_AUTH_TOKEN
import com.example.myapplication.interactors.auth.AttemptRegistration.Companion.GENERIC_AUTH_ERROR
import com.example.myapplication.presentation.ui.auth.state.AuthViewState
import com.example.myapplication.utils.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

/**
 * - Attempt to resister the user
 * - make registration attempt
 * - If the registration credentials are invalid, [GENERIC_AUTH_ERROR] string is returned from the server and error is shown
 * - If the registration credentials are correct, accountProperties are inserted into [AccountProperties] database
 * - If -1 is returned, [ERROR_SAVE_ACCOUNT_PROPERTIES] is shown to user
 * - Similarly, pk and token is inserted into [AuthToken] database
 * - If -1 is returned, [ERROR_SAVE_AUTH_TOKEN] is shown to user
 * - email is stored in [EmailDataStore]
 * - [AuthToken] is returned to [AuthViewState]
 */
class AttemptRegistration(
    private val tokenDao: TokenDao,
    private val userDao: UserDao,
    private val emailDataStore: EmailDataStore
) {

    fun execute(
        stateEvent: StateEvent,
        name: String,
        mobile: String,
        email: String,
        password: String
    ): Flow<DataState<AuthViewState>?> = flow {

        val user = userDao.searchByEmail(email)

        if (user != null){
            emit(
                DataState.error<AuthViewState>(
                    response = Response(
                        USER_ALREADY_EXIST,
                        UiType.SnackBar,
                        MessageType.Error
                    ),
                    stateEvent = stateEvent
                )
            )
        } else {

            val pk = Random.nextInt(0, 5000)

            // Save user
            val result1 = userDao.insertAndReplace(
                User(
                    pk = pk,
                    name = name,
                    mobile = mobile,
                    email = email,
                    password = password
                )
            )
            if (result1 < 0) {
                emit(
                    DataState.error(
                        response = Response(
                            ERROR_SAVE_ACCOUNT_PROPERTIES,
                            UiType.Dialog,
                            MessageType.Error
                        ),
                        stateEvent = stateEvent
                    )
                )
                return@flow
            }

            // Save token
            val token = Token(
                pk,
                "token"
            )
            val result2 = tokenDao.insert(token)
            if (result2 < 0) {
                emit(
                    DataState.error(
                        response = Response(
                            ERROR_SAVE_AUTH_TOKEN,
                            UiType.Dialog,
                            MessageType.Error
                        ),
                        stateEvent = stateEvent
                    )
                )
                return@flow
            }

            // Save email
            emailDataStore.updateUserEmail(email)

            emit(
                DataState.data(
                    data = AuthViewState(token = token),
                    stateEvent = stateEvent,
                    response = null
                )
            )
        }
    }

    companion object {
        const val USER_ALREADY_EXIST = "User with given email already exists"
        const val ERROR_SAVE_ACCOUNT_PROPERTIES =
            "Error saving account properties.\nTry restarting the app."
        const val ERROR_SAVE_AUTH_TOKEN =
            "Error saving authentication token.\nTry restarting the app."
        const val GENERIC_AUTH_ERROR = "Error"
    }
}