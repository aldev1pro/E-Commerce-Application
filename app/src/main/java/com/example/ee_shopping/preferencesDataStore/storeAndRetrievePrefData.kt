package com.example.ee_shopping.preferencesDataStore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.ee_shopping.PREFERENCES_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException



object PreferencesTokenKey {

     val nameHolder = stringPreferencesKey(name = "nameHolder")

    fun readData(context: Context): Flow<String> {
        val contactData: Flow<String> = context.dataStore.data
            .catch {
                if (it is IOException) {
                    emit(emptyPreferences())
                }
            }
            .map {
                it[nameHolder] ?: ""
            }
        return contactData
    }

    suspend fun addData(context: Context, data: String) {
        context.dataStore.edit {
            it[nameHolder] = data
        }
    }

    suspend fun deleteData(context: Context) {
        context.dataStore.edit {
            it[nameHolder] = ""
        }
    }
}
