package net.yoedtos.intime.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.main_content.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import net.yoedtos.intime.R
import net.yoedtos.intime.model.Constants
import net.yoedtos.intime.model.dto.BoardDTO
import net.yoedtos.intime.model.dto.UserDTO
import net.yoedtos.intime.service.AuthService
import net.yoedtos.intime.service.BoardService
import net.yoedtos.intime.service.ResultListener
import net.yoedtos.intime.service.UserService
import net.yoedtos.intime.utils.CacheHandler
import net.yoedtos.intime.utils.PermissionHelper
import net.yoedtos.intime.view.ListViewUtils.hideBoardList
import net.yoedtos.intime.view.ListViewUtils.showBoardList
import net.yoedtos.intime.view.ViewUtils.closeNavigationView
import net.yoedtos.intime.view.ViewUtils.setImageToView
import net.yoedtos.intime.view.ViewUtils.setupActionBarWithNavigation
import net.yoedtos.intime.view.adapter.BoardItemsAdapter
import net.yoedtos.intime.view.info.ErrorAlert
import net.yoedtos.intime.view.info.Progress
import net.yoedtos.intime.view.listener.ItemClickListener
import net.yoedtos.intime.view.listener.NavigationListener

private val LOG_TAG = MainActivity::class.java.simpleName

class MainActivity : AppCompatActivity(), ItemClickListener {
    private var boardItemsAdapter: BoardItemsAdapter? = null
    private val boardService = BoardService()
    private lateinit var progress: Progress

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBarWithNavigation(this, toolbar_main_activity)
        nav_view.setNavigationItemSelectedListener(NavigationListener(this))
        rv_boards_list.layoutManager = LinearLayoutManager(this)
        rv_boards_list.setHasFixedSize(true)

        PermissionHelper(this).checkPermission(Constants.READ_STORAGE)
        progress = Progress(this)
    }

    override fun onResume() {
        Log.i(LOG_TAG, "On resume")
        fab_create_board.setOnClickListener{
            startActivity(Intent(this, BoardActivity::class.java))
        }

        initialize()
        loadUserData()
        super.onResume()
    }
    override fun onBackPressed() {
        if (!closeNavigationView(this)) {
            super.onBackPressed()
        }
    }

    override fun onClick(position: Int, item: Any) {
        callTasksActivity(item as BoardDTO)
    }

    private fun initialize() {
        progress.showDefault()
        boardService.loadBoards(object: ResultListener {
            override fun onSuccess(any: Any) {
                progress.hideView()
                loadBoardsToUI(any as ArrayList<BoardDTO>)
            }

            override fun onFailure(message: String) {
                progress.hideView()
                ErrorAlert(this@MainActivity, message).show()
            }
        })
    }

    private fun loadBoardsToUI(boardList: ArrayList<BoardDTO>) {
        if (boardList.size > 0) {
            showBoardList(this)
            boardItemsAdapter = BoardItemsAdapter(boardList)
            boardItemsAdapter?.setOnClickListener(this)
            rv_boards_list.adapter = boardItemsAdapter
        } else {
            hideBoardList(this)
        }
    }

    private fun loadUserData() {
        val userId = AuthService.getInstance().getCurrentUserID()
        val userDTO: UserDTO

        try {
            userDTO = CacheHandler(this).loadObject(UserDTO::class.java) as UserDTO
            setNavigationData(userDTO)
        } catch (e: IllegalStateException) {
            loadFromService(userId)
        }
    }

    private fun loadFromService(userId: String) {
        UserService().loadUser(userId, object : ResultListener {
            override fun onSuccess(any: Any) {
                setNavigationData(any as UserDTO)
            }

            override fun onFailure(message: String) {
                ErrorAlert(this@MainActivity, message).show()
            }

        })
    }

    private fun setNavigationData(userDTO: UserDTO) {
        val headerView = nav_view.getHeaderView(0)
        headerView.tv_username.text = userDTO.name
        setImageToView(
            this,
            ImageData(userDTO.image, R.drawable.ic_user_place_holder, headerView.iv_user_image)
        )
    }

    private fun callTasksActivity(boardDTO: BoardDTO) {
        val intent = Intent(this@MainActivity, TasksActivity::class.java)
        intent.putExtra(IntentExtra.BOARD_ID, boardDTO.id)
        intent.putExtra(IntentExtra.BOARD_NAME, boardDTO.name)
        startActivity(intent)
    }
}