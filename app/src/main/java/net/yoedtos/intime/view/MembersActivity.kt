package net.yoedtos.intime.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_members.*
import net.yoedtos.intime.R
import net.yoedtos.intime.model.entity.User
import net.yoedtos.intime.view.ViewUtils.setupActionBar
import net.yoedtos.intime.view.adapter.MemberItemsAdapter
import net.yoedtos.intime.view.info.SearchDialog

class MembersActivity : AppCompatActivity() {
    private var membersAdapter: MemberItemsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_members)

        setupActionBar(this, toolbar_members_activity)
        rv_members_list.layoutManager = LinearLayoutManager(this)
        rv_members_list.setHasFixedSize(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_member, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_add_member -> {
                SearchDialog(this).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun searchMember(memberEmail: String) {

    }

    private fun loadMembersToUI(members: ArrayList<User>) {
        membersAdapter = MemberItemsAdapter(members)
        rv_members_list.adapter = membersAdapter
    }
}