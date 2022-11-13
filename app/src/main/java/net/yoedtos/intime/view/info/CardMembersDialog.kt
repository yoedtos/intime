package net.yoedtos.intime.view.info

import android.content.Context
import android.view.View
import kotlinx.android.synthetic.main.dlg_list.view.*
import net.yoedtos.intime.R
import net.yoedtos.intime.view.adapter.MemberItemsAdapter
import net.yoedtos.intime.view.listener.ItemClickListener

class CardMembersDialog(context: Context, private val memberItemsAdapter: MemberItemsAdapter): SelectDialog(context) {

    override fun setupRecycleView(view: View) {
        view.tv_dlg_title.text = context.getText(R.string.select_members)
        view.rv_dlg_items.adapter = memberItemsAdapter

        memberItemsAdapter.setItemClickListener(object: ItemClickListener {
            override fun onClick(position: Int, item: Any) {
                clickListener!!.onClick(item)
                dismiss()
            }
        })
    }
}