package ny.gelato.extessera.feature.character

import io.realm.Sort
import ny.gelato.extessera.R
import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.data.model.character.Ability
import ny.gelato.extessera.data.model.character.Character
import ny.gelato.extessera.data.model.character.Job
import ny.gelato.extessera.feature.character.view_model.*

/**
 * Created by jord.goldberg on 7/31/17.
 *
 * Extension functions for a Character data model receiver that return Character view models.
 * This is here because I didn't want feature-specific view models imported in the data layer.
 */

fun Character.noteModels(): List<BaseViewModel> =
        notes.map { NoteModel(it) }
                .toMutableList<BaseViewModel>()
                .apply {
                    val character = this@noteModels
                    if (hasToLevelUp()) add(LevelUpModel(character))
                    if (isEmpty()) add(NoteModel(text = "No Notes\nClick to create one"))
                    val title = if (hasToLevelUp()) "Level Up to ${character.expLevel()}" else "Notes"
                    add(0, HeaderModel(title, AvatarModel(character), R.menu.menu_character_notes))
                    add(FooterModel(title))
                }

fun Character.skillModels(): List<BaseViewModel> =
        if (preferences.sortSkillsByAbility) getSkillsSortByAbility(this)
        else getSkillsSortByName(this)

fun Character.weaponModels(): List<BaseViewModel> =
        weapons.map { WeaponModel(it, this) }
                .toMutableList<BaseViewModel>()
                .apply {
                    val character = this@weaponModels
                    if (weapons.isEmpty().or(primary.job == Job.Type.MONK))
                        add(0, WeaponModel(character))
                    val title = "Weapons"
                    add(0, HeaderModel(title, AvatarModel(character), R.menu.menu_character_weapons))
                    add(FooterModel(title))
                }

fun Character.spellModels(): List<BaseViewModel> {
    if (primary.job == Job.Type.BARBARIAN) return emptyList()
    val spellModels = mutableListOf<BaseViewModel>()

    if (spells.isEmpty())
        spellModels.add(SpellModel())
    else {
        spellModels.addAll(spells.where().equalTo("prepared", true)
                .findAllSorted("level", Sort.DESCENDING, "name", Sort.ASCENDING)
                .map { SpellModel(it) })

        spellModels.addAll(spells.where().equalTo("prepared", false)
                .findAllSorted("level", Sort.ASCENDING, "name", Sort.ASCENDING)
                .map { SpellModel(it) })
    }

    val title = "Spells"
    spellModels.add(0, HeaderModel(title, AvatarModel(this), R.menu.menu_character_spells,
            "Spell Attack bonus:  ${Ability.format(proficiencyBonus() + when (primary.castingAbility()) {
                Ability.Type.INT -> intelligence.modifier()
                Ability.Type.WIS -> wisdom.modifier()
                Ability.Type.CHA -> charisma.modifier()
                else -> 0
            })}\nSpell Save DC:  ${8 + proficiencyBonus() + when (primary.castingAbility()) {
                Ability.Type.INT -> intelligence.modifier()
                Ability.Type.WIS -> wisdom.modifier()
                Ability.Type.CHA -> charisma.modifier()
                else -> 0
            }}"))
    spellModels.add(FooterModel(title))
    return spellModels
}

fun Character.equipmentModelsSheet(): List<BaseViewModel> = mutableListOf<BaseViewModel>().apply {
    val character = this@equipmentModelsSheet
    val title = "Equipment"
    add(HeaderModel(title, AvatarModel(character), R.menu.menu_character_equipment))
    for (i in 0 until CoinModel.Type.values().size) {
        add(CoinModel(CoinModel.Type.values()[i], character))
        if (equipment.size <= i) add(EquipmentModel())
        else add(EquipmentModel(equipment[i]))
    }
    add(FooterModel(title))
    add(EquipmentFooterModel(character))
}

fun Character.equipmentModelsFull(): MutableList<BaseViewModel> = mutableListOf<BaseViewModel>().apply {
    val character = this@equipmentModelsFull
    val title = "Equipment Items"
    add(HeaderModel(title, AvatarModel(character), R.menu.menu_character_equipment))
    addAll(equipment.map { EquipmentModel(it) })
    add(FooterModel(title))
}

private fun getSkillsSortByAbility(character: Character): List<BaseViewModel> =
        character.skills.map { SkillModel(character, it) }
                .sortedBy { it.type.abilityOrder() }
                .toMutableList<BaseViewModel>()
                .apply {
                    add(0, SkillSubheaderModel(Ability.Type.STR.formatted))
                    add(1, SkillSubheaderModel(Ability.Type.WIS.formatted))
                    add(4, SkillSubheaderModel(Ability.Type.DEX.formatted))
                    add(12, SkillSubheaderModel(Ability.Type.INT.formatted))
                    add(13, SkillSubheaderModel(Ability.Type.CHA.formatted))
                    add(SkillSubheaderModel(""))
                    val title = "Skills"
                    add(0, HeaderModel(title, AvatarModel(character), R.menu.menu_character_skills))
                    add(FooterModel(title))
                }

private fun getSkillsSortByName(character: Character): List<BaseViewModel> =
        character.skills.map { SkillModel(character, it) }
                .sortedBy { it.type.nameOrder() }
                .toMutableList<BaseViewModel>()
                .apply {
                    val title = "Skills"
                    add(0, HeaderModel(title, AvatarModel(character), R.menu.menu_character_skills))
                    add(FooterModel(title))
                }