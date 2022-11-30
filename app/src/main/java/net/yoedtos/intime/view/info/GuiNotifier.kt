package net.yoedtos.intime.view.info

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import net.yoedtos.intime.R

class GuiNotifier(val activity: AppCompatActivity) {

    fun showErrorSnackBar(message: String) {
        val snackbar = Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundColor(ContextCompat.getColor(activity, R.color.snackbar_error_color))
        snackbar.show()
    }

    fun showToast(id: Int, value: String) {
        Toast.makeText(activity, activity.resources.getString(id, value), Toast.LENGTH_LONG).show()
    }
}