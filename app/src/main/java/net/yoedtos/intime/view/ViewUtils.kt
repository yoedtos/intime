package net.yoedtos.intime.view

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.provider.MediaStore
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_card.*
import kotlinx.android.synthetic.main.activity_main.*
import net.yoedtos.intime.R

object ViewUtils {
    fun showImageChooser(activity: ActivityResultLauncher<Intent>) {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        activity.launch(galleryIntent)
    }

    fun setImageToView(context: Context, imageData: ImageData) {
        Glide
            .with(context)
            .load(imageData.image)
            .centerCrop()
            .placeholder(imageData.resourceId)
            .into(imageData.imageView)
    }

    fun setupFullScreen(activity: AppCompatActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            @Suppress("DEPRECATION")
            activity.window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    fun setupActionBar(activity: AppCompatActivity, toolbar: Toolbar) {
        activity.setSupportActionBar(toolbar)
        val actionBar = activity.supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            toolbar.setNavigationOnClickListener {
                activity.onBackPressed()
            }
        }
    }

    fun setupActionBarWithNavigation(activity: AppCompatActivity, toolbar: Toolbar) {
        activity.setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_navigation_menu)
        toolbar.setNavigationOnClickListener {
            if(!closeNavigationView(activity)){
                activity.drawer_layout.openDrawer(GravityCompat.START)
            }
        }
    }

    fun setupLabelColorInView(activity: AppCompatActivity, color: String) {
        activity.tv_select_label_color.text = ""
        activity.tv_select_label_color.setBackgroundColor(Color.parseColor(color))
    }

     /**
     * Close the navigation view if it's open
     * @return true if it's closed
     */
    fun closeNavigationView(activity: AppCompatActivity):Boolean {
        var closed = false
        if(activity.drawer_layout.isDrawerOpen(GravityCompat.START)){
            activity.drawer_layout.closeDrawer(GravityCompat.START)
            closed = true
        }
        return closed
    }
}