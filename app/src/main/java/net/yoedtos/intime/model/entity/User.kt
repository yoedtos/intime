package net.yoedtos.intime.model.entity

data class User(
    val id: String ="",
    var name: String = "",
    val email: String = "",
    var image: String = "",
    var mobile: Long = 0,
    var token: String = ""
)
