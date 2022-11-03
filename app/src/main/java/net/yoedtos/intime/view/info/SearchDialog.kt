package net.yoedtos.intime.view.info

import android.app.Activity
import android.app.Dialog
import kotlinx.android.synthetic.main.dlg_search_member.*
import net.yoedtos.intime.R

class SearchDialog(activity: Activity):Dialog(activity) {

    init {
        super.setContentView(R.layout.dlg_search_member)
        this.tv_cancel.setOnClickListener {
            this.dismiss()
        }
    }
}