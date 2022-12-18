package net.yoedtos.intime.utils

import android.content.Context
import android.net.Uri
import android.telephony.PhoneNumberUtils
import android.text.format.DateUtils.*
import android.webkit.MimeTypeMap
import net.yoedtos.intime.client.Document
import net.yoedtos.intime.model.Constants
import java.util.*

class StringUtils(val context: Context) {
    fun formatDateFromMillis(millis: Long): String {
        return formatDateTime(context, millis,
            FORMAT_SHOW_YEAR or FORMAT_SHOW_DATE or FORMAT_NUMERIC_DATE)
    }

    fun formatPhoneNumber(number: String): String {
        return PhoneNumberUtils.formatNumber(number, Locale.getDefault().country)
    }

    fun createFileName(uri: Uri): String {
        return Document.USER + System.currentTimeMillis() + Constants.DOT + getFileExtension(uri)
    }

    private fun getFileExtension(uri: Uri): String? {
        return MimeTypeMap
            .getSingleton()
            .getExtensionFromMimeType(context.contentResolver.getType(uri))
    }
}
