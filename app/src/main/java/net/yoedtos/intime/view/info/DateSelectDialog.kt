package net.yoedtos.intime.view.info

import android.app.DatePickerDialog
import android.content.Context
import java.util.*

class DateSelectDialog(context: Context, listener: DatePickerDialog.OnDateSetListener) {
    private val datePickerDialog: DatePickerDialog

    init {
        val calendar = Calendar.getInstance()
        datePickerDialog = DatePickerDialog(context, listener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH))
    }

    fun setMinDate(minDate: Long) {
        datePickerDialog.datePicker.minDate = minDate
    }

    fun show() {
        datePickerDialog.show()
    }
}