package com.example.myapplication.presentation.ui.auth

import com.example.myapplication.interactors.auth.AuthInteractors
import com.example.myapplication.presentation.ui.BaseViewModel
import com.example.myapplication.presentation.ui.auth.state.AuthStateEvent.*
import com.example.myapplication.presentation.ui.auth.state.AuthViewState
import com.example.myapplication.utils.DataState
import com.example.myapplication.utils.StateEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
@Inject
constructor(
    private val authInteractors: AuthInteractors
) : BaseViewModel<AuthViewState>() {

    init {
        setStateEvent(CheckPreviousAuthEvent)
    }

    override fun handleNewData(data: AuthViewState) {
        data.previousUserCheck?.let { checked ->
            setViewState(viewState.value.copy(previousUserCheck = checked))
        }
    }

    override fun setStateEvent(stateEvent: StateEvent) {
        val job: Flow<DataState<AuthViewState>?> = when (stateEvent) {

            is LoginAttemptEvent -> {
                authInteractors.attemptLogin.execute(
                    stateEvent = stateEvent,
                    email = stateEvent.email,
                    password = stateEvent.password
                )
            }

            is RegisterAttemptEvent -> {
                authInteractors.attemptRegistration.execute(
                    stateEvent = stateEvent,
                    name = stateEvent.name,
                    mobile = stateEvent.mobile,
                    email = stateEvent.email,
                    password = stateEvent.password,
                )
            }

            is CheckPreviousAuthEvent -> {
                authInteractors.checkPreviousUser.execute(stateEvent)
            }

            else -> emitInvalidStateEvent(stateEvent)
        }
        launchJob(stateEvent, job)
    }

    override fun initViewState(): AuthViewState {
        return AuthViewState()
    }

    fun login(
        email: String,
        password: String
    ) {
        setStateEvent(LoginAttemptEvent(email, password))
    }

    fun register(
        name: String,
        mobile: String,
        email: String,
        password: String,
    ) {
        setStateEvent(
            RegisterAttemptEvent(
                name = name,
                mobile = mobile,
                email = email,
                password = password
            )
        )
    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }
}