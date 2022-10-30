package net.yoedtos.intime.view

import android.view.View
import kotlinx.android.synthetic.main.item_task.view.*
import kotlinx.android.synthetic.main.main_content.*

object ListViewUtils {

    fun showBoardList(activity:MainActivity) {
        activity.rv_boards_list.visibility = View.VISIBLE
        activity.tv_no_boards_available.visibility = View.GONE
    }

    fun hideBoardList(activity: MainActivity) {
        activity.rv_boards_list.visibility = View.GONE
        activity.tv_no_boards_available.visibility = View.VISIBLE
    }

    fun showTaskList(itemView: View) {
        itemView.ll_task_item.visibility = View.VISIBLE
        itemView.tv_add_task_list.visibility = View.GONE
    }

    fun hideTaskList(itemView: View) {
        itemView.tv_add_task_list.visibility = View.VISIBLE
        itemView.ll_task_item.visibility = View.GONE
    }

    fun showAddTaskListInput(itemView: View) {
        itemView.cv_add_task_list_name.visibility = View.VISIBLE
        itemView.tv_add_task_list.visibility = View.GONE
    }

    fun hideAddTaskListInput(itemView: View) {
        itemView.tv_add_task_list.visibility = View.VISIBLE
        itemView.cv_add_task_list_name.visibility = View.GONE
    }

    fun showEditTaskListInput(itemView: View) {
        itemView.cv_edit_task_list_name.visibility = View.VISIBLE
        itemView.ll_title_view.visibility = View.GONE
    }

    fun hideEditTaskListInput(itemView: View) {
        itemView.ll_title_view.visibility = View.VISIBLE
        itemView.cv_edit_task_list_name.visibility = View.GONE
    }

    fun showAddCardListInput(itemView: View) {
        itemView.cv_add_card.visibility = View.VISIBLE
        itemView.tv_add_card.visibility = View.GONE
    }

    fun hideAddCardListInput(itemView: View) {
        itemView.tv_add_card.visibility = View.VISIBLE
        itemView.cv_add_card.visibility = View.GONE
    }

    fun setupAddCloseOnClick(itemView: View) {
        itemView.tv_add_task_list.setOnClickListener{
            showAddTaskListInput(itemView)
        }

        itemView.ib_close_list_name.setOnClickListener {
            hideAddTaskListInput(itemView)
        }

        itemView.ib_close_editable_view.setOnClickListener {
            hideEditTaskListInput(itemView)
        }

        itemView.tv_add_card.setOnClickListener {
            showAddCardListInput(itemView)
        }

        itemView.ib_close_card_name.setOnClickListener {
            hideAddCardListInput(itemView)
        }
    }
}