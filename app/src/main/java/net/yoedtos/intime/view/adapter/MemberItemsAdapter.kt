package net.yoedtos.intime.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_member.view.*
import net.yoedtos.intime.R
import net.yoedtos.intime.model.entity.User
import net.yoedtos.intime.view.ImageData
import net.yoedtos.intime.view.ViewUtils.setImageToView
import net.yoedtos.intime.view.info.ItemViewHolder
import net.yoedtos.intime.view.listener.ItemClickListener

class MemberItemsAdapter(private val items: ArrayList<User>): RecyclerView.Adapter<ItemViewHolder>() {

    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_member, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val user = items[position]

        holder.itemView.tv_member_name.text = user.name
        holder.itemView.tv_member_email.text = user.email

        setImageToView(holder.itemView.context,
            ImageData(user.image, R.drawable.ic_user_place_holder, holder.itemView.iv_member_image)
        )

        holder.itemView.setOnClickListener{
            if (clickListener != null) {
                // TODO("Not yet implemented")
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