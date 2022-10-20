package net.yoedtos.intime.model.entity

data class Task(
    var title: String = "",
    val createdBy: String = "",
    var cards: ArrayList<Card> = ArrayList()
)
