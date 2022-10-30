package net.yoedtos.intime.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_tasks.*
import net.yoedtos.intime.R
import net.yoedtos.intime.model.entity.Board
import net.yoedtos.intime.model.entity.Task
import net.yoedtos.intime.view.ViewUtils.setupActionBar
import net.yoedtos.intime.view.adapter.TaskItemsAdapter

private val LOG_TAG = TasksActivity::class.java.simpleName

class TasksActivity : AppCompatActivity() {
    private var taskItemsAdapter: TaskItemsAdapter? = null
    private lateinit var board: Board

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

        setupActionBar(this, toolbar_task_list_activity)

        rv_task_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_task_list.setHasFixedSize(true)
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
    override fun onResume() {
        Log.d(LOG_TAG, "On resume")

        super.onResume()
    }
    fun updateAdapterData() {
        taskItemsAdapter?.notifyDataSetChanged()
    }

    private fun loadTasksToUI(taskList: ArrayList<Task>) {
        taskItemsAdapter = TaskItemsAdapter(this, taskList)
        rv_task_list.adapter = taskItemsAdapter
    }

}