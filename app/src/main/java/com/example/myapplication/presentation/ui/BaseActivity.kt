package com.example.myapplication.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.SettingPreferences.Theme.LIGHT
import com.example.myapplication.datastore.EmailDataStore
import com.example.myapplication.datastore.SettingDataStore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var emailDataStore: EmailDataStore

    @Inject
    lateinit var settingDataStore: SettingDataStore

    var appTheme by mutableStateOf(LIGHT)

    override fun onStart() {
        super.onStart()
        lifecycleScope.launchWhenCreated {
            settingDataStore.settingFlow.collect { setting ->
                appTheme = setting.theme
            }
        }
    }
}