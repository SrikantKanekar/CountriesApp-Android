package com.example.myapplication.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.database.dao.TokenDao
import com.example.myapplication.database.entity.Token
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager
@Inject
constructor(
    private val authTokenDao: TokenDao,
) {
    private val _cachedToken = MutableLiveData<Token>()

    val cachedToken: LiveData<Token>
        get() = _cachedToken

    fun setValue(newValue: Token) {
        GlobalScope.launch(Main) {
            if (_cachedToken.value != newValue) {
                _cachedToken.value = newValue
            }
        }
    }

    fun login(newValue: Token) {
        setValue(newValue)
    }

    fun logout() {
        println("SessionManager : Logging out")

        CoroutineScope(IO).launch {
            var errorMessage: String? = null
            try {
                _cachedToken.value!!.account_pk?.let { token ->
                    authTokenDao.nullifyToken(token)
                } ?: throw CancellationException("Token Error. Logging out user.")
            } catch (e: CancellationException) {
                println("SessionManager : CancellationException -> ${e.message}")
                errorMessage = e.message
            } catch (e: Exception) {
                println("SessionManager : Exception -> ${e.message}")
                errorMessage = errorMessage + "\n" + e.message
            } finally {
                errorMessage?.let {
                    println("SessionManager : Finally -> $errorMessage")
                }
                println("SessionManager : Finally Logged out")
                setValue(Token())
            }
        }
    }
}