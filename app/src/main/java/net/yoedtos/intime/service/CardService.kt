package net.yoedtos.intime.service

import net.yoedtos.intime.InTimeApp
import net.yoedtos.intime.client.Document
import net.yoedtos.intime.model.Constants
import net.yoedtos.intime.model.dto.Member
import net.yoedtos.intime.model.entity.Card
import net.yoedtos.intime.utils.CacheHandler

class CardService(private val cardIndex: Int, private val cardList: ArrayList<Card>) {
    private var members: ArrayList<Member> = ArrayList()
    private val membersService: MembersService
    private val context = InTimeApp.instance?.applicationContext

    init {
        val boardId = context?.let {
            CacheHandler(it).load(Document.BOARD_ID)
        }.toString()

        membersService = MembersService(boardId)
    }

    fun update(index: Int, card: Card) {
        cardList[index] = card
    }

    fun delete(index: Int) {
        cardList.removeAt(index)
    }

    fun getCards(): ArrayList<Card> {
        return cardList
    }

    fun updateMember(member: Member) {
        if (member.selected) {
            cardList[cardIndex].assignedTo.remove(member.id)
            member.selected = false
        } else {
            cardList[cardIndex].assignedTo.add(member.id)
        }
    }

    fun loadAssignedMembers(): ArrayList<Member> {
        val assignedTo = cardList[cardIndex].assignedTo

        for (i in members.indices) {
            for(j in assignedTo) {
                if (members[i].id == j) {
                    members[i].selected = true
                }
            }
        }
        return members
    }

    fun getSelectedMembers(): ArrayList<Member> {
        val members = loadAssignedMembers()
        return members.filter { m ->
            m.selected
        }.toCollection(ArrayList())
    }

    fun loadMembers(resultListener: ResultListener) {
        membersService.loadBoardMembers(object : ResultListener {
            override fun onSuccess(any: Any) {
                members = any as ArrayList<Member>
                resultListener.onSuccess(Constants.OK)
            }

            override fun onFailure(message: String) {
                resultListener.onFailure(message)
            }

        })
    }
}
