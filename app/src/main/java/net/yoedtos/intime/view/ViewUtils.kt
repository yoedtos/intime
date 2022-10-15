package net.yoedtos.intime.view

import android.content.Intent
import android.provider.MediaStore
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import net.yoedtos.intime.R

object ViewUtils {
    fun showImageChooser(activity: AppCompatActivity) {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        activity.startActivityForResult(galleryIntent, RequestCode.PICK_IMAGE_CODE)
    }

    fun setImageToView(activity: AppCompatActivity, imageData: ImageData) {
        Glide
            .with(activity)
            .load(imageData.image)
            .centerCrop()
            .placeholder(imageData.resourceId)
            .into(imageData.imageView)
    }

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