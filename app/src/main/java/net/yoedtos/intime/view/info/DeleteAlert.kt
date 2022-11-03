package net.yoedtos.intime.view.info

import android.app.AlertDialog
import android.content.Context
import net.yoedtos.intime.R

class DeleteAlert(context: Context, name: String) : AlertDialog.Builder(context) {

    init {
        setTitle(context.getString(R.string.alert_title))
        setMessage(context.resources.getString(R.string.alert_delete_confirm, name))
        setIcon(android.R.drawable.ic_dialog_alert)
        setCancelable(true)
        setNegativeButton(context.getString(R.string.alert_no)) { dialogInterface, _ ->
            dialogInterface.dismiss()
        }
    }
}