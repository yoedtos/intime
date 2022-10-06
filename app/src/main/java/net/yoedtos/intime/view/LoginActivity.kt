package net.yoedtos.intime.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import net.yoedtos.intime.R
import net.yoedtos.intime.view.SetUpUtils.setupActionBar
import net.yoedtos.intime.view.SetUpUtils.setupFullScreen

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setupFullScreen(this)
        setupActionBar(this, toolbar_login_activity)

    }
}