package net.yoedtos.intime.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import net.yoedtos.intime.R
import net.yoedtos.intime.view.ViewUtils.closeNavigationView
import net.yoedtos.intime.view.ViewUtils.setupActionBarWithNavigation
import net.yoedtos.intime.view.listener.NavigationListener

private val LOG_TAG = MainActivity::class.java.simpleName

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBarWithNavigation(this, toolbar_main_activity)
        nav_view.setNavigationItemSelectedListener(NavigationListener(this))
    }

    override fun onResume() {
        Log.i(LOG_TAG, "On resume")
        fab_create_board.setOnClickListener{
            startActivity(Intent(this, BoardActivity::class.java))
        }
        super.onResume()
    }
    override fun onBackPressed() {
        if (!closeNavigationView(this)) {
            super.onBackPressed()
        }
    }
}