package net.yoedtos.intime.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_card.*
import net.yoedtos.intime.R
import net.yoedtos.intime.model.dto.Member
import net.yoedtos.intime.model.entity.Card
import net.yoedtos.intime.service.CardService
import net.yoedtos.intime.service.ResultListener
import net.yoedtos.intime.utils.StringUtils
import net.yoedtos.intime.view.ListViewUtils.hideCardMembersView
import net.yoedtos.intime.view.ListViewUtils.showCardMembersView
import net.yoedtos.intime.view.ViewUtils.setupActionBar
import net.yoedtos.intime.view.ViewUtils.setupLabelColorInView
import net.yoedtos.intime.view.adapter.CardMembersItemsAdapter
import net.yoedtos.intime.view.adapter.MemberItemsAdapter
import net.yoedtos.intime.view.info.*
import net.yoedtos.intime.view.listener.ItemClickListener
import net.yoedtos.intime.view.listener.OnClickListener
import java.util.*

private val LOG_TAG = CardActivity::class.java.simpleName

class CardActivity : AppCompatActivity() {
    private var cardIndex: Int = 0
    private lateinit var card: Card
    private lateinit var stringUtils: StringUtils
    private lateinit var cardService: CardService

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(LOG_TAG, "On Create")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)

        setupActionBar(this, toolbar_card_activity)

        initialize()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_delete_card, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_delete_card -> {
                deleteWithAlert(card.name)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        Log.d(LOG_TAG, "On Start")

        super.onStart()
    }
    override fun onResume() {
        Log.d(LOG_TAG, "On Resume")

        tv_select_label_color.setOnClickListener{
            showLabelColorDialog()
        }

        tv_select_members.setOnClickListener {
            showSelectMemberDialog()
        }

        tv_select_due_date.setOnClickListener {
            showDateDialog()
        }

        btn_update_card.setOnClickListener{
            card.name = et_name_card.text.toString()
            cardService.update(cardIndex, card)
            finish()
        }

        super.onResume()
    }

    override fun finish() {
        val cards: ArrayList<Card> = cardService.getCards()
        val intent = Intent()
        intent.putParcelableArrayListExtra(IntentExtra.CARD_LIST, cards)
        this.setResult(RESULT_OK, intent)
        super.finish()
    }
    private fun initialize() {
        stringUtils = StringUtils(this)

        if(intent.hasExtra(IntentExtra.CARD_LIST)) {
            val cardList = intent.getParcelableArrayListExtra<Card>(IntentExtra.CARD_LIST) as ArrayList<Card>
            cardIndex = intent.getIntExtra(IntentExtra.CARD_INDEX, 0)

            card = cardList[cardIndex]
            cardService = CardService(cardIndex, cardList)
            supportActionBar?.title = card.name
            loadCardMembers()

            et_name_card.setText(card.name)
            if (card.labelColor.isNotEmpty()) {
                setupLabelColorInView(this, card.labelColor)
            }

            tv_select_due_date.text = stringUtils.formatDateFromMillis(card.dueDate)
        }
    }

    private fun loadCardMembers() {
        cardService.loadMembers(object : ResultListener {
            override fun onSuccess(any: Any) {
                setupSelectedMembersList()
            }

            override fun onFailure(message: String) {
                ErrorAlert(this@CardActivity, message).show()
            }

        })
    }

    private fun showLabelColorDialog() {
        val colorDialog = ColorDialog(this, card.labelColor)
        colorDialog.setOnClickListener(object : OnClickListener {
            override fun onClick(item: Any) {
                val activity = this@CardActivity
                val color = item as String
                card.labelColor = color
                setupLabelColorInView(activity, color)
            }
        })

        colorDialog.show()
    }

    private fun showSelectMemberDialog() {
        val boardMembers = cardService.loadAssignedMembers()
        val memberItemsAdapter = MemberItemsAdapter(boardMembers)

        val cardMembersDialog = CardMembersDialog(this, memberItemsAdapter)
        cardMembersDialog.setOnClickListener(object : OnClickListener {
            override fun onClick(item: Any) {
                val member = item as Member
                cardService.updateMember(member)
                setupSelectedMembersList()
            }
        })

        cardMembersDialog.show()
    }

    private fun showDateDialog() {
        val calendar = Calendar.getInstance()
        val dateSelectDialog = DateSelectDialog(this) { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            val dateInMillis = calendar.timeInMillis
            tv_select_due_date.text = stringUtils.formatDateFromMillis(dateInMillis)
            card.dueDate = dateInMillis
        }
        dateSelectDialog.setMinDate(calendar.timeInMillis)
        dateSelectDialog.show()
    }

    private fun deleteWithAlert(cardName: String) {
        DeleteAlert(this, cardName)
            .setPositiveButton(R.string.alert_yes) {dialogInterface, _ ->
                cardService.delete(cardIndex)
                dialogInterface.dismiss()
                finish()
        }.create().show()
    }

    private fun setupSelectedMembersList() {
        val selectedMembers = cardService.getSelectedMembers()

        if(selectedMembers.size > 0) {
            selectedMembers.add(Member())
            initCardMembersView(selectedMembers)
            showCardMembersView(this)
        } else {
            hideCardMembersView(this)
        }
    }

    private fun initCardMembersView(selectedMembers: ArrayList<Member>) {
        rv_selected_members_list.layoutManager = GridLayoutManager(this@CardActivity, 6)
        val cardMemberItemsAdapter = CardMembersItemsAdapter(selectedMembers)
        rv_selected_members_list.adapter = cardMemberItemsAdapter

        cardMemberItemsAdapter.setOnClickListener(object : ItemClickListener {
            override fun onClick(position: Int, item: Any) {
                item as Member
                if(item.id.isEmpty()) {
                    this@CardActivity.showSelectMemberDialog()
                }
            }
        })
    }
}