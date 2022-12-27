package net.yoedtos.intime.service

import androidx.core.net.toUri
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import net.yoedtos.intime.InTimeApp
import net.yoedtos.intime.R
import net.yoedtos.intime.client.Document
import net.yoedtos.intime.client.FileBaseStore
import net.yoedtos.intime.model.Constants
import net.yoedtos.intime.model.dto.UserDTO
import net.yoedtos.intime.model.entity.User
import net.yoedtos.intime.service.base.StoreProvider
import net.yoedtos.intime.utils.CacheHandler

class UserService {
    private val storeProvider: StoreProvider
    private var user: User = User()
    private val context = InTimeApp.instance?.applicationContext

    init {
        storeProvider = FileBaseStore(Document.USERS)
    }

    fun loadUser(id: String, resultListener: ResultListener) {
        val task = storeProvider.findById(id) as Task<DocumentSnapshot>
        task
            .addOnSuccessListener { document ->
                user = document.toObject(User::class.java)!!
                val userDTO = convertToDTO(user)
                saveToCache(userDTO)
                resultListener.onSuccess(userDTO)
            }
            .addOnFailureListener {
                resultListener.onFailure(it.message.toString())
            }
    }

    fun create(user: User, resultListener: ResultListener) {
        val task = storeProvider.add(user.id, user) as Task<Void>
        task
            .addOnSuccessListener {
                saveToCache(convertToDTO(user))
                resultListener.onSuccess(Constants.OK)
            }
            .addOnFailureListener {
                resultListener.onFailure(it.message.toString())
            }
    }

    fun update(userDTO: UserDTO, resultListener: ResultListener) {
        if (context == null || userDTO.image.contains(Constants.IMAGE_SRC) || userDTO.image.isEmpty()) {
            updateUserData(userDTO, resultListener)
        } else {
            StorageService.getInstance(context)
                .upload(userDTO.image.toUri(), object : ResultListener {
                    override fun onSuccess(any: Any) {
                        any as String
                        userDTO.image = any
                        updateUserData(userDTO, resultListener)
                    }

                    override fun onFailure(message: String) {
                        resultListener.onFailure(message)
                    }
                })
        }
    }

    fun findByEmail(email: String, resultListener: ResultListener) {
        val task = storeProvider.findByField(Document.EMAIL, email) as Task<QuerySnapshot>
        task
            .addOnSuccessListener { document ->
                if (document.documents.size > 0) {
                    user = document.documents[0].toObject(User::class.java)!!
                    resultListener.onSuccess(user)
                } else {
                    val message = context?.resources?.getString(R.string.user_not_found)
                    if (message != null) {
                        resultListener.onFailure(message)
                    }
                }
            }
            .addOnFailureListener {
                resultListener.onFailure(it.message.toString())
            }
    }

    fun loadUsersFromIdList(arrayList: ArrayList<String>, resultListener: ResultListener) {
        val list = arrayList as List<String>
        val task = storeProvider.findAllByFieldInList(Document.ID, list) as Task<QuerySnapshot>
        task
            .addOnSuccessListener { document ->
                val usersList = ArrayList<User>()
                if (task.isSuccessful) {
                    for (d in document.documents) {
                        val user = d.toObject(User::class.java)!!
                        usersList.add(user)
                    }
                    resultListener.onSuccess(usersList)
                }
            }
            .addOnFailureListener {
                resultListener.onFailure(it.message.toString())
            }
    }

    private fun updateUserData(userDTO: UserDTO, resultListener: ResultListener) {
        val userHashMap = convertToHashMap(userDTO)
        val task = storeProvider.update(user.id, userHashMap) as Task<Void>
        task
            .addOnSuccessListener {
                saveToCache(userDTO)
                resultListener.onSuccess(Constants.OK)
            }
            .addOnFailureListener {
                resultListener.onFailure(it.message.toString())
            }

    }

    private fun saveToCache(userDTO: UserDTO) {
        if (context != null) {
            val cacheHandler = CacheHandler(context)
            cacheHandler.save(Document.USER_NAME, userDTO.name)
            cacheHandler.saveObject(userDTO)
        }
    }

    private fun convertToDTO(user: User): UserDTO {
        return UserDTO(user.name, user.email, user.image, user.mobile.toString())
    }

    private fun convertToHashMap(userDTO: UserDTO): HashMap<String, Any> {
        val userHashMap = HashMap<String, Any>()
        userHashMap[Document.IMAGE] = userDTO.image
        userHashMap[Document.NAME] = userDTO.name
        userHashMap[Document.MOBILE] = userDTO.mobile.toLong()
        return userHashMap
    }
}
