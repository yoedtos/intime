package net.yoedtos.intime.view.info

import android.app.Dialog
import android.content.Context
import kotlinx.android.synthetic.main.view_progress.*
import net.yoedtos.intime.R

class Progress(context: Context):Dialog(context) {

    init {
        super.setContentView(R.layout.view_progress)
        this.tv_progress_text.text = context.getString(R.string.please_wait)
    }

    fun showDefault() {
        super.show()
    }
    fun showView(text: String) {
        this.tv_progress_text.text = text
        super.show()
    }

    fun hideView() {
        super.dismiss()
    }
}
