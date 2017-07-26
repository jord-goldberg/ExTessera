package ny.gelato.extessera.feature.character.view_model

import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.data.model.Weapon
import ny.gelato.extessera.data.model.character.Character
import ny.gelato.extessera.data.model.character.Proficiency
import ny.gelato.extessera.data.model.character.Trait

/**
 * Created by jord.goldberg on 6/19/17.
 *
 * @layout bottom_sheet_character_about.xml
 */

data class AboutModel(
        val name: String = "",
        val background: String = "",
        val alignment: String = "",
        val about: String = "",
        val racialTraits: String = "",
        val classFeatures: String = "",
        val weaponProficiencies: String = "",
        val armorProficiencies: String = "",
        val toolProficiencies: String = "",
        val languages: String = ""

) : BaseViewModel() {

    constructor(character: Character) :
            this(character.name,
                    character.background,
                    Trait.Alignment.valueOf(character.alignment).formatted,
                    character.about,

                    character.traits
                            .map { it.name }
                            .toString()
                            .substring(1)
                            .dropLast(1),

                    character.primary.features
                            .map { it.name }
                            .toString()
                            .substring(1)
                            .dropLast(1),

                    if (character.proficiencies
                            .map { it.name }
                            .containsAll(Weapon.Type.values().map { it.formatted }))
                        "All Weapons"
                    else if (character.proficiencies
                            .map { it.name }
                            .containsAll(Weapon.Type.values().filter { it.isSimple() }.map { it.formatted }))
                        character.proficiencies.filter {
                            it.type == Proficiency.Type.WEAPON.name
                                    && !(Weapon.Type.values().filter { it.isSimple() }.map { it.formatted }.contains(it.name))
                        }
                                .map { if (it.name.contains(",")) it.name.replace(", ", " (").plus(")") else it.name }
                                .toMutableList()
                                .apply { add(0, "Simple Weapons") }
                                .toString()
                                .substring(1)
                                .dropLast(1)
                    else character.proficiencies.where().equalTo("type", Proficiency.Type.WEAPON.name)
                            .findAll()
                            .map { if (it.name.contains(",")) it.name.replace(", ", " (").plus(")") else it.name }
                            .toString()
                            .substring(1)
                            .dropLast(1),

                    character.proficiencies.where().equalTo("type", Proficiency.Type.ARMOR.name)
                            .findAll()
                            .map { it.name }
                            .toString()
                            .substring(1)
                            .dropLast(1),

                    character.proficiencies.where().equalTo("type", Proficiency.Type.TOOL.name)
                            .findAll()
                            .map { it.name }
                            .toString()
                            .substring(1)
                            .dropLast(1),

                    character.proficiencies.where().equalTo("type", Proficiency.Type.LANGUAGE.name)
                            .findAll()
                            .map { it.name }
                            .toString()
                            .substring(1)
                            .dropLast(1))

    fun title(): String = "About ${name.substringBefore(" ")}"
}