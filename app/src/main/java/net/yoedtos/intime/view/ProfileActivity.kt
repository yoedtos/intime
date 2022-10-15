package net.yoedtos.intime.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_profile.*
import net.yoedtos.intime.R
import net.yoedtos.intime.view.ViewUtils.setImageToView
import net.yoedtos.intime.view.ViewUtils.setupActionBar
import net.yoedtos.intime.view.ViewUtils.showImageChooser

private val LOG_TAG = ProfileActivity::class.java.simpleName

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setupActionBar(this, toolbar_profile_activity)
    }

    override fun onResume() {
        Log.d(LOG_TAG, "On resume")
        iv_user_image.setOnClickListener{
            showImageChooser(this)
        }
        super.onResume()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (resultCode == Activity.RESULT_OK
                                && requestCode == RequestCode.PICK_IMAGE_CODE
                                && data.data != null) {

                val image: Uri = Uri.parse(data.data.toString())
                setImageToView(this, image.toString())
            }
        }
    }

}