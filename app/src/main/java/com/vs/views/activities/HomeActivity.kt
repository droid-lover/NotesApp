package com.vs.views.activities

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.vs.R
import com.vs.models.Note
import com.vs.veronica.utils.C
import com.vs.views.fragments.NotesListFragment
import kotlinx.android.synthetic.main.activity_home.*


/**
 * Created By Sachin
 */
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHomeFragment()
    }

    private fun setHomeFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.rlContainer, NotesListFragment(), "Home").commitAllowingStateLoss()
    }


    /*-- handling back press here --*/
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    override fun onBackPressed() {
        if (supportFragmentManager.findFragmentByTag(C.NOTE_DETAILS) == null) {
            super.onBackPressed()
        } else {
            for (i in 0 until supportFragmentManager.backStackEntryCount) {
                supportFragmentManager.popBackStack()
            }
        }
    }
}
