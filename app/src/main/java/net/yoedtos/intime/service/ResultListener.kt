package net.yoedtos.intime.service

interface ResultListener {
    fun onSuccess(any: Any)
    fun onFailure(message: String)
}