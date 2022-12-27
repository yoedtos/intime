package net.yoedtos.intime.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_board.*
import net.yoedtos.intime.R
import net.yoedtos.intime.model.Constants
import net.yoedtos.intime.model.Validator
import net.yoedtos.intime.model.dto.BoardDTO
import net.yoedtos.intime.service.BoardService
import net.yoedtos.intime.service.ResultListener
import net.yoedtos.intime.utils.PermissionHelper
import net.yoedtos.intime.view.ViewUtils.setImageToView
import net.yoedtos.intime.view.ViewUtils.setupActionBar
import net.yoedtos.intime.view.ViewUtils.showImageChooser
import net.yoedtos.intime.view.info.ErrorAlert
import net.yoedtos.intime.view.info.GuiNotifier
import net.yoedtos.intime.view.info.PermissionAlert
import net.yoedtos.intime.view.info.Progress

private val LOG_TAG = BoardActivity::class.java.simpleName

class BoardActivity : AppCompatActivity() {
    private lateinit var boardDTO: BoardDTO
    private lateinit var boardService: BoardService
    private lateinit var progress: Progress

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        setupActionBar(this, toolbar_board_activity)
        progress = Progress(this)

        initialize()
    }
    override fun onResume() {
        Log.d(LOG_TAG, "On resume")
        iv_board_image.setOnClickListener{
            if(PermissionHelper(this).isGranted(Constants.READ_STORAGE)) {
                showImageChooser(this)
            } else {
                PermissionAlert(this).show()
            }
        }
        super.onResume()

        btn_create.setOnClickListener {
            boardDTO.name = et_board_name.text.toString()

            try {
                Validator(resources).validateBoard(boardDTO)
                progress.showDefault()
                boardService.create(boardDTO, object : ResultListener {
                    override fun onSuccess(any: Any) {
                        progress.hideView()
                        GuiNotifier(this@BoardActivity).showToast(R.string.created, boardDTO.name)
                        finish()
                    }

                    override fun onFailure(message: String) {
                        progress.hideView()
                        ErrorAlert(this@BoardActivity, message).show()
                    }

                })

            } catch (e: IllegalArgumentException) {
                GuiNotifier(this).showErrorSnackBar(e.message.toString())
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (intent != null) {
            if (resultCode == Activity.RESULT_OK
                && requestCode == RequestCode.PICK_IMAGE_CODE
                && intent.data != null) {

                val image: String = Uri.parse(intent.data.toString()).toString()
                boardDTO.image = image
                setImageToView(this, ImageData(image, R.drawable.ic_board_place_holder, iv_board_image))
            }
        }
    }

    private fun initialize() {
        boardDTO = BoardDTO()
        boardService = BoardService()
    }
}