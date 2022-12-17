package net.yoedtos.intime.service

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import net.yoedtos.intime.InTimeApp
import net.yoedtos.intime.client.FileBaseAuth
import net.yoedtos.intime.model.Constants
import net.yoedtos.intime.model.dto.Login
import net.yoedtos.intime.model.dto.Register
import net.yoedtos.intime.service.base.ProviderAuth
import net.yoedtos.intime.utils.CacheHandler

private val LOG_TAG = AuthService::class.simpleName

class AuthService private constructor(){
    private val authenticator: ProviderAuth

    companion object {
        private var instance: AuthService? = null

        fun getInstance(): AuthService {
            if (instance == null) {
                instance = AuthService()
            }
            return instance!!
        }
    }

    init {
        authenticator = FileBaseAuth()
    }

    fun register(register: Register, resultListener: ResultListener) {
        val task = authenticator.register(register.email, register.password) as Task<AuthResult>
        task
            .addOnCompleteListener {
                if (task.isSuccessful) {
                    val firebaseUser = task.result?.user
                    Log.d(LOG_TAG, "Created ID: ${firebaseUser?.uid}")
                    resultListener.onSuccess(Constants.OK)
                }
            }
            .addOnFailureListener {
                resultListener.onFailure(it.message.toString())
            }
    }

    fun login(login: Login, resultListener: ResultListener) {
        val task = authenticator.login(login.email, login.password) as Task<AuthResult>
        task
            .addOnCompleteListener {
                if (task.isSuccessful) {
                    val firebaseUser = task.result?.user
                    Log.d(LOG_TAG, "Logged ID: ${firebaseUser?.uid}")
                    resultListener.onSuccess(Constants.OK)
                }
            }
            .addOnFailureListener {
                resultListener.onFailure(it.message.toString())
            }
    }

    fun logout() {
        InTimeApp.instance?.let {
            Log.d(LOG_TAG, "Cache cleared!")
            CacheHandler(it.getContext()).clearAll()
        }
        authenticator.logout()
    }

    fun isAuthenticated(): Boolean {
        return authenticator.isAuthenticated()
    }

    fun getCurrentUserID(): String {
        return authenticator.getUserId()
    }
}