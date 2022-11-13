package net.yoedtos.intime.view.listener

interface ChangeListener {
    fun onAdd(index: Int, any: Any)
    fun onUpdate(index: Int, any: Any)
    fun onDelete(index: Int)
}