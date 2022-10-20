package net.yoedtos.intime.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_board.view.*
import net.yoedtos.intime.R
import net.yoedtos.intime.model.entity.Board
import net.yoedtos.intime.view.ImageData
import net.yoedtos.intime.view.ViewUtils
import net.yoedtos.intime.view.ViewUtils.setImageToView
import net.yoedtos.intime.view.info.ItemViewHolder
import net.yoedtos.intime.view.listener.ItemClickListener

class BoardItemsAdapter(private val items: ArrayList<Board>): RecyclerView.Adapter<ItemViewHolder>() {

    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_board, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val board = items[position]

        setImageToView(holder.itemView.context, ImageData(board.image, R.drawable.ic_board_place_holder, holder.itemView.iv_board_image))

        holder.itemView.tv_name.text = board.name
        holder.itemView.tv_created_by.text = board.createdBy

        holder.itemView.setOnClickListener{
            if (clickListener != null) {
                clickListener!!.onClick(position, board)
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