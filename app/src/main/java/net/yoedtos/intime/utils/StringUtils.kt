package net.yoedtos.intime.utils

import android.content.Context
import android.text.format.DateUtils.*

class StringUtils(val context: Context) {

    fun formatDateFromMillis(millis: Long): String {
        return formatDateTime(context, millis,
            FORMAT_SHOW_YEAR or FORMAT_SHOW_DATE or FORMAT_NUMERIC_DATE)
    }

}
