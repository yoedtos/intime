package net.yoedtos.intime.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_profile.*
import net.yoedtos.intime.R
import net.yoedtos.intime.view.ViewUtils.setupActionBar

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setupActionBar(this, toolbar_profile_activity)
    }
}