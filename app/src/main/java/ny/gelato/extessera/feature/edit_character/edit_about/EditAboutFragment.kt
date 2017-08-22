package ny.gelato.extessera.feature.edit_character.edit_about

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ny.gelato.extessera.App
import ny.gelato.extessera.R
import ny.gelato.extessera.data.model.character.Background
import ny.gelato.extessera.data.model.character.Character
import ny.gelato.extessera.data.model.character.Proficiency
import ny.gelato.extessera.data.model.character.Skill
import ny.gelato.extessera.databinding.FragmentEditCharacterAboutBinding
import ny.gelato.extessera.feature.edit_character.EditCharacterView
import ny.gelato.extessera.feature.edit_character.edit_proficiencies.EditProficienciesFragment
import java.util.*

/**
 * Created by jord.goldberg on 6/23/17.
 */

class EditAboutFragment : Fragment(), EditCharacterView {

    companion object {
        fun newInstance(id: String?): EditAboutFragment = EditAboutFragment().apply {
            arguments = Bundle().apply { putString("id", id) }
        }
    }

    val id: String by lazy(LazyThreadSafetyMode.NONE) { arguments.getString("id") }

    val model: EditAboutModel by lazy {
        EditAboutModel(App.component.realm()
                .where(Character::class.java)
                .equalTo("id", id)
                .findFirst())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentEditCharacterAboutBinding>(
                activity.layoutInflater, R.layout.fragment_edit_character_about, null, false)
                .apply { viewModel = model }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        activity.title = "Character Background"
    }

    override fun showNext() {
        saveAbout()
        fragmentManager.beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in_right,
                        R.anim.slide_out_left,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right)
                .addToBackStack(null)
                .replace(R.id.container, EditProficienciesFragment.newInstance(id))
                .commit()
    }

    private fun saveAbout() {
        App.component.realm().executeTransaction { realm ->
            val character = realm.where(Character::class.java)
                    .equalTo("id", id)
                    .findFirst()

            val prevBackground = character.background
            for (type in prevBackground.skills)
                character.skills.where()
                        .equalTo("typeName", type.name)
                        .findFirst()
                        .proficiency = Skill.Proficiency.NONE

            character.background = model.background
            character.alignment = model.alignment
            character.about = model.about
            character.updated = Date()

            val skills = character.background.skills
            for (type in skills)
                character.skills.where()
                        .equalTo("typeName", type.name)
                        .findFirst()
                        .proficiency = Skill.Proficiency.FULL

            character.proficiencies.where()
                    .equalTo("typeName", Proficiency.Type.TOOL.name)
                    .findAll()
                    .forEach {
                        character.proficiencies.remove(it)
                        it.deleteFromRealm()
                    }

            val tools = character.background.tools()
            tools.forEach { character.proficiencies.add(Proficiency(Proficiency.Type.TOOL.name, it.formatted)) }

            val equipment = character.background.equipment()
            equipment.filter { character.equipment.where().equalTo("name", it.name).findFirst() == null }
                    .forEach { character.equipment.add(it) }

            character.gold = character.background.goldPieces
        }
    }
}