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
        notes.where()
                .isNull("archived")
                .findAll()
                .mapIndexed { index, note -> NoteModel(note, index) }
                .toMutableList<BaseViewModel>()
                .apply {
                    val character = this@noteModels
                    if (hasToLevelUp()) add(LevelUpModel(character))
                    if (isEmpty()) add(NoteModel(text = "No Notes\nClick to create one"))
                    val title = if (hasToLevelUp()) "Level Up to ${character.expLevel()}" else "Notes"
                    val section = HeaderModel.Section.NOTES
                    add(0, HeaderModel(section, AvatarModel(character), R.menu.menu_character_notes))
                    add(FooterModel(section))
                }

fun Character.skillModels(): List<BaseViewModel> =
        (if (preferences.sortSkillsByAbility) getSkillsSortByAbility(this)
        else getSkillsSortByName(this))
                .apply {
                    val section = HeaderModel.Section.SKILLS
                    val editSkills = preferences.editAllSkills
                    add(0, HeaderModel(section, AvatarModel(this@skillModels), R.menu.menu_character_skills,
                            isEditingSection = editSkills))
                    add(FooterModel(section))
                }

fun Character.weaponModels(): List<BaseViewModel> =
        weapons.mapIndexed { index, heldWeapon -> WeaponModel(heldWeapon, this, index) }
                .toMutableList<BaseViewModel>()
                .apply {
                    val character = this@weaponModels
                    if (weapons.isEmpty().or(primary.job == Job.Type.MONK))
                        add(0, WeaponModel(character))
                    val section = HeaderModel.Section.WEAPONS
                    val titleInfo = if (character.attacksPerAction() > 1)
                        "Attacks per Action:  ${character.attacksPerAction()}" else ""
                    add(0, HeaderModel(section, AvatarModel(character), R.menu.menu_character_weapons, titleInfo))
                    add(FooterModel(section))
                }

fun Character.spellModels(): List<BaseViewModel> {
    if (primary.job == Job.Type.BARBARIAN) return emptyList()
    val spellModels = mutableListOf<BaseViewModel>()

    if (spells.isEmpty())
        spellModels.add(SpellModel())
    else {
        spellModels.addAll(spells.where().equalTo("prepared", true)
                .findAllSorted("level", Sort.DESCENDING, "name", Sort.ASCENDING)
                .mapIndexed { index, knownSpell -> SpellModel(knownSpell, index) })

        spellModels.addAll(spells.where().equalTo("prepared", false)
                .findAllSorted("level", Sort.ASCENDING, "name", Sort.ASCENDING)
                .mapIndexed { index, knownSpell -> SpellModel(knownSpell, index) })
    }

    val section = HeaderModel.Section.SPELLS
    spellModels.add(0, HeaderModel(section, AvatarModel(this), R.menu.menu_character_spells,
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
    spellModels.add(FooterModel(section))
    return spellModels
}

fun Character.equipmentModelsSheet(): List<BaseViewModel> = mutableListOf<BaseViewModel>().apply {
    val character = this@equipmentModelsSheet
    val section = HeaderModel.Section.EQUIPMENT
    add(HeaderModel(section, AvatarModel(character), R.menu.menu_character_equipment))
    for (i in 0 until CoinModel.Type.values().size) {
        add(CoinModel(CoinModel.Type.values()[i], character))
        if (equipment.size <= i) add(EquipmentModel())
        else add(EquipmentModel(equipment[i], i))
    }
    add(FooterModel(section))
    add(EquipmentFooterModel(character))
}

fun Character.equipmentModelsFull(): MutableList<BaseViewModel> = mutableListOf<BaseViewModel>().apply {
    val character = this@equipmentModelsFull
    val title = "Equipment Items"
    val section = HeaderModel.Section.EQUIPMENT
    add(HeaderModel(section, AvatarModel(character), R.menu.menu_character_equipment))
    addAll(equipment.mapIndexed { index, equipment -> EquipmentModel(equipment, index) })
    add(FooterModel(section))
}

private fun getSkillsSortByAbility(character: Character): MutableList<BaseViewModel> =
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
                }

private fun getSkillsSortByName(character: Character): MutableList<BaseViewModel> =
        character.skills.map { SkillModel(character, it) }
                .sortedBy { it.type.nameOrder() }
                .toMutableList()