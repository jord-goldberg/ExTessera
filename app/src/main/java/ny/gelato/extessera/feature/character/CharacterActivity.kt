package ny.gelato.extessera.feature.character

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.*
import android.view.Menu
import android.view.MenuItem
import android.view.View

import ny.gelato.extessera.R
import kotlinx.android.synthetic.main.activity_character.*
import ny.gelato.extessera.feature.character.sheet.CharacterSheetFragment
import ny.gelato.extessera.feature.search_5e.Search5eActivity

/**
 * Pretty much just a container for whatever character fragments are shown. CharacterSheetFragment
 * in the sheet package is the main view for a character. This activity also keeps the toolbar
 * navigation - either back up to the player view or to the search view - in one place.
 */

class CharacterActivity : AppCompatActivity() {

    companion object {
        fun showCharacter(context: Context, id: String) {
            val intent = Intent(context, CharacterActivity::class.java)
            intent.putExtra("id", id)
            context.startActivity(intent)
        }
    }

    val id: String by lazy(LazyThreadSafetyMode.NONE) { intent.getStringExtra("id") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        savedInstanceState ?: supportFragmentManager.beginTransaction()
                .replace(R.id.container, CharacterSheetFragment.newInstance(id))
                .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_character_toolbar, menu)
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.apply {
            queryHint = "Search all spells, weapons..."
            isFocusable = false
            setIconifiedByDefault(false)
            setOnClickListener { Search5eActivity.showSearchAll(this@CharacterActivity) }
            findViewById<View>(android.support.v7.appcompat.R.id.search_src_text).setOnTouchListener { _, _ ->
                Search5eActivity.showSearchAll(this@CharacterActivity); true
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_search_also) Search5eActivity.showSearchAll(this)
        return super.onOptionsItemSelected(item)
    }
}
