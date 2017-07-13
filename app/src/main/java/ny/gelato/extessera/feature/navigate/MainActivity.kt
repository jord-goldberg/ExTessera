package ny.gelato.extessera.feature.navigate

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.*
import android.util.Log
import android.view.Menu

import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import ny.gelato.extessera.R
import ny.gelato.extessera.feature.character_sheet.*
import ny.gelato.extessera.util.rawToRealmSpells
import java.io.File
import java.io.IOException
import kotlinx.android.synthetic.main.activity_main.*
import ny.gelato.extessera.App
import ny.gelato.extessera.data.model.Spell
import ny.gelato.extessera.data.model.Weapon
import ny.gelato.extessera.data.model.character.Character
import ny.gelato.extessera.feature.search_5e.Search5eActivity
import ny.gelato.extessera.util.realmWeapons


class MainActivity : AppCompatActivity() {

    companion object {
        fun showCharacter(context: Context, id: String) {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("id", id)
            context.startActivity(intent)
        }
    }

    val id: String by lazy(LazyThreadSafetyMode.NONE) { intent.getStringExtra("id") }

    val realm: Realm = App.component.realm()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Log.d("Path:", externalCacheDir.canonicalPath)

        try {
            val file = File(this.externalCacheDir, "export.realm")
            file.delete()
            realm.writeCopyTo(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        savedInstanceState ?: supportFragmentManager.beginTransaction()
                .replace(R.id.container, CharacterFragment.newInstance(id))
                .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_nav_toolbar, menu)
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.apply {
            queryHint = "Spells, weapons, monsters..."
            setIconifiedByDefault(false)
            setOnClickListener { Search5eActivity.showSearchAll(this@MainActivity) }
            findViewById(android.support.v7.appcompat.R.id.search_src_text).setOnTouchListener { _, _ ->
                Search5eActivity.showSearchAll(this@MainActivity); true
            }
        }
        return true
    }
}
