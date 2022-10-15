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

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (intent != null) {
            if (resultCode == Activity.RESULT_OK
                                && requestCode == RequestCode.PICK_IMAGE_CODE
                                && intent.data != null) {

                val image: String = Uri.parse(intent.data.toString()).toString()

                setImageToView(this, ImageData(image, R.drawable.ic_user_place_holder, iv_user_image))
            }
        }
    }

}