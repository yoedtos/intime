package net.yoedtos.intime.model.entity

data class Task(
    var id: String = "",
    var title: String = "",
    val createdBy: String = "",
    var cards: ArrayList<Card> = ArrayList()
)
