package com.example.myapplication.utils

abstract class CacheResponseHandler<ViewState, Data>(
    private val response: CacheResult<Data?>,
    private val stateEvent: StateEvent?
) {
    suspend fun getResult(): DataState<ViewState>? {

        return when (response) {

            is CacheResult.GenericError -> {
                println(
                    "CacheResponseHandler : ----------Cache Error-----------\n" +
                            "Error Message : ${response.errorMessage}"
                )
                DataState.error(
                    response = Response(
                        message = "${stateEvent?.errorInfo()}\n\nReason: ${response.errorMessage}",
                        uiType = UiType.Dialog,
                        messageType = MessageType.Error
                    ),
                    stateEvent = stateEvent
                )
            }

            is CacheResult.Success -> {
                if (response.value == null) {
                    println(
                        "CacheResponseHandler : ----------Cache Error-----------\n" +
                                "Error : Cache data is null"
                    )
                    DataState.error(
                        response = Response(
                            message = "${stateEvent?.errorInfo()}\n\nReason: $CACHE_DATA_NULL.",
                            uiType = UiType.None,
                            messageType = MessageType.Error
                        ),
                        stateEvent = stateEvent
                    )
                } else {
                    handleSuccess(resultObj = response.value)
                }
            }

        }
    }

    abstract suspend fun handleSuccess(resultObj: Data): DataState<ViewState>?

    companion object {
        const val CACHE_DATA_NULL = "Cache data is null"
    }
}