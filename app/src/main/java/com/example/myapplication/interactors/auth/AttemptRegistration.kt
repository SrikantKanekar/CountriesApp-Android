package com.example.myapplication.interactors.auth

import com.example.myapplication.database.User
import com.example.myapplication.database.UserDao
import com.example.myapplication.datastore.EmailDataStore
import com.example.myapplication.interactors.auth.AttemptRegistration.Companion.ERROR_SAVE_USER
import com.example.myapplication.presentation.ui.auth.state.AuthViewState
import com.example.myapplication.utils.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * - Attempt to resister the user
 * - If the registration credentials are correct, credentials are inserted into [User] database
 * - If -1 is returned, [ERROR_SAVE_USER] is shown to user
 * - email is stored in [EmailDataStore]
 */
class AttemptRegistration(
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

        if (user != null) {
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

            // Save user
            val result = userDao.insertAndReplace(
                User(
                    name = name,
                    mobile = mobile,
                    email = email,
                    password = password
                )
            )
            if (result < 0) {
                emit(
                    DataState.error(
                        response = Response(
                            ERROR_SAVE_USER,
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
                    data = AuthViewState(),
                    response = Response(
                        message = REGISTRATION_SUCCESSFUL,
                        uiType = UiType.None,
                        messageType = MessageType.Success
                    ),
                    stateEvent = stateEvent
                )
            )
        }
    }

    companion object {
        const val USER_ALREADY_EXIST = "User with given email already exists"
        const val ERROR_SAVE_USER = "Error saving user, try restarting the app."
        const val REGISTRATION_SUCCESSFUL = "Successfully registered the user"
    }
}