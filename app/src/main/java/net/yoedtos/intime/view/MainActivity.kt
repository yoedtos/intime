package net.yoedtos.intime.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.main_content.*
import net.yoedtos.intime.R
import net.yoedtos.intime.model.entity.Board
import net.yoedtos.intime.view.ListViewUtils.hideBoardList
import net.yoedtos.intime.view.ListViewUtils.showBoardList
import net.yoedtos.intime.view.ViewUtils.closeNavigationView
import net.yoedtos.intime.view.ViewUtils.setupActionBarWithNavigation
import net.yoedtos.intime.view.adapter.BoardItemsAdapter
import net.yoedtos.intime.view.listener.NavigationListener

private val LOG_TAG = MainActivity::class.java.simpleName

class MainActivity : AppCompatActivity() {
    private var boardItemsAdapter: BoardItemsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBarWithNavigation(this, toolbar_main_activity)
        nav_view.setNavigationItemSelectedListener(NavigationListener(this))
        rv_boards_list.layoutManager = LinearLayoutManager(this)
        rv_boards_list.setHasFixedSize(true)
    }

    override fun onResume() {
        Log.i(LOG_TAG, "On resume")
        fab_create_board.setOnClickListener{
            startActivity(Intent(this, BoardActivity::class.java))
        }
        super.onResume()
    }
    override fun onBackPressed() {
        if (!closeNavigationView(this)) {
            super.onBackPressed()
        }
    }

    private fun loadBoardsToUI(boardList: ArrayList<Board>) {
        if (boardList.size > 0) {
            showBoardList(this)
            boardItemsAdapter = BoardItemsAdapter(boardList)
            rv_boards_list.adapter = boardItemsAdapter
        } else {
            hideBoardList(this)
        }
    }
}