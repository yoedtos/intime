package net.yoedtos.intime.service.base

import com.google.android.gms.tasks.Task

interface ProviderAuth {
    fun register(email: String, password: String): Task<*>
    fun login(email: String, password: String): Task<*>
    fun logout()
    fun isAuthenticated(): Boolean
    fun getUserId(): String
}