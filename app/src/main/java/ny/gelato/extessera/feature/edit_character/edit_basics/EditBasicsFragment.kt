package ny.gelato.extessera.feature.edit_character.edit_basics

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ny.gelato.extessera.App
import ny.gelato.extessera.R
import ny.gelato.extessera.data.model.character.Ability
import ny.gelato.extessera.data.model.character.Character
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
            character.race = model.race
            character.subrace = model.subrace
            character.primary.job = model.job
            character.primary.level = model.level
            character.setExpToLevel()
            character.proficiencies.deleteAllFromRealm()
            character.setProficiencies()

            if (id.isEmpty()) {
                id = character.id

                character.strength.score = character.primary.job.startingAbilityScores.getOrDefault(Ability.Type.STR, 0) +
                        character.race.abilityScoreIncrease(Ability.Type.STR) +
                        (character.subrace?.abilityScoreIncrease(Ability.Type.STR) ?: 0)

                character.dexterity.score = character.primary.job.startingAbilityScores.getOrDefault(Ability.Type.DEX, 0) +
                        character.race.abilityScoreIncrease(Ability.Type.DEX) +
                        (character.subrace?.abilityScoreIncrease(Ability.Type.DEX) ?: 0)

                character.constitution.score = character.primary.job.startingAbilityScores.getOrDefault(Ability.Type.CON, 0) +
                        character.race.abilityScoreIncrease(Ability.Type.CON) +
                        (character.subrace?.abilityScoreIncrease(Ability.Type.CON) ?: 0)

                character.intelligence.score = character.primary.job.startingAbilityScores.getOrDefault(Ability.Type.INT, 0) +
                        character.race.abilityScoreIncrease(Ability.Type.INT) +
                        (character.subrace?.abilityScoreIncrease(Ability.Type.INT) ?: 0)

                character.wisdom.score = character.primary.job.startingAbilityScores.getOrDefault(Ability.Type.WIS, 0) +
                        character.race.abilityScoreIncrease(Ability.Type.WIS) +
                        (character.subrace?.abilityScoreIncrease(Ability.Type.WIS) ?: 0)

                character.charisma.score = character.primary.job.startingAbilityScores.getOrDefault(Ability.Type.CHA, 0) +
                        character.race.abilityScoreIncrease(Ability.Type.CHA) +
                        (character.subrace?.abilityScoreIncrease(Ability.Type.CHA) ?: 0)

                character.baseHp = character.primary.hitDieMax()
                character.hp = character.maxHp()
            }
        }
    }
}