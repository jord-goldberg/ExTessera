package ny.gelato.extessera.feature.edit_character.edit_basics

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ny.gelato.extessera.App
import ny.gelato.extessera.R
import ny.gelato.extessera.data.model.character.Character
import ny.gelato.extessera.data.model.character.Proficiency
import ny.gelato.extessera.databinding.FragmentEditCharacterBasicsBinding
import ny.gelato.extessera.feature.edit_character.edit_about.EditAboutFragment
import ny.gelato.extessera.feature.edit_character.EditCharacterView

/**
 * Created by jord.goldberg on 6/22/17.
 */

class EditBasicsFragment : Fragment(), EditCharacterView {

    companion object {
        fun newInstance(id: String?): EditBasicsFragment = EditBasicsFragment().apply {
            arguments = Bundle().apply { putString("id", id) }
        }
    }

    lateinit var id: String

    val model: EditBasicsModel by lazy {
        id = arguments.getString("id", "")
        if (id.isEmpty()) EditBasicsModel()
        else EditBasicsModel(App.component.realm()
                .where(Character::class.java)
                .equalTo("id", id)
                .findFirst())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentEditCharacterBasicsBinding>(
                activity.layoutInflater, R.layout.fragment_edit_character_basics, null, false)
                .apply { viewModel = model }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        activity.title = "Character Basics"
    }

    override fun validateInput(): Boolean = model.name.isNotBlank()

    override fun showNext() {
        if (validateInput()) {
            saveBasics()
            fragmentManager.beginTransaction()
                    .setCustomAnimations(
                            R.anim.slide_in_right,
                            R.anim.slide_out_left,
                            R.anim.slide_in_left,
                            R.anim.slide_out_right)
                    .addToBackStack(null)
                    .replace(R.id.container, EditAboutFragment.newInstance(id))
                    .commit()
        } else showInputError()
    }

    private fun showInputError() {

    }

    private fun saveBasics() {
        App.component.realm().executeTransaction { realm ->
            val character =
                    if (id.isEmpty()) realm.copyToRealm(Character())
                    else realm.where(Character::class.java)
                            .equalTo("id", id)
                            .findFirst()

            character.name = model.name
            character.race = model.race.name
            character.subrace = model.subrace?.name
            character.primary.job = model.job.name
            character.primary.resetLevelTo(model.level)
            character.setExpToLevel()
            character.maxHp = character.primary.hitDieMax() + character.constitution.modifier()
            character.traits.deleteAllFromRealm()
            character.proficiencies.where()
                    .equalTo("origin", Proficiency.Origin.RACE_CLASS.name)
                    .findAll()
                    .deleteAllFromRealm()
            character.setTraitsAndProficiencies()

            id = character.id
        }
    }
}