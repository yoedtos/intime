package net.yoedtos.intime.view.info

import android.app.AlertDialog
import android.content.Context
import net.yoedtos.intime.R

class ErrorAlert(context: Context, message: String) : AlertDialog.Builder(context) {

    init {
        setTitle(context.getString(R.string.provider_error))
        setMessage(context.resources.getString(R.string.provider_error_msg, message))
        setIcon(android.R.drawable.ic_dialog_alert)
        setCancelable(true)
        setNeutralButton(android.R.string.ok) { dialogInterface, _ ->
            dialogInterface.dismiss()
        }
    }
}