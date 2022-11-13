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
import net.yoedtos.intime.StringUtils
import net.yoedtos.intime.model.dto.Member
import net.yoedtos.intime.model.entity.Card
import net.yoedtos.intime.view.ListViewUtils.hideCardMembersView
import net.yoedtos.intime.view.ListViewUtils.showCardMembersView
import net.yoedtos.intime.view.ViewUtils.setupActionBar
import net.yoedtos.intime.view.ViewUtils.setupLabelColorInView
import net.yoedtos.intime.view.adapter.CardMembersItemsAdapter
import net.yoedtos.intime.view.adapter.MemberItemsAdapter
import net.yoedtos.intime.view.info.CardMembersDialog
import net.yoedtos.intime.view.info.ColorDialog
import net.yoedtos.intime.view.info.DateSelectDialog
import net.yoedtos.intime.view.info.DeleteAlert
import net.yoedtos.intime.view.listener.ItemClickListener
import net.yoedtos.intime.view.listener.OnClickListener
import java.util.*
import kotlin.collections.ArrayList

private val LOG_TAG = CardActivity::class.java.simpleName

class CardActivity : AppCompatActivity() {
    private var cardIndex: Int = 0
    private lateinit var card: Card
    private lateinit var stringUtils: StringUtils

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

            finish()
        }

        super.onResume()
    }

    override fun finish() {
        var cards = ArrayList<Card>()
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

            supportActionBar?.title = card.name

            et_name_card.setText(card.name)
            if (card.labelColor.isNotEmpty()) {
                setupLabelColorInView(this, card.labelColor)
            }

            tv_select_due_date.text = stringUtils.formatDateFromMillis(card.dueDate)
            setupSelectedMembersList()
        }
    }

    private fun showLabelColorDialog() {
        val colorDialog = ColorDialog(this, card.labelColor)
        colorDialog.setOnClickListener(object : OnClickListener {
            override fun onClick(item: Any) {
                val activity = this@CardActivity
                val color = item as String

                setupLabelColorInView(activity, color)
            }
        })

        colorDialog.show()
    }

    private fun showSelectMemberDialog() {
        var boardMembers = ArrayList<Member>()

        val memberItemsAdapter = MemberItemsAdapter(boardMembers)

        val cardMembersDialog = CardMembersDialog(this, memberItemsAdapter)
        cardMembersDialog.setOnClickListener(object : OnClickListener {
            override fun onClick(item: Any) {
                val member = item as Member

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

                dialogInterface.dismiss()
                finish()
        }.create().show()
    }

    private fun setupSelectedMembersList() {
        val selectedMembers = ArrayList<Member>()

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