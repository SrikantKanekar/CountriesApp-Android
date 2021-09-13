package com.example.myapplication.presentation.ui.main.state

import com.example.myapplication.utils.StateEvent

sealed class MainStateEvent : StateEvent {

    object GetCountriesEvent : MainStateEvent() {
        override fun errorInfo() = "Failed to get Countries"
        override fun eventName() = "GetCountriesEvent"
        override fun shouldDisplayProgressBar() = true
    }
}