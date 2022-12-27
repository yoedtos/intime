package net.yoedtos.intime.service.base

import com.google.android.gms.tasks.Task

interface StoreProvider {
    fun add(any: Any): Task<*>
    fun add(id: String, any: Any): Task<*>
    fun update(id: String, any: Any): Task<*>
    fun findById(id: String): Task<*>
    fun findAll(): Task<*>
    fun delete(id: String, field: String): Task<*>
    fun findArrayByValue(array: String, value: String): Task<*>
    fun findByField(field: String, value: String): Task<*>
    fun findAllByFieldInList(field: String, list: List<String>): Task<*>
}