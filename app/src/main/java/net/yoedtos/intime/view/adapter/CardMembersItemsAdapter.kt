package net.yoedtos.intime.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_card_member.view.*
import net.yoedtos.intime.R
import net.yoedtos.intime.model.dto.Member
import net.yoedtos.intime.view.ImageData
import net.yoedtos.intime.view.ListViewUtils.hideAddMemberView
import net.yoedtos.intime.view.ListViewUtils.showAddMemberView
import net.yoedtos.intime.view.ViewUtils.setImageToView
import net.yoedtos.intime.view.info.ItemViewHolder
import net.yoedtos.intime.view.listener.ItemClickListener

class CardMembersItemsAdapter(private val items: ArrayList<Member>): RecyclerView.Adapter<ItemViewHolder>() {

    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_card_member, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val itemView = holder.itemView
        val member = items[position]

        if (position == items.size - 1) {
            showAddMemberView(itemView)
        } else {
            hideAddMemberView(itemView)
        }

        setImageToView(
            itemView.context,
            ImageData(member.image, R.drawable.ic_user_place_holder, itemView.iv_selected_member_image)
        )

        itemView.setOnClickListener{
            if (clickListener != null) {
                clickListener!!.onClick(position, member)
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