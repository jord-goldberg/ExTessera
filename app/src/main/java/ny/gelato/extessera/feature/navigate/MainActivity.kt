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

    val characters: RealmResults<Character> by lazy {
        realm.where(Character::class.java)
                .findAllSorted("updated", Sort.DESCENDING)
    }

    val spells: RealmResults<Spell> by lazy {
        realm.where(Spell::class.java)
                .findAll()
    }

    val weapons: RealmResults<Weapon> by lazy {
        realm.where(Weapon::class.java)
                .findAll()
    }

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

        if (spells.isEmpty()) {
            val spells = rawToRealmSpells(parseSpells(R.raw.spells))
            realm.executeTransactionAsync { realm ->
                for (spell in spells) {
                    Log.d("KnownSpell", "${spell.name} (${spell.level}) ${spell.range} ${spell.castingTime} ${spell.school} ${spell.isRitual}")
                    realm.copyToRealmOrUpdate(spell)
                }
            }
        }

        if (weapons.isEmpty()) {
            val weapons = realmWeapons()
            realm.executeTransactionAsync { realm ->
                for (weapon in weapons) realm.copyToRealmOrUpdate(weapon)
            }
        }

        if (characters.isEmpty()) realm.executeTransaction {
            it.copyToRealm(Character())
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

    fun parseSpells(resource: Int): String {
        var json: String = ""
        try {
            val input = resources.openRawResource(resource)
            val buffer = ByteArray(input.available())
            input.read(buffer)
            input.close()
            json = String(buffer)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return json
    }


}
