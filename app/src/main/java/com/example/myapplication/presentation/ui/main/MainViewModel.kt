package com.example.myapplication.presentation.ui.main

import com.example.myapplication.interactors.main.MainInteractors
import com.example.myapplication.presentation.ui.BaseViewModel
import com.example.myapplication.presentation.ui.main.state.MainStateEvent
import com.example.myapplication.presentation.ui.main.state.MainStateEvent.*
import com.example.myapplication.presentation.ui.main.state.MainViewState
import com.example.myapplication.utils.DataState
import com.example.myapplication.utils.StateEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val mainInteractors: MainInteractors
) : BaseViewModel<MainViewState>() {

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
            else -> emitInvalidStateEvent(stateEvent)
        }
        launchJob(stateEvent, job)
    }

    override fun initViewState(): MainViewState {
        return MainViewState()
    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }
}