package net.yoedtos.intime.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_register.*
import net.yoedtos.intime.R
import net.yoedtos.intime.model.Validator
import net.yoedtos.intime.model.dto.Register
import net.yoedtos.intime.service.AuthService
import net.yoedtos.intime.service.ResultListener
import net.yoedtos.intime.view.ViewUtils.setupActionBar
import net.yoedtos.intime.view.ViewUtils.setupFullScreen
import net.yoedtos.intime.view.info.ErrorAlert
import net.yoedtos.intime.view.info.GuiNotifier
import net.yoedtos.intime.view.info.Progress

class RegisterActivity : AppCompatActivity() {
    private lateinit var progress: Progress

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setupFullScreen(this)
        setupActionBar(this, toolbar_register_activity)

        progress = Progress(this)
    }

    override fun onResume() {
        btn_register.setOnClickListener {
            val register = Register(et_name.text.toString(), et_email.text.toString(), et_password.text.toString())
            try {
                Validator(resources).validateRegister(register)
                progress.showDefault()
                AuthService.getInstance().register(register, object : ResultListener {
                    override fun onSuccess(any: Any) {
                        startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                        progress.hideView()
                        finish()
                    }

                    override fun onFailure(message: String) {
                        progress.hideView()
                        ErrorAlert(this@RegisterActivity, message).show()
                    }

                })

            } catch (e: IllegalArgumentException) {
                GuiNotifier(this).showErrorSnackBar(e.message.toString())
            }
        }

        super.onResume()
    }
}