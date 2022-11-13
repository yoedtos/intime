package net.yoedtos.intime.view.info

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.dlg_list.view.*
import net.yoedtos.intime.R
import net.yoedtos.intime.view.listener.OnClickListener

abstract class SelectDialog(context: Context): Dialog(context) {
    var clickListener: OnClickListener? = null

    fun setOnClickListener(clickListener: OnClickListener) {
        this.clickListener = clickListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = LayoutInflater.from(context).inflate(R.layout.dlg_list, null)
        view.rv_dlg_items.layoutManager = LinearLayoutManager(context)

        setContentView(view)
        setCanceledOnTouchOutside(true)
        setCancelable(true)
        setupRecycleView(view)
    }

    protected abstract fun setupRecycleView(view: View)
}