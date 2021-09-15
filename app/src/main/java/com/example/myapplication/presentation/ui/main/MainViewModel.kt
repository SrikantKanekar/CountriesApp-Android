package com.example.myapplication.presentation.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.myapplication.SettingPreferences.*
import com.example.myapplication.datastore.EmailDataStore
import com.example.myapplication.datastore.SettingDataStore
import com.example.myapplication.interactors.main.MainInteractors
import com.example.myapplication.model.Country
import com.example.myapplication.model.enums.SortFilter
import com.example.myapplication.model.enums.SortFilter.*
import com.example.myapplication.presentation.ui.BaseViewModel
import com.example.myapplication.model.enums.SortFilterRegion
import com.example.myapplication.model.enums.SortFilterRegion.*
import com.example.myapplication.model.enums.SortOptions
import com.example.myapplication.model.enums.SortOptions.*
import com.example.myapplication.presentation.ui.main.state.MainStateEvent.*
import com.example.myapplication.presentation.ui.main.state.MainViewState
import com.example.myapplication.utils.DataState
import com.example.myapplication.utils.StateEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val mainInteractors: MainInteractors,
    private val settingDataStore: SettingDataStore,
    private val emailDataStore: EmailDataStore
) : BaseViewModel<MainViewState>() {

    var countries by mutableStateOf<List<Country>>(emptyList())
    val logoutDialog = mutableStateOf(false)

    init {
        setStateEvent(GetCountriesEvent)
    }

    override fun handleNewData(data: MainViewState) {
        data.countries?.let { list ->
            setViewState(viewState.value.copy(countries = list))
            countries = list
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

    // App bar
    var expanded by mutableStateOf(false)

    fun toggleAppBar(value: Boolean){
        expanded = value
    }

    // Search
    var query by mutableStateOf("")

    fun onQueryChanged(value: String) {
        query = value
        resetAppBar()
        countries = viewState.value.countries?.filter {
            it.name.startsWith(prefix = value, ignoreCase = true)
        }.orEmpty()
    }

    // Sorting options
    var sortOptions by mutableStateOf(Name)

    fun handleSortOptionChange(option: SortOptions){
        sortOptions = option
        resetFilters()

        val list = viewState.value.countries.orEmpty()
        when(option){
            Name -> list.sortedBy { it.name }
            Region -> list.filter { it.region == Africa }
            Population -> list.sortedBy { it.population }
            Area -> list.sortedBy { it.area }
        }
    }

    // filter
    var sortFilter by mutableStateOf(Ascending)

    fun handleSortFilterChange(filter: SortFilter) {
        sortFilter = filter
        val list = viewState.value.countries.orEmpty()
        countries = when(filter){
            Ascending -> {
                when(sortOptions){
                    Name -> list.sortedBy { it.name }
                    Region -> list
                    Population -> list.sortedBy { it.population }
                    Area -> list.sortedBy { it.area }
                }
            }
            Descending -> {
                when(sortOptions){
                    Name -> list.sortedByDescending { it.name }
                    Region -> list
                    Population -> list.sortedByDescending { it.population }
                    Area -> list.sortedByDescending { it.area }
                }
            }
        }
    }

    // Region Sorting
    var sortFilterRegion by mutableStateOf(Africa)

    fun handleSortFilterRegionChange(region: SortFilterRegion) {
        sortFilterRegion = region
        val list = viewState.value.countries.orEmpty()
        countries = list.filter { it.region == region }
    }

    private fun resetAppBar(){
        expanded = false
        sortOptions = Name
        resetFilters()
    }

    private fun resetFilters(){
        sortFilter = Ascending
        sortFilterRegion = Africa
    }

    // Theme
    val settingFlow = settingDataStore.settingFlow

    fun setTheme(theme: Theme) {
        viewModelScope.launch {
            settingDataStore.updateTheme(theme)
        }
    }

    fun deleteUser() {
        setStateEvent(DeleteUserEvent)
    }

    fun logout() {
        viewModelScope.launch {
            emailDataStore.updateUserEmail("")
        }
    }
}