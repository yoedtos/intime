package net.yoedtos.intime.view.info

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.dlg_search_member.*
import net.yoedtos.intime.R
import net.yoedtos.intime.view.MembersActivity

class SearchDialog(activity: AppCompatActivity):Dialog(activity) {

    init {
        super.setContentView(R.layout.dlg_search_member)
        this.tv_add.setOnClickListener {
            val memberEmail = this.et_email_search_member.text.toString()
            if (memberEmail.isNotEmpty()) {
                this.dismiss()
                if (activity is MembersActivity)
                    activity.searchMember(memberEmail)
            } else {
                // TODO show validation error
            }
        }
        this.tv_cancel.setOnClickListener {
            this.dismiss()
        }
    }
}