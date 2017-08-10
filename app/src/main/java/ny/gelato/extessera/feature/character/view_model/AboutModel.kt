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
                    character.alignment.formatted,
                    character.about,

                    character.traits
                            .map { it.name }
                            .toString()
                            .substring(1)
                            .dropLast(1),

                    character.primary.features()
                            .toString()
                            .substring(1)
                            .dropLast(1),

                    character.proficienciesFormatted(Proficiency.Type.WEAPON),
                    character.proficienciesFormatted(Proficiency.Type.ARMOR),
                    character.proficienciesFormatted(Proficiency.Type.TOOL),
                    character.proficienciesFormatted(Proficiency.Type.LANGUAGE))

    fun title(): String = "About ${name.substringBefore(" ")}"
}