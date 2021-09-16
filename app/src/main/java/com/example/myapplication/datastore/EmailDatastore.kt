package com.example.myapplication.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.myapplication.datastore.EmailDataStore.PreferenceKeys.AUTHENTICATED_USER_EMAIL
import com.example.myapplication.presentation.ui.BaseApplication
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmailDataStore
@Inject
constructor(
    private val application: BaseApplication
) {
    private val Context.emailDataStore: DataStore<Preferences> by preferencesDataStore(name = "EMAIL_DATASTORE_FILE")

    val preferenceFlow: Flow<String> = application.emailDataStore.data
        .map { preferences ->
            preferences[AUTHENTICATED_USER_EMAIL] ?: ""
        }

    suspend fun updateUserEmail(email: String) {
        withContext(IO) {
            application.emailDataStore.edit { preferences ->
                preferences[AUTHENTICATED_USER_EMAIL] = email
            }
        }
    }

    private object PreferenceKeys {
        val AUTHENTICATED_USER_EMAIL = stringPreferencesKey("AUTHENTICATED_USER_EMAIL")
    }
}