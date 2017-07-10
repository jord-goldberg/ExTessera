package ny.gelato.extessera.feature.spell_detail

import android.app.Dialog
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v7.widget.PopupMenu
import android.view.View
import io.realm.RealmResults
import io.realm.Sort
import ny.gelato.extessera.App
import ny.gelato.extessera.R
import ny.gelato.extessera.data.model.Spell
import ny.gelato.extessera.data.model.character.Character
import ny.gelato.extessera.data.source.CharacterManager
import ny.gelato.extessera.databinding.BottomSheetSpellDetailBinding
import ny.gelato.extessera.feature.edit_character.EditCharacterActivity

/**
 * Created by jord.goldberg on 5/15/17.
 */


class SpellDetailBottomFragment : BottomSheetDialogFragment() {

    companion object {
        fun newInstance(name: String, isInSpellBook: Boolean = false): SpellDetailBottomFragment =
                SpellDetailBottomFragment().apply {
                    arguments = Bundle().apply {
                        putString("name", name)
                        putBoolean("isInSpellBook", isInSpellBook)
                    }
                }
    }

    val spell: Spell by lazy {
        val name = arguments.getString("name")
        App.component.realm()
                .where(Spell::class.java)
                .equalTo("name", name)
                .findFirst()
    }

    val binding: BottomSheetSpellDetailBinding by lazy {
        val isInSpellBook = arguments.getBoolean("isInSpellBook")
        DataBindingUtil.inflate<BottomSheetSpellDetailBinding>(
                activity.layoutInflater, R.layout.bottom_sheet_spell_detail, null, false)
                .apply {
                    viewModel = SpellDetailModel(spell, isInSpellBook)
                    presenter = this@SpellDetailBottomFragment
                }
    }

    val sheet: BottomSheetDialog by lazy {
        BottomSheetDialog(activity).apply {
            setContentView(binding.root)
        }
    }

    val characters: RealmResults<Character> by lazy {
        App.component.realm()
                .where(Character::class.java)
                .findAllSorted("updated", Sort.DESCENDING)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = sheet

    fun showAddSpell(v: View, spell: SpellDetailModel) {
        PopupMenu(activity, v).apply {
            if (characters.isNotEmpty()) characters.forEachIndexed { index, character ->
                val name = character.name.substringBefore(" ")
                menu.add(0, index, index, "Add to $name")
            }
            menu.add(1, Int.MAX_VALUE, characters.size, "Create Character")
            setOnMenuItemClickListener {
                when (it.itemId) {
                    Int.MAX_VALUE -> EditCharacterActivity.show(activity)
                    else -> {
                        CharacterManager(App.component.realm(), characters[it.itemId].id).learnSpell(spell.name)
                        Handler().postDelayed({ dismiss(); activity.finish() }, 200)
                    }
                }
                true
            }
            show()
        }
    }
}