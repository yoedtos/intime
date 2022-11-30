package net.yoedtos.intime.model

import android.content.res.Resources
import net.yoedtos.intime.R
import net.yoedtos.intime.model.Constants.FIELD_MAX
import net.yoedtos.intime.model.Constants.FIELD_MIN
import net.yoedtos.intime.model.dto.BoardDTO
import net.yoedtos.intime.model.dto.Login
import net.yoedtos.intime.model.dto.Register
import net.yoedtos.intime.model.dto.UserDTO

class Validator(val resources: Resources) {

    fun validateRegister(register: Register) {
        validate(register.name, resources.getString(R.string.name) )
        validate(register.email, resources.getString(R.string.email))
        validate(register.password, resources.getString(R.string.password))
    }

    fun validateLogin(login: Login) {
        validate(login.email, resources.getString(R.string.email))
        validate(login.password, resources.getString(R.string.password))
    }

    fun validateUser(userDTO: UserDTO) {
        validate(userDTO.name, resources.getString(R.string.name))
        validate(userDTO.mobile, resources.getString(R.string.mobile))
    }

    fun validateBoard(boardDTO: BoardDTO) {
        validate(boardDTO.name, resources.getString(R.string.board_name))
    }

    private fun validate(value: String, field: String) {
        val value = value.trim { it <= ' '}

        if (value.isNullOrEmpty()) {
            throw IllegalArgumentException(resources.getString(R.string.entry_error, field))
        }

        if (value.length > FIELD_MAX || value.length < FIELD_MIN) {
            throw IllegalArgumentException(resources.getString(R.string.entry_size_error, field))
        }
    }
}
