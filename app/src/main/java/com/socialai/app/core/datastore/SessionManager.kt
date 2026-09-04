package com.socialai.app.core.datastore
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.socialai.app.core.data.model.User
import com.socialai.app.core.network.TokenProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")
@Singleton
class SessionManager @Inject constructor(@ApplicationContext private val context: Context) : TokenProvider {
    private object Keys {
        val TOKEN = stringPreferencesKey("jwt_token")
        val USER_ID = stringPreferencesKey("user_id")
        val USER_ROLE = stringPreferencesKey("user_role")
        val USER_NAME = stringPreferencesKey("user_name")
        val SERVER_URL = stringPreferencesKey("server_url")
    }
    suspend fun saveSession(token: String, user: User) {
        context.dataStore.edit { it[Keys.TOKEN] = token; it[Keys.USER_ID] = user.id; it[Keys.USER_ROLE] = user.role; it[Keys.USER_NAME] = user.name }
    }
    suspend fun continueAsGuestCitizen() {
        context.dataStore.edit {
            it.remove(Keys.TOKEN)
            it.remove(Keys.USER_ID)
            it[Keys.USER_ROLE] = "CITIZEN"
            it[Keys.USER_NAME] = "Guest Citizen"
        }
    }
    suspend fun clearSession() {
        context.dataStore.edit { it.remove(Keys.TOKEN); it.remove(Keys.USER_ID); it.remove(Keys.USER_ROLE); it.remove(Keys.USER_NAME) }
    }
    suspend fun saveServerUrl(url: String) { context.dataStore.edit { it[Keys.SERVER_URL] = url } }
    val token: Flow<String?> = context.dataStore.data.map { it[Keys.TOKEN] }
    val userId: Flow<String?> = context.dataStore.data.map { it[Keys.USER_ID] }
    val userRole: Flow<String?> = context.dataStore.data.map { it[Keys.USER_ROLE] }
    val userName: Flow<String?> = context.dataStore.data.map { it[Keys.USER_NAME] }
    val serverUrl: Flow<String?> = context.dataStore.data.map { it[Keys.SERVER_URL] }
    override fun getToken(): String? = runBlocking { context.dataStore.data.first()[Keys.TOKEN] }
    fun getServerUrlBlocking(): String? = runBlocking { context.dataStore.data.first()[Keys.SERVER_URL] }
}
