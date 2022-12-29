package net.yoedtos.intime.service

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import net.yoedtos.intime.InTimeApp
import net.yoedtos.intime.client.Document
import net.yoedtos.intime.client.FileBaseStore
import net.yoedtos.intime.model.entity.Board
import net.yoedtos.intime.model.entity.Card
import net.yoedtos.intime.model.entity.Task
import net.yoedtos.intime.service.base.StoreProvider
import net.yoedtos.intime.utils.CacheHandler

private val LOG_TAG = TasksService::class.simpleName

class TasksService(private val boardId: String){
    private lateinit var userName: String
    private val context = InTimeApp.instance?.applicationContext
    private var taskList: ArrayList<Task> = ArrayList()
    private val storeProvider: StoreProvider

    init {
        if (context != null) {
            CacheHandler(context).save(Document.BOARD_ID, boardId)
            userName = CacheHandler(context).load(Document.USER_NAME)
        }
        storeProvider = FileBaseStore(Document.BOARDS)
    }

    fun addTask(title: String){
        taskList.add(0, Task(title = title, createdBy = userName))
    }

    fun updateTask(index: Int, task: Task) {
        taskList[index] = task
    }

    fun deleteTask(index: Int) {
        taskList.removeAt(index)
    }

    fun addCardToTask(index: Int, cardName: String) {
        taskList[index].cards.add(Card(name = cardName, createdBy = userName))
    }

    fun addCards(index: Int, cards: ArrayList<Card>) {
        taskList[index].cards = cards
    }

    fun updatePersist(resultListener: ResultListener) {
        val tasks = ArrayList<Task>(taskList)
        tasks.removeLast()
        persist(tasks, resultListener)
    }

    private fun persist(tasks: ArrayList<Task>, resultListener: ResultListener) {
        val tasksHashMap = HashMap<String, Any>()
        tasksHashMap[Document.TASKS] = tasks

        val task = storeProvider.update(boardId, tasksHashMap) as com.google.android.gms.tasks.Task<Void>
        task
            .addOnCompleteListener {
                if (task.isSuccessful) {
                    Log.d(LOG_TAG, "Success")
                    resultListener.onSuccess(taskList)
                }
            }
            .addOnFailureListener {
                Log.e(LOG_TAG, "Error: ${it.message}")
                resultListener.onFailure(it.message.toString())
            }
    }

    fun loadTaskList(resultListener: ResultListener) {
        val task = storeProvider.findById(boardId) as com.google.android.gms.tasks.Task<DocumentSnapshot>
        task
            .addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    val board = result.result.toObject(Board::class.java)
                    if (board != null) {
                        taskList = board.taskList
                        resultListener.onSuccess(taskList)
                    }
                }
            }
            .addOnFailureListener {
                resultListener.onFailure(it.message.toString())
            }
    }
}
