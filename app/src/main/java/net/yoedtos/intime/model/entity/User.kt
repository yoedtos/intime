package net.yoedtos.intime.model.entity

data class User(
    val id: String ="",
    val name: String = "",
    val email: String = "",
    val image: String = "",
    val mobile: Long = 0,
    val token: String = "",
    val selected: Boolean = false
)
