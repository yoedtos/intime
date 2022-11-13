package net.yoedtos.intime.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_tasks.*
import net.yoedtos.intime.R
import net.yoedtos.intime.model.Constants
import net.yoedtos.intime.model.entity.Card
import net.yoedtos.intime.model.entity.Task
import net.yoedtos.intime.service.TasksService
import net.yoedtos.intime.view.ViewUtils.setupActionBar
import net.yoedtos.intime.view.adapter.TaskItemsAdapter
import net.yoedtos.intime.view.listener.ChangeListener
import net.yoedtos.intime.view.listener.ItemClickListener

private val LOG_TAG = TasksActivity::class.java.simpleName

class TasksActivity: AppCompatActivity(), ItemClickListener, ChangeListener {
    private var taskIndex = 0
    private var boardId: String = "0"
    private var taskItemsAdapter: TaskItemsAdapter? = null
    private var taskList: ArrayList<Task> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

        setupActionBar(this, toolbar_task_list_activity)

        rv_task_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_task_list.setHasFixedSize(true)

        initialize()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_members, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_members -> {
                startActivity(Intent(this, MembersActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        Log.d(LOG_TAG, "On start")

        super.onStart()
    }

    override fun onResume() {
        Log.d(LOG_TAG, "On resume")
        taskItemsAdapter?.notifyDataSetChanged()

        super.onResume()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == RequestCode.CARD_VIEW) {
            if (data != null && data.hasExtra(IntentExtra.CARD_LIST)) {
                var cardList: ArrayList<Card> =
                    data.getParcelableArrayListExtra<Card>(IntentExtra.CARD_LIST) as ArrayList<Card>
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
    override fun onClick(position: Int, item: Any) {
        callCardActivity(position, item as ArrayList<Card>)
    }

    override fun onAdd(index: Int, any: Any) {
        taskIndex = index

        updateAndPersistData()
    }

    override fun onUpdate(index: Int, any: Any) {
        taskIndex = index

        updateAndPersistData()
    }

    override fun onDelete(index: Int) {
        taskIndex = index

        updateAndPersistData()
    }
    private fun updateAndPersistData() {
        taskItemsAdapter?.notifyDataSetChanged()

    }

    private fun loadTasksToUI(taskList: ArrayList<Task>) {
        taskItemsAdapter = TaskItemsAdapter(this, taskList)
        rv_task_list.adapter = taskItemsAdapter
        taskItemsAdapter!!.setChangeListener(this)
    }

    private fun initialize() {
        if (intent.hasExtra(IntentExtra.BOARD_ID)) {
            boardId = intent.getStringExtra(IntentExtra.BOARD_ID).toString()
            val boardName = intent.getStringExtra(IntentExtra.BOARD_NAME)

            supportActionBar?.title = boardName

            taskList.add(Task(resources.getString(R.string.add_list), Constants.APPSYS))
            loadTasksToUI(taskList)
        }
    }

    private fun callCardActivity(position: Int, cards: ArrayList<Card>) {
        var cardIntent = Intent(this, CardActivity::class.java)
        cardIntent.putExtra(IntentExtra.CARD_INDEX, position)
        cardIntent.putParcelableArrayListExtra(IntentExtra.CARD_LIST, cards)
        startActivityForResult(cardIntent, RequestCode.CARD_VIEW)
    }
}