package ny.gelato.extessera.feature.spell_detail

import android.app.Dialog
import android.databinding.DataBindingUtil
import android.os.Bundle
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
import ny.gelato.extessera.databinding.BottomSheetSpellDetailBinding
import java.util.*

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

    val spell: Spell by lazy(LazyThreadSafetyMode.NONE) {
        val name = arguments.getString("name")
        App.component.realm()
                .where(Spell::class.java)
                .equalTo("name", name)
                .findFirst()
    }

    val binding: BottomSheetSpellDetailBinding by lazy(LazyThreadSafetyMode.NONE) {
        val isInSpellBook = arguments.getBoolean("isInSpellBook")
        DataBindingUtil.inflate<BottomSheetSpellDetailBinding>(
                activity.layoutInflater, R.layout.bottom_sheet_spell_detail, null, false)
                .apply {
                    viewModel = SpellDetailModel(spell, isInSpellBook)
                    presenter = this@SpellDetailBottomFragment
                }
    }

    val sheet: BottomSheetDialog by lazy(LazyThreadSafetyMode.NONE) {
        BottomSheetDialog(activity).apply {
            setContentView(binding.root)
        }
    }

    val characters: RealmResults<Character> = App.component.realm()
            .where(Character::class.java)
            .findAllSorted("updated", Sort.DESCENDING)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = sheet

    fun showAddSpell(v: View) {
        if (characters.isNotEmpty())
            PopupMenu(activity, v).apply {
                if (characters.isNotEmpty()) characters.forEachIndexed { index, character ->
                    val name = character.firstName()
                    menu.add(0, index, index, "Add to $name")
                }
                setOnMenuItemClickListener {
                    val characterId = characters[it.itemId].id
                    App.component.realm().executeTransaction { realm ->
                        val character = realm.where(Character::class.java)
                                .equalTo("id", characterId)
                                .findFirst()
                        if (character.spells.where().equalTo("name", spell.name).findFirst() == null) {
                            character.spells.add(spell.toSpellbook())
                            character.updated = Date()
                        }

                        dismiss()
                        activity.finish()
                    }
                    true
                }
                show()
            }
    }
}