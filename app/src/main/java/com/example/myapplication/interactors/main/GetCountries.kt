package com.example.myapplication.interactors.main

import com.example.myapplication.model.Country
import com.example.myapplication.network.CountriesApi
import com.example.myapplication.presentation.ui.main.state.MainViewState
import com.example.myapplication.utils.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCountries(
    private val countriesApi: CountriesApi
) {

    fun execute(
        stateEvent: StateEvent,
    ): Flow<DataState<MainViewState>?> = flow {

        val apiResult = safeApiCall(IO) {
            countriesApi.getAllCountries()
        }
        val response = object : ApiResponseHandler<MainViewState, List<Country>>(
            response = apiResult,
            stateEvent = null
        ) {
            override suspend fun handleSuccess(resultObj: List<Country>): DataState<MainViewState> {
                print(resultObj.toString())
                return DataState.data(
                    response = Response(
                        message = "Successfully fetched countries",
                        uiType = UiType.None,
                        messageType = MessageType.Success
                    ),
                    data = MainViewState(resultObj),
                    stateEvent = stateEvent
                )
            }
        }.getResult()

        emit(response)
    }
}