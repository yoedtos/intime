package net.yoedtos.intime.service

import androidx.core.net.toUri
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import net.yoedtos.intime.InTimeApp
import net.yoedtos.intime.client.Document
import net.yoedtos.intime.client.FileBaseStore
import net.yoedtos.intime.model.Constants
import net.yoedtos.intime.model.dto.BoardDTO
import net.yoedtos.intime.model.entity.Board
import net.yoedtos.intime.service.base.StoreProvider
import net.yoedtos.intime.utils.CacheHandler

class BoardService {
    private val storeProvider: StoreProvider
    private val userId: String
    private var boardList: ArrayList<BoardDTO>
    private val context = InTimeApp.instance?.applicationContext
    private var userName: String

    init {
        storeProvider = FileBaseStore(Document.BOARDS)
        userId = AuthService.getInstance().getCurrentUserID()
        boardList = arrayListOf()
        userName = context?.let {
            CacheHandler(it).load(Document.USER_NAME)
        }.toString()

    }

    fun create(boardDTO: BoardDTO, resultListener: ResultListener) {
        if (context == null || boardDTO.image.contains(Constants.IMAGE_SRC)  || boardDTO.image.isEmpty()) {
            createBoardAndPersist(boardDTO, resultListener)
        } else {
            StorageService.getInstance(context)
                .upload(boardDTO.image.toUri(), object : ResultListener {
                    override fun onSuccess(any: Any) {
                        boardDTO.image = any as String
                        createBoardAndPersist(boardDTO, resultListener)
                    }

                    override fun onFailure(message: String) {
                        resultListener.onFailure(message)
                    }
                })
        }
    }

    fun loadBoards(resultListener: ResultListener) {
        storeProvider as FileBaseStore
        val task = storeProvider.findArrayByValue(Document.ASSIGNED_TO, userId)
        task
            .addOnSuccessListener { document ->
                boardList = arrayListOf()
                for (d in document.documents) {
                    val boardDTO = d.toObject(BoardDTO::class.java)
                    if (boardDTO != null) {
                        boardDTO.id = d.id
                        boardList.add(boardDTO)
                    }
                }
                resultListener.onSuccess(boardList)
            }
            .addOnFailureListener {
                resultListener.onFailure(it.message.toString())
            }
    }

    fun loadMembers(boardId: String, resultListener: ResultListener) {
        val task = storeProvider.findById(boardId) as Task<DocumentSnapshot>
       task
           .addOnSuccessListener { document ->
               val board = document.toObject(Board::class.java)!!
               val assignedTo = board.assignedTo
               resultListener.onSuccess(assignedTo)
           }
           .addOnFailureListener {
               resultListener.onFailure(it.message.toString())
           }
    }

    fun updateMembers(boardId: String, userIdList: ArrayList<String>, resultListener: ResultListener) {
        val membersHashMap = HashMap<String, Any>()
        membersHashMap[Document.ASSIGNED_TO] = userIdList

        val task = storeProvider.update(boardId, membersHashMap) as Task<Void>
        task
            .addOnCompleteListener {
                if (task.isSuccessful) {
                    resultListener.onSuccess(Constants.OK)
                }
        }
            .addOnFailureListener {
                resultListener.onFailure(it.message.toString())
            }
    }

    private fun createBoardAndPersist(boardDTO: BoardDTO, resultListener: ResultListener) {
        val board = Board(boardDTO.name, boardDTO.image, userName)
        board.assignedTo.add(userId)

        val task = storeProvider.add(board) as Task<Void>
        task
            .addOnSuccessListener {
                boardDTO.id = board.documentId
                boardDTO.createdBy = userName
                boardList.add(boardDTO)
                resultListener.onSuccess(Constants.OK)
            }
            .addOnFailureListener {
                resultListener.onFailure(it.message.toString())
            }
    }
}
