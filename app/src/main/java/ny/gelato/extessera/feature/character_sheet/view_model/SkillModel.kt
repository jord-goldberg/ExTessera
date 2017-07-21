package ny.gelato.extessera.feature.character_sheet.view_model

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
        var jackOfAllTrades: Boolean = false

) : BaseViewModel() {

    constructor(char: Character, skill: Skill) : this(
            when (Skill.Type.valueOf(skill.type).ability()) {
                Ability.Type.STR -> char.strength.modifier()
                Ability.Type.DEX -> char.dexterity.modifier()
                Ability.Type.CON -> char.constitution.modifier()
                Ability.Type.INT -> char.intelligence.modifier()
                Ability.Type.WIS -> char.wisdom.modifier()
                Ability.Type.CHA -> char.charisma.modifier()
            },
            char.proficiencyBonus(),
            Skill.Type.valueOf(skill.type),
            Skill.Proficiency.valueOf(skill.proficiency),
            char.isJackOfAllTrades())

    override fun isSameAs(model: BaseViewModel): Boolean =
            if (model is SkillModel) model.type == type
            else false

    fun showStat(): String = "(${type.ability().formatted})"

    fun none(sheet: BottomSheetDialog): SkillModel {
        sheet.dismiss()
        return copy(proficiency = Skill.Proficiency.NONE).apply { action = Action.UPDATE }
    }

    fun full(sheet: BottomSheetDialog): SkillModel {
        sheet.dismiss()
        return copy(proficiency = Skill.Proficiency.FULL).apply { action = Action.UPDATE }
    }

    fun expert(sheet: BottomSheetDialog): SkillModel {
        sheet.dismiss()
        return copy(proficiency = Skill.Proficiency.EXPERT).apply { action = Action.UPDATE }
    }

    fun showModifier(): String = Ability.format(modifier + when (proficiency) {
        Skill.Proficiency.NONE -> if (jackOfAllTrades) bonus / 2 else 0
        Skill.Proficiency.FULL -> bonus
        Skill.Proficiency.EXPERT -> bonus * 2
    })
}