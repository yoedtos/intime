package net.yoedtos.intime.client

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import net.yoedtos.intime.service.base.ProviderAuth

class FileBaseAuth : ProviderAuth {

    private val firebaseAuth = FirebaseAuth.getInstance()

    init {
        firebaseAuth.useEmulator("10.0.2.2", 9099)
    }

    override fun register(email: String, password: String): Task<AuthResult> {
        return firebaseAuth.createUserWithEmailAndPassword(email, password)
    }

    override fun login(email: String, password: String): Task<AuthResult> {
        return firebaseAuth.signInWithEmailAndPassword(email, password)
    }

    override fun logout() {
        firebaseAuth.signOut()
    }

    override fun isAuthenticated(): Boolean {
        if (firebaseAuth.currentUser != null) {
            return true
        }
        return false
    }

    override fun getUserId(): String {
        return firebaseAuth.currentUser?.uid ?: ""
    }
}
