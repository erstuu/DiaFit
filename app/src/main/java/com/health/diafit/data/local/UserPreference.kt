package com.health.diafit.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class UserPreference @Inject constructor(@ApplicationContext context: Context) {

    private val dataStore: DataStore<Preferences> = context.dataStore

    fun getUsername(): Flow<String> {
        return dataStore.data.map { preferences ->
            val name = preferences[USER_NAME] ?: DEFAULT_NAME
            name
        }
    }

    suspend fun saveLanguage(language: String) {
        dataStore.edit { preferences ->
            preferences[LANGUAGE] = language
        }
    }

    fun getLanguageSetting(): Flow<String> {
        return dataStore.data.map { preferences ->
            val lang = preferences[LANGUAGE] ?: DEFAULT_LANGUAGE
            lang
        }
    }

    fun getLanguageSync(): String {
        return runBlocking {
            dataStore.data.first()[LANGUAGE] ?: DEFAULT_LANGUAGE
        }
    }

    suspend fun saveUsername(name: String) {
        try {
            dataStore.edit { preferences ->
                preferences[USER_NAME] = name
            }
        } catch (e: Exception) {
            throw e
        }
    }

    fun isOnboardingCompleted(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[ONBOARDING_COMPLETED] ?: false
        }
    }

    suspend fun setOnboardingCompleted() {
        dataStore.edit { preferences ->
            preferences[ONBOARDING_COMPLETED] = true
        }
    }

    companion object {
        private val USER_NAME = stringPreferencesKey("name")
        private val LANGUAGE = stringPreferencesKey("language")
        private val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")

        private const val DEFAULT_LANGUAGE = "id"
        private const val DEFAULT_NAME = "Sobat Diafit"
    }
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "userPreference")