package net.yoedtos.intime.view

import android.view.View
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
}