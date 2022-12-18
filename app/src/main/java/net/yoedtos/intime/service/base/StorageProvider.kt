package net.yoedtos.intime.service.base

import com.google.android.gms.tasks.Task

interface StorageProvider {
    fun upload(any: Any): Task<*>
}