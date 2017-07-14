package ny.gelato.extessera.feature.edit_character

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import kotlinx.android.synthetic.main.activity_edit_character.*
import ny.gelato.extessera.App
import ny.gelato.extessera.R
import ny.gelato.extessera.data.model.character.Character
import ny.gelato.extessera.feature.edit_character.edit_about.EditAboutFragment
import ny.gelato.extessera.feature.edit_character.edit_basics.EditBasicsFragment
import ny.gelato.extessera.feature.edit_character.edit_proficiencies.EditProficienciesFragment
import java.util.*


class EditCharacterActivity : AppCompatActivity() {

    companion object {
        fun show(context: Context, id: String? = null) {
            val intent = Intent(context, EditCharacterActivity::class.java)
            intent.apply {
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                putExtra("id", id)
            }
            context.startActivity(intent)
        }
    }

    val id: String? by lazy(LazyThreadSafetyMode.NONE) { intent.getStringExtra("id") }

    var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_character)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_white)

        back_text.setOnClickListener { showBack() }
        next_text.setOnClickListener { showNext() }

        savedInstanceState?.let { page = it.getInt("page") }
                ?: supportFragmentManager.beginTransaction()
                .replace(R.id.container, EditBasicsFragment.newInstance(id))
                .commit()

        updateBottomButtons()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt("page", page)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    fun showBack() {
        page -= 1
        onBackPressed()
        updateBottomButtons()
    }

    fun showNext() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.container)
                as EditCharacterView

        if (currentFragment.validateInput()) {
            page += 1
            currentFragment.showNext()
            if (page < 3) updateBottomButtons()
        }
    }

    private fun updateBottomButtons() {
        if (page == 2) next_text.text = "done"
        else next_text.text = "next"
        if (page == 0) back_text.visibility = View.GONE
        else back_text.visibility = View.VISIBLE
    }
}
