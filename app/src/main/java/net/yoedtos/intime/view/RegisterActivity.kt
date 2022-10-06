package net.yoedtos.intime.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_register.*
import net.yoedtos.intime.R
import net.yoedtos.intime.view.SetUpUtils.setupActionBar
import net.yoedtos.intime.view.SetUpUtils.setupFullScreen

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setupFullScreen(this)
        setupActionBar(this, toolbar_register_activity)
    }
}