package net.yoedtos.intime.service

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.storage.UploadTask
import net.yoedtos.intime.client.FileBaseStorage
import net.yoedtos.intime.service.base.StorageProvider

private val LOG_TAG = StorageService::class.simpleName

class StorageService private constructor(context: Context) {
    private val storageProvider: StorageProvider

    companion object {
        private var instance: StorageService? = null

        fun getInstance(context: Context): StorageService {
            if (instance == null) {
                instance = StorageService(context)
            }
            return instance!!
        }
    }

    init {
        storageProvider = FileBaseStorage(context)
    }

    fun upload(uri: Uri, resultListener: ResultListener) {
        val uploadTask = storageProvider.upload(uri) as UploadTask
        uploadTask
            .addOnSuccessListener { taskSnapshot ->
                taskSnapshot.metadata?.reference?.downloadUrl
                    ?.addOnSuccessListener { uri ->
                        val profileImageUrl = uri.toString()
                        Log.d(LOG_TAG, "URL: $profileImageUrl")
                        resultListener.onSuccess(profileImageUrl)
                    }
                    ?.addOnFailureListener {
                        Log.e(LOG_TAG, "Error: ${it.message}")
                        resultListener.onFailure(it.message.toString())
                    }

            }
            .addOnFailureListener{
                Log.e(LOG_TAG, "Failed: ${it.message}")
                resultListener.onFailure(it.message.toString())
            }
    }
}