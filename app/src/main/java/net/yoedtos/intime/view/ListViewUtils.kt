package net.yoedtos.intime.view

import android.graphics.Color
import android.view.View
import kotlinx.android.synthetic.main.activity_card.*
import kotlinx.android.synthetic.main.item_card.view.*
import kotlinx.android.synthetic.main.item_card_member.view.*
import kotlinx.android.synthetic.main.item_label_color.view.*
import kotlinx.android.synthetic.main.item_member.view.*
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

    fun showLabelSelectDone(itemView: View) {
        itemView.iv_selected_color.visibility = View.VISIBLE
    }

    fun hideLabelSelectDone(itemView: View) {
        itemView.iv_selected_color.visibility = View.GONE
    }

    fun showCardLabelColor(itemView: View, color: String) {
        itemView.view_label_color.visibility = View.VISIBLE
        itemView.view_label_color.setBackgroundColor(Color.parseColor(color))
    }

    fun showMemberSelected(itemView: View) {
        itemView.iv_selected_member.visibility = View.VISIBLE
    }

    fun showAddMemberView(itemView: View) {
        itemView.iv_add_member.visibility = View.VISIBLE
        itemView.iv_selected_member_image.visibility = View.GONE
    }

    fun hideAddMemberView(itemView: View) {
        itemView.iv_add_member.visibility = View.GONE
        itemView.iv_selected_member_image.visibility = View.VISIBLE
    }

    fun showCardMembersView(activity: CardActivity) {
        activity.tv_select_members.visibility = View.GONE
        activity.rv_selected_members_list.visibility = View.VISIBLE
    }

    fun hideCardMembersView(activity: CardActivity) {
        activity.tv_select_members.visibility = View.VISIBLE
        activity.rv_selected_members_list.visibility = View.GONE
    }
}