package net.yoedtos.intime.view.adapter

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_task.view.*
import net.yoedtos.intime.R
import net.yoedtos.intime.model.entity.Card
import net.yoedtos.intime.model.entity.Task
import net.yoedtos.intime.view.ListViewUtils.hideAddCardListInput
import net.yoedtos.intime.view.ListViewUtils.hideAddTaskListInput
import net.yoedtos.intime.view.ListViewUtils.hideEditTaskListInput
import net.yoedtos.intime.view.ListViewUtils.hideTaskList
import net.yoedtos.intime.view.ListViewUtils.setupAddCloseOnClick
import net.yoedtos.intime.view.ListViewUtils.showEditTaskListInput
import net.yoedtos.intime.view.ListViewUtils.showTaskList
import net.yoedtos.intime.view.TasksActivity
import net.yoedtos.intime.view.info.DeleteAlert
import net.yoedtos.intime.view.info.ItemViewHolder
import net.yoedtos.intime.view.listener.ChangeListener

class TaskItemsAdapter(private val context: Context, private var taskList:ArrayList<Task>): RecyclerView.Adapter<ItemViewHolder>() {
    private var changeListener: ChangeListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        val layoutParams = LinearLayout.LayoutParams((parent.width * 0.95).toInt(), LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins((15.toDp()).toPx(), 0, (30.toDp()).toPx(), 0)
        view.layoutParams = layoutParams

        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val itemView = holder.itemView

        var task = taskList[position]

        if (position == taskList.size - 1) {
            hideTaskList(itemView)
        } else {
            showTaskList(itemView)
        }

        setupAddCloseOnClick(itemView)
        setupCardListView(position, itemView, task.cards)

        itemView.tv_task_list_title.text = task.title

        itemView.ib_done_list_name.setOnClickListener {
            val title = itemView.et_task_list_name.text.toString()
            changeListener?.onAdd(position, title)

            hideAddTaskListInput(itemView)
        }

        itemView.ib_edit_list_name.setOnClickListener {
            itemView.et_edit_task_list_name.setText(task.title)

            showEditTaskListInput(itemView)
        }

        itemView.ib_done_edit_list_name.setOnClickListener {
            task.title = itemView.et_edit_task_list_name.text.toString()
            changeListener?.onUpdate(position, task)

            hideEditTaskListInput(itemView)
        }

        itemView.ib_delete_list.setOnClickListener {
            deleteWithAlert(position, task.title)
        }

        itemView.ib_done_card_name.setOnClickListener {
            val cardName = itemView.et_card_name.text.toString()
            changeListener?.onUpdate(position, cardName)

            hideAddCardListInput(itemView)
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    fun setChangeListener(changeListener: ChangeListener) {
        this.changeListener = changeListener
    }

    private fun setupCardListView(position: Int, itemView: View, cardList: ArrayList<Card>) {
        itemView.rv_card_list.layoutManager = LinearLayoutManager(itemView.context)
        itemView.rv_card_list.setHasFixedSize(true)
        val adapter = CardItemsAdapter(cardList)
        itemView.rv_card_list.adapter = adapter

        adapter.setTaskIndex(position)
        adapter.setOnClickListener(context as TasksActivity)
    }
    /**
     * Convert screen pixel to density pixel
     * @return dp in Int
     */
    private fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()
    /**
     * Convert screen density pixel to pixel
     * @return  px in Int
     */
    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

    private fun deleteWithAlert(position: Int, taskName: String) {
        DeleteAlert(context, taskName)
            .setPositiveButton(R.string.alert_yes) {dialogInterface, _ ->
                changeListener?.onDelete(position)
                dialogInterface.dismiss()
            }.create().show()
    }
}