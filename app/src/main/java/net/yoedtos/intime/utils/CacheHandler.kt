package net.yoedtos.intime.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import net.yoedtos.intime.model.Constants

class CacheHandler(context: Context) {
    private val sharedPreferences: SharedPreferences
    private val editor: SharedPreferences.Editor

    init {
        sharedPreferences = context.getSharedPreferences(Constants.CACHE, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    fun save(key: String, value: String) {
        editor.putString(key, value)
        editor.commit()
    }

    fun load(key: String): String {
        return sharedPreferences.getString(key, "").toString()
    }

    fun remove(key: String) {
        editor.remove(key)
        editor.commit()
    }

    fun clearAll() {
        editor.clear()
        editor.commit()
    }

    fun saveObject(any: Any) {
        val json = Gson().toJson(any)
        save(any::class.simpleName!!, json)
    }

    fun loadObject(clazz: Class<*>): Any {
        val json = load(clazz.simpleName)
        if(json.isEmpty()) {
            throw IllegalStateException()
        }
        return Gson().fromJson(json, clazz)
    }
}