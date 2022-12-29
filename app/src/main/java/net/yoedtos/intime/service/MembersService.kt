package net.yoedtos.intime.service

import net.yoedtos.intime.model.dto.Member
import net.yoedtos.intime.model.entity.User

class MembersService (val boardId: String) {
    private val boardService: BoardService = BoardService()
    private val userService: UserService = UserService()
    private lateinit var userIdList: ArrayList<String>

    fun addMember(memberEmail: String, resultListener: ResultListener) {
        userService.findByEmail(memberEmail, object : ResultListener {
            override fun onSuccess(any: Any) {
                any as User
                userIdList.add(any.id)
                boardService.updateMembers(boardId, userIdList, resultListener)
            }

            override fun onFailure(message: String) {
                resultListener.onFailure(message)
            }
        })
    }

    fun loadBoardMembers(resultListener: ResultListener) {
        boardService.loadMembers(boardId, object : ResultListener {
            override fun onSuccess(any: Any) {
                userIdList = any as ArrayList<String>
                loadUsersFromList(userIdList, resultListener)
            }

            override fun onFailure(message: String) {
                resultListener.onFailure(message)
            }
        })
    }

    private fun loadUsersFromList(userIdList: ArrayList<String>, resultListener: ResultListener) {
        userService.loadUsersFromIdList(userIdList, object : ResultListener {
            override fun onSuccess(any: Any) {
                any as ArrayList<User>
                val members = convertToMembers(any)
                resultListener.onSuccess(members)
            }

            override fun onFailure(message: String) {
                resultListener.onFailure(message)
            }
        })
    }

    private fun convertToMembers(users: ArrayList<User>): ArrayList<Member> {
        return users.map { u ->
            Member(u.id, u.name, u.email, u.image, false)
        }.toCollection(ArrayList())
    }
}
