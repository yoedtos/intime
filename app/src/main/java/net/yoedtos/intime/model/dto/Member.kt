package net.yoedtos.intime.model.dto

data class Member(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val image: String = "",
    var selected: Boolean = false
)
