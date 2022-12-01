package net.yoedtos.intime.utils

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

private val LOG_TAG = PermissionHelper::class.simpleName

class PermissionHelper(val context: Context) {

    fun isGranted(permission: String): Boolean {
        if(ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
            return true
        }
        return false
    }

    fun checkPermission(permission: String) {
        Dexter.withContext(context)
            .withPermission(permission)
            .withListener(object: PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    Log.d(LOG_TAG, "Permission Granted")
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    Log.d(LOG_TAG, "Permission Denied")
                }

                override fun onPermissionRationaleShouldBeShown(
                    request: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    Log.d(LOG_TAG, "Show Rational Alert")
                    token?.continuePermissionRequest()
                }

            })
            .withErrorListener { error ->
                Log.e(LOG_TAG, error.toString())
            }
            .onSameThread()
            .check()
    }
}