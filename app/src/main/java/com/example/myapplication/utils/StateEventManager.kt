package com.example.myapplication.utils

import androidx.compose.runtime.mutableStateOf

class StateEventManager {

    private val activeStateEvents: HashMap<String, StateEvent> = HashMap()
    val shouldDisplayProgressbar = mutableStateOf(false)

    fun isStateEventActive(stateEvent: StateEvent): Boolean {
        println(
            "StateEventManager: Is StateEvent Active? : ${
                activeStateEvents.containsKey(
                    stateEvent.eventName()
                )
            }"
        )
        return activeStateEvents.containsKey(stateEvent.eventName())
    }

    fun addStateEvent(stateEvent: StateEvent) {
        println(
            "StateEventManager : Launching New StateEvent --------> ${stateEvent.eventName()}"
        )
        activeStateEvents[stateEvent.eventName()] = stateEvent
        syncProgressbar()
    }

    fun removeStateEvent(stateEvent: StateEvent?) {
        println("StateEventManager : Removed StateEvent ----> ${stateEvent?.eventName()}")
        activeStateEvents.remove(stateEvent?.eventName())
        syncProgressbar()
    }

    fun getActiveStateEvents(): MutableSet<String> {
        return activeStateEvents.keys
    }

    fun clearActiveStateEvents() {
        println("StateEventManager : Clearing active state events")
        activeStateEvents.clear()
        syncProgressbar()
    }

    private fun syncProgressbar() {
        var progressBar = false
        for (stateEvent in activeStateEvents.values) {
            if (stateEvent.shouldDisplayProgressBar()) {
                progressBar = true
            }
        }
        shouldDisplayProgressbar.value = progressBar
        println("StateEventManager : ProgressBar $progressBar ")
    }
}