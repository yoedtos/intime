package net.yoedtos.intime.client

import android.content.Context
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import net.yoedtos.intime.service.base.StorageProvider
import net.yoedtos.intime.utils.StringUtils

class FileBaseStorage(private val context: Context): StorageProvider {

    private var filebaseStorage = FirebaseStorage.getInstance()

    init {
        filebaseStorage.useEmulator("10.0.2.2", 9199)
    }

    override fun upload(any: Any): UploadTask {
        val fileImageUri = any as Uri

        return filebaseStorage.reference
            .child(createImageName(fileImageUri))
            .putFile(fileImageUri)
    }

    private fun createImageName(uri: Uri): String {
        return StringUtils(context).createFileName(uri)
    }
}