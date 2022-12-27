package net.yoedtos.intime.client

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import net.yoedtos.intime.service.base.StoreProvider

class FileBaseStore(val collection: String): StoreProvider {
    private var fileStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val collectionReference: CollectionReference

    init {
        try {
            fileStore.useEmulator("10.0.2.2", 8080)
        } catch (e: IllegalStateException) {

        }
        collectionReference = fileStore.collection(collection)
    }

    override fun add(any: Any): Task<Void> {
        return fileStore.collection(collection)
            .document()
            .set(any, SetOptions.merge())
    }

    override fun add(id: String, any: Any): Task<Void> {
        return fileStore.collection(collection)
            .document(id)
            .set(any, SetOptions.merge())
    }

    override fun update(id: String, any: Any): Task<Void> {
        return fileStore.collection(collection)
            .document(id)
            .update(any as HashMap<String, Any>)
    }

    override fun findById(id: String): Task<DocumentSnapshot> {
        return fileStore.collection(collection)
            .document(id)
            .get()
    }

    override fun findAll(): Task<QuerySnapshot> {
        return collectionReference.get()
    }

    /**
     * Delete a field in document with value 'id'
     */
    override fun delete(id: String, field: String): Task<Void> {
        val updates = hashMapOf<String, Any>(field to FieldValue.delete())

        return fileStore.collection(collection)
            .document(id)
            .update(updates)
    }

    /**
     * Get array of String in collection with name 'array' with value 'value'
     */
    override fun findArrayByValue(array: String, value: String): Task<QuerySnapshot> {
        return collectionReference
            .whereArrayContains(array, value)
            .get()
    }

    override fun findByField(field: String, value: String): Task<QuerySnapshot> {
        return collectionReference
            .whereEqualTo(field, value)
            .get()
    }

    /**
     * Get all records with field value 'field' contained in a list
     */
    override fun findAllByFieldInList(field: String, list: List<String>): Task<QuerySnapshot> {
        return collectionReference
            .whereIn(field, list)
            .get()
    }
}