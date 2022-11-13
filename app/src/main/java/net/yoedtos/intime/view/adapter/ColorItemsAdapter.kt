package net.yoedtos.intime.view.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_label_color.view.*
import net.yoedtos.intime.R
import net.yoedtos.intime.view.ListViewUtils.hideLabelSelectDone
import net.yoedtos.intime.view.ListViewUtils.showLabelSelectDone
import net.yoedtos.intime.view.info.ItemViewHolder
import net.yoedtos.intime.view.listener.OnClickListener

class ColorItemsAdapter(private val colors: Array<String>, private val selectedColor: String): RecyclerView.Adapter<ItemViewHolder>() {

    private var clickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_label_color, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val color = colors[position]

        holder.itemView.view_main.setBackgroundColor(Color.parseColor(color))

        if(color == selectedColor) {
            showLabelSelectDone(holder.itemView)
        } else {
            hideLabelSelectDone(holder.itemView)
        }

        holder.itemView.setOnClickListener{
            if (clickListener != null) {
                clickListener!!.onClick(color)
            }
        }
    }

    override fun getItemCount(): Int {
        return colors.size
    }

    fun setOnClickListener(clickListener: OnClickListener) {
        this.clickListener = clickListener
    }
}