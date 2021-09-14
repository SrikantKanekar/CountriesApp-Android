package com.example.myapplication.presentation.ui.main

import androidx.compose.runtime.mutableStateOf
import com.example.myapplication.interactors.main.MainInteractors
import com.example.myapplication.presentation.ui.BaseViewModel
import com.example.myapplication.presentation.ui.main.state.MainStateEvent
import com.example.myapplication.presentation.ui.main.state.MainStateEvent.*
import com.example.myapplication.presentation.ui.main.state.MainViewState
import com.example.myapplication.utils.DataState
import com.example.myapplication.utils.SessionManager
import com.example.myapplication.utils.StateEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val sessionManager: SessionManager,
    private val mainInteractors: MainInteractors
) : BaseViewModel<MainViewState>() {

    val logoutDialog = mutableStateOf(false)
    val sortDialog = mutableStateOf(false)
    val ascending = mutableStateOf(true)

    fun setAscending(){
        ascending.value = true
        val sorted = viewState.value.countries?.sortedBy { it.name }
        setViewState(viewState.value.copy(countries = sorted))
        sortDialog.value = false
    }

    fun setDescending(){
        ascending.value = false
        val sorted = viewState.value.countries?.sortedByDescending { it.name }
        setViewState(viewState.value.copy(countries = sorted))
        sortDialog.value = false
    }

    init {
        setStateEvent(GetCountriesEvent)
    }

    override fun handleNewData(data: MainViewState) {
        data.countries?.let { countries ->
            setViewState(viewState.value.copy(countries = countries))
        }
    }

    override fun setStateEvent(stateEvent: StateEvent) {
        val job: Flow<DataState<MainViewState>?> = when (stateEvent) {

            is GetCountriesEvent -> {
                mainInteractors.getCountries.execute(
                    stateEvent = stateEvent
                )
            }

            is DeleteUserEvent -> {
                mainInteractors.deleteUser.execute(
                    stateEvent = stateEvent
                )
            }
            else -> emitInvalidStateEvent(stateEvent)
        }
        launchJob(stateEvent, job)
    }

    override fun initViewState(): MainViewState {
        return MainViewState()
    }

    fun deleteUser() {
        sessionManager.logout()
        setStateEvent(DeleteUserEvent)
    }

    fun logout() {
        sessionManager.logout()
    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }
}