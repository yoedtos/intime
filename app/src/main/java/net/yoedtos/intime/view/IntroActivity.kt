package net.yoedtos.intime.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_intro.*
import net.yoedtos.intime.R
import net.yoedtos.intime.service.AuthService
import net.yoedtos.intime.view.ViewUtils.setupFullScreen

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        setupFullScreen(this)

        initialize()

        btn_login_intro.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        btn_register_intro.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun initialize() {
        if (AuthService.getInstance().isAuthenticated()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}