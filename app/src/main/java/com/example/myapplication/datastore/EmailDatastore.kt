package com.example.myapplication.datastore

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import com.example.myapplication.presentation.ui.BaseApplication
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmailDataStore
@Inject
constructor(
    app: BaseApplication
) {

    private val dataStore = app.createDataStore("EMAIL_DATASTORE_FILE")

    val preferenceFlow = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val email = preferences[PreferenceKeys.AUTHENTICATED_USER_EMAIL]
            email
        }

    suspend fun updateAuthenticatedUserEmail(email: String) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.AUTHENTICATED_USER_EMAIL] = email
        }
    }

    private object PreferenceKeys {
        val AUTHENTICATED_USER_EMAIL = stringPreferencesKey("AUTHENTICATED_USER_EMAIL")
    }
}