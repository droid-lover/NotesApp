package com.vs.views.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.vs.R
import com.vs.veronica.views.fragments.NotesListFragment
import kotlinx.android.synthetic.main.activity_home.*

/**
 * Created By Sachin
 */
class HomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        setHomeFragment()
        fab.setOnClickListener { view ->
        }
    }


    private fun setHomeFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.rlContainer, NotesListFragment())
                .commitAllowingStateLoss()
    }


    /*-- handling back press here --*/
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
//        compositeDisposable.dispose()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
}
