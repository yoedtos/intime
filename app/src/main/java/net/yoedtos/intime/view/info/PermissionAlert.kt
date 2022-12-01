package net.yoedtos.intime.view.info

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import net.yoedtos.intime.R
import net.yoedtos.intime.model.Constants

class PermissionAlert(context: Context) : AlertDialog.Builder(context) {

    init {
        setTitle(R.string.permission_request)
        setMessage(R.string.permission_request_msg)
        setIcon(android.R.drawable.ic_dialog_alert)
        setPositiveButton(R.string.go_settings) { _, _ ->
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts(Constants.PACKAGE, context.packageName, null)
            intent.data = uri
            context.startActivity(intent)
        }
        setNegativeButton(android.R.string.cancel) { dialog, _ ->
            dialog.dismiss()
        }
    }
}