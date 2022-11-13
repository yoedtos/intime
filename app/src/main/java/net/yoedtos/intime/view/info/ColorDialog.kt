package net.yoedtos.intime.view.info

import android.content.Context
import android.view.View
import kotlinx.android.synthetic.main.dlg_list.view.*
import net.yoedtos.intime.R
import net.yoedtos.intime.view.adapter.ColorItemsAdapter
import net.yoedtos.intime.view.listener.OnClickListener

class ColorDialog(context: Context, private val selectedColor: String): SelectDialog(context) {

    override fun setupRecycleView(view: View) {
        view.tv_dlg_title.text = context.getText(R.string.select_label_color)

        var colors = context.resources.getStringArray(R.array.label_color_array)
        var colorsAdapter = ColorItemsAdapter(colors, selectedColor)

        view.rv_dlg_items.adapter = colorsAdapter

        colorsAdapter.setOnClickListener(object: OnClickListener {
            override fun onClick(item: Any) {
                clickListener!!.onClick(item)
                dismiss()
            }
        })
    }
}