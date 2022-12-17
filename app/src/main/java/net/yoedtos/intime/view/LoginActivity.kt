package net.yoedtos.intime.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import net.yoedtos.intime.R
import net.yoedtos.intime.model.Validator
import net.yoedtos.intime.model.dto.Login
import net.yoedtos.intime.service.AuthService
import net.yoedtos.intime.service.ResultListener
import net.yoedtos.intime.view.ViewUtils.setupActionBar
import net.yoedtos.intime.view.ViewUtils.setupFullScreen
import net.yoedtos.intime.view.info.ErrorAlert
import net.yoedtos.intime.view.info.GuiNotifier
import net.yoedtos.intime.view.info.Progress

class LoginActivity : AppCompatActivity() {
    private lateinit var progress: Progress

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setupFullScreen(this)
        setupActionBar(this, toolbar_login_activity)
        progress = Progress(this)
    }

    override fun onResume() {
        btn_login.setOnClickListener {
            val login = Login(et_email.text.toString(), et_password.text.toString())
            try {
                Validator(resources).validateLogin(login)
                progress.showDefault()
                AuthService.getInstance().login(login, object : ResultListener {
                    override fun onSuccess(any: Any) {
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        progress.hideView()
                        finish()
                    }

                    override fun onFailure(message: String) {
                        progress.hideView()
                        ErrorAlert(this@LoginActivity, message).show()
                    }

                })

            } catch (e: IllegalArgumentException) {
                GuiNotifier(this).showErrorSnackBar(e.message.toString())
            }
        }

        super.onResume()
    }
}