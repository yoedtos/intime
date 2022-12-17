package net.yoedtos.intime.view

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.activity_splash.view.*
import net.yoedtos.intime.R
import net.yoedtos.intime.view.ViewUtils.setupFullScreen

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setupFullScreen(this)

        val typeFace = Typeface.createFromAsset(assets, "tex-gyre-adventor.otf")
        tv_app_name.tv_app_name.typeface = typeFace

        Handler(mainLooper).postDelayed({
            startActivity(Intent(this, IntroActivity::class.java))
            finish()
        }, 2000)
    }
}