package net.yoedtos.intime.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_card.view.*
import net.yoedtos.intime.R
import net.yoedtos.intime.model.entity.Card
import net.yoedtos.intime.view.ListViewUtils.showCardLabelColor
import net.yoedtos.intime.view.info.ItemViewHolder
import net.yoedtos.intime.view.listener.ItemClickListener

class CardItemsAdapter(private val items: ArrayList<Card>): RecyclerView.Adapter<ItemViewHolder>() {

    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val itemView = holder.itemView

        val card = items[position]

        if(card.labelColor.isNotEmpty()) {
            showCardLabelColor(itemView, card.labelColor)
        }

        itemView.tv_card_name.text = card.name

        itemView.setOnClickListener{
            if (clickListener != null) {
                clickListener!!.onClick(position, items)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setOnClickListener(clickListener: ItemClickListener) {
        this.clickListener = clickListener
    }
}