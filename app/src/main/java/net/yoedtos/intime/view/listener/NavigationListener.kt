package net.yoedtos.intime.view.listener

import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import kotlinx.android.synthetic.main.activity_main.*
import net.yoedtos.intime.R

class NavigationListener(private val activity: AppCompatActivity): OnNavigationItemSelectedListener {
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_my_profile -> {
                TODO("implement profile screen call")
            }
            R.id.nav_logout -> {
               TODO("implement user logout call")
            }
        }
        activity.drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}