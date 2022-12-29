package net.yoedtos.intime.view

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_members.*
import kotlinx.android.synthetic.main.dlg_search_member.*
import net.yoedtos.intime.R
import net.yoedtos.intime.client.Document
import net.yoedtos.intime.model.Validator
import net.yoedtos.intime.model.dto.Member
import net.yoedtos.intime.service.MembersService
import net.yoedtos.intime.service.ResultListener
import net.yoedtos.intime.utils.CacheHandler
import net.yoedtos.intime.view.ViewUtils.setupActionBar
import net.yoedtos.intime.view.adapter.MemberItemsAdapter
import net.yoedtos.intime.view.info.ErrorAlert
import net.yoedtos.intime.view.info.GuiNotifier
import net.yoedtos.intime.view.info.SearchDialog

private val LOG_TAG = MembersActivity::class.java.simpleName

class MembersActivity : AppCompatActivity() {
    private var membersAdapter: MemberItemsAdapter? = null
    private lateinit var membersService: MembersService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_members)

        setupActionBar(this, toolbar_members_activity)
        rv_members_list.layoutManager = LinearLayoutManager(this)
        rv_members_list.setHasFixedSize(true)

        initialize()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_member, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_add_member -> {
                showSearchDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        Log.d(LOG_TAG, "On resume")

        membersService.loadBoardMembers(object: ResultListener {
            override fun onSuccess(any: Any) {
                loadMembersToUI(any as ArrayList<Member>)
            }

            override fun onFailure(message: String) {
                ErrorAlert(this@MembersActivity, message).show()
            }
        })

        super.onResume()
    }

    private fun initialize() {
        val boardId = CacheHandler(this).load(Document.BOARD_ID)
        membersService = MembersService(boardId)
    }

    private fun searchMember(memberEmail: String) {
        membersService.addMember(memberEmail, object: ResultListener {
            override fun onSuccess(any: Any) {
                GuiNotifier(this@MembersActivity).showToast(R.string.added, memberEmail)
                onResume()
            }

            override fun onFailure(message: String) {
                ErrorAlert(this@MembersActivity, message).show()
            }
        })
    }

    private fun loadMembersToUI(members: ArrayList<Member>) {
        membersAdapter = MemberItemsAdapter(members)
        rv_members_list.adapter = membersAdapter
    }

    private fun showSearchDialog() {
        val searchDialog = SearchDialog(this)
        searchDialog.tv_add.setOnClickListener {
            val memberEmail = searchDialog.et_email_search_member.text.toString()
            try {
                Validator(resources).validateEmail(memberEmail)
                searchDialog.dismiss()
                searchMember(memberEmail)
            } catch (e: IllegalArgumentException) {
                GuiNotifier(this).showErrorSnackBar(e.message.toString())
            }
        }
        searchDialog.show()
    }
}