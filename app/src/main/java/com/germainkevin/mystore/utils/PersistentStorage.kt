package com.germainkevin.mystore.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map


class PersistentStorage(private val context: Context) {

    companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
        val IS_USING_DYNAMIC_THEME = booleanPreferencesKey("isUsingDynamicTheme")
    }

    suspend fun setUsingDynamicThemeState(newValue: Boolean) {
        context.dataStore.edit { settings ->
            settings[IS_USING_DYNAMIC_THEME] = newValue
        }
    }

    val isUsingDynamicTheme: Flow<Boolean> = context.dataStore.data.map { preferences ->
        // No type safety.
        preferences[IS_USING_DYNAMIC_THEME] ?: false
    }

}