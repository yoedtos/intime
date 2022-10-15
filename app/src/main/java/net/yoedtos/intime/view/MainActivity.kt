package net.yoedtos.intime.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import net.yoedtos.intime.R
import net.yoedtos.intime.view.ViewUtils.closeNavigationView
import net.yoedtos.intime.view.ViewUtils.setupActionBarWithNavigation
import net.yoedtos.intime.view.info.Progress
import net.yoedtos.intime.view.listener.NavigationListener

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBarWithNavigation(this, toolbar_main_activity)
        nav_view.setNavigationItemSelectedListener(NavigationListener(this))
    }

    override fun onBackPressed() {
        if (!closeNavigationView(this)) {
            super.onBackPressed()
        }
    }
}