package net.yoedtos.intime.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_board.*
import net.yoedtos.intime.R
import net.yoedtos.intime.model.Validator
import net.yoedtos.intime.model.dto.BoardDto
import net.yoedtos.intime.service.BoardService
import net.yoedtos.intime.service.UserService
import net.yoedtos.intime.view.ViewUtils.setImageToView
import net.yoedtos.intime.view.ViewUtils.setupActionBar
import net.yoedtos.intime.view.ViewUtils.showImageChooser
import net.yoedtos.intime.view.info.GuiNotifier

private val LOG_TAG = BoardActivity::class.java.simpleName

class BoardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        setupActionBar(this, toolbar_board_activity)
    }

    override fun onResume() {
        Log.d(LOG_TAG, "On resume")
        iv_board_image.setOnClickListener{
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

                setImageToView(this, ImageData(image, R.drawable.ic_board_place_holder, iv_board_image))
            }
        }
    }
}