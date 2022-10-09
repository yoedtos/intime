package net.yoedtos.intime.view

import android.view.Gravity
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import kotlinx.android.synthetic.main.activity_main.*
import net.yoedtos.intime.R

object SetUpUtils {
    fun setupFullScreen(activity: AppCompatActivity) {
        activity.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    fun setupActionBar(activity: AppCompatActivity, toolbar: Toolbar) {
        activity.setSupportActionBar(toolbar)
        val actionBar = activity.supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_back_24dp)
            toolbar.setNavigationOnClickListener {
                activity.onBackPressed()
            }
        }
    }

    fun setupActionBarWithNavigation(activity: AppCompatActivity, toolbar: Toolbar) {
        activity.setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_navigation_menu)
        toolbar.setNavigationOnClickListener {
            if(activity.drawer_layout.isDrawerOpen(GravityCompat.START)){
                activity.drawer_layout.closeDrawer(GravityCompat.START)
            } else{
                activity.drawer_layout.openDrawer(GravityCompat.START)
            }
        }
    }
}