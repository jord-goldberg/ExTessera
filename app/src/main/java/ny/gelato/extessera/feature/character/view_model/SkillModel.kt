package ny.gelato.extessera.feature.character.view_model

import android.support.design.widget.BottomSheetDialog
import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.data.model.character.Ability
import ny.gelato.extessera.data.model.character.Character
import ny.gelato.extessera.data.model.character.Skill

/**
 * Created by jord.goldberg on 5/12/17.
 *
 * @layout bottom_sheet_character_skill.xml
 * @layout item_character_skill.xml
 */

data class SkillModel(
        val modifier: Int = 0,
        val bonus: Int = 0,
        val type: Skill.Type = Skill.Type.ACROBATICS,
        var proficiency: Skill.Proficiency = Skill.Proficiency.NONE,
        val jackOfAllTrades: Boolean = false,
        val showCheckBox: Boolean = false

) : BaseViewModel() {

    constructor(char: Character, skill: Skill) : this(
            when (skill.type.ability) {
                Ability.Type.STR -> char.strength.modifier()
                Ability.Type.DEX -> char.dexterity.modifier()
                Ability.Type.CON -> char.constitution.modifier()
                Ability.Type.INT -> char.intelligence.modifier()
                Ability.Type.WIS -> char.wisdom.modifier()
                Ability.Type.CHA -> char.charisma.modifier()
            },
            char.proficiencyBonus(),
            skill.type,
            skill.proficiency,
            char.isJackOfAllTrades(),
            char.preferences.editAllSkills)

    override fun isSameAs(model: BaseViewModel): Boolean =
            if (model is SkillModel) model.type == type
            else false

    fun updateProficiency(proficiency: Skill.Proficiency): SkillModel =
            copy(proficiency = proficiency).apply { action = Action.UPDATE }

    fun updateProficiencyAndDismiss(proficiency: Skill.Proficiency, sheet: BottomSheetDialog): SkillModel {
        sheet.dismiss()
        return updateProficiency(proficiency)
    }

    fun showStat(): String = "(${type.ability.formatted})"

    fun showModifier(): String = Ability.format(modifier + when (proficiency) {
        Skill.Proficiency.NONE -> if (jackOfAllTrades) bonus / 2 else 0
        Skill.Proficiency.FULL -> bonus
        Skill.Proficiency.EXPERT -> bonus * 2
    })
}