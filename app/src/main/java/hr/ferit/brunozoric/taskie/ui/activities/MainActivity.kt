package hr.ferit.brunozoric.taskie.ui.activities

import android.view.Menu
import android.view.MenuItem
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.ui.activities.base.BaseActivity
import hr.ferit.brunozoric.taskie.ui.fragments.RefreshAllTasks
import hr.ferit.brunozoric.taskie.ui.fragments.TasksFragment

class MainActivity : BaseActivity() {

    override fun getLayoutResourceId() = R.layout.activity_main

    override fun setUpUi() {
        showFragment(TasksFragment.newInstance())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        supportFragmentManager.fragments.last()?.let {
            if (it is TasksFragment) {
                menuInflater.inflate(R.menu.menu_layout, menu)
            }
        }
        return true
    }

    private var refreshListener:RefreshAllTasks? = null


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refreshTasks -> callRefreshTasks()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun callRefreshTasks(){
        refreshListener!!.refreshTasks()
    }

}