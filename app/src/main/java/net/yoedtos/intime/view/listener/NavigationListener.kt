package net.yoedtos.intime.view.listener

import android.content.Intent
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import kotlinx.android.synthetic.main.activity_main.*
import net.yoedtos.intime.R
import net.yoedtos.intime.service.AuthService
import net.yoedtos.intime.view.IntroActivity
import net.yoedtos.intime.view.ProfileActivity

class NavigationListener(private val activity: AppCompatActivity) : OnNavigationItemSelectedListener {
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_my_profile -> {
                activity.startActivity(Intent(activity, ProfileActivity::class.java))
            }
            R.id.nav_logout -> {
                logout()
            }
        }
        activity.drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun logout() {
        AuthService.getInstance().logout()
        val intent = Intent(activity, IntroActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        activity.startActivity(intent)
        activity.finish()
    }
}