package net.yoedtos.intime.view

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_profile.*
import net.yoedtos.intime.R
import net.yoedtos.intime.model.Validator
import net.yoedtos.intime.model.dto.UserDTO
import net.yoedtos.intime.service.AuthService
import net.yoedtos.intime.service.ResultListener
import net.yoedtos.intime.service.UserService
import net.yoedtos.intime.view.ViewUtils.setImageToView
import net.yoedtos.intime.view.ViewUtils.setupActionBar
import net.yoedtos.intime.view.ViewUtils.showImageChooser
import net.yoedtos.intime.view.info.ErrorAlert
import net.yoedtos.intime.view.info.GuiNotifier
import net.yoedtos.intime.view.info.Progress

private val LOG_TAG = ProfileActivity::class.java.simpleName

class ProfileActivity : AppCompatActivity() {

    private lateinit var userDTO: UserDTO
    private lateinit var userService: UserService
    private lateinit var progress: Progress

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            if (intent != null) {
                val image: String = Uri.parse(intent.data.toString()).toString()
                userDTO.image = image
                setImageToView(this, ImageData(image, R.drawable.ic_user_place_holder, iv_user_image))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setupActionBar(this, toolbar_profile_activity)

        progress = Progress(this)
        initialize()
    }

    override fun onResume() {
        Log.d(LOG_TAG, "On resume")
        iv_user_image.setOnClickListener{
            showImageChooser(startForResult)
        }

        btn_update.setOnClickListener {
            userDTO.name = et_name.text.toString()
            userDTO.mobile = et_mobile.text.toString()

            try {
                Validator(resources).validateUser(userDTO)
                progress.showDefault()
                userService.update(userDTO, object : ResultListener {
                    override fun onSuccess(any: Any) {
                        progress.hideView()
                        GuiNotifier(this@ProfileActivity).showToast(R.string.updated, userDTO.name)
                        finish()
                    }

                    override fun onFailure(message: String) {
                        progress.hideView()
                        ErrorAlert(this@ProfileActivity, message).show()
                    }

                })
            } catch (e: IllegalArgumentException) {
                GuiNotifier(this).showErrorSnackBar(e.message.toString())
            }

        }
        super.onResume()
    }
    private fun initialize() {
        userService = UserService()
        val userId = AuthService.getInstance().getCurrentUserID()
        userService.loadUser(userId, object : ResultListener {
            override fun onSuccess(any: Any) {
                userDTO = any as UserDTO
                setUserData(userDTO)
            }

            override fun onFailure(message: String) {
                ErrorAlert(this@ProfileActivity, message).show()
            }

        })
    }

    private fun setUserData(userDTO: UserDTO) {
        et_name.setText(userDTO.name)
        et_email.setText(userDTO.email)
        et_mobile.setText(userDTO.mobile)

        setImageToView(
            this@ProfileActivity,
            ImageData(userDTO.image, R.drawable.ic_user_place_holder, iv_user_image)
        )
    }
}