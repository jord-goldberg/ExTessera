package ny.gelato.extessera.feature.character_sheet.view_model

import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.data.model.Weapon
import ny.gelato.extessera.data.model.character.Ability
import ny.gelato.extessera.data.model.character.Character
import ny.gelato.extessera.data.model.character.Job
import ny.gelato.extessera.data.model.character.Proficiency

/**
 * Created by jord.goldberg on 6/8/17.
 */

data class WeaponModel(
        val name: String = "",
        val properties: String = "",
        private val damage: String = "",
        private val damageType: String = "",
        private val modifier: Int = 0,
        val proficient: Boolean = false,
        private val proficiencyBonus: Int = 0

) : BaseViewModel() {

    constructor(weapon: Weapon, char: Character) :
            this(weapon.name,
                    weapon.properties,
                    weapon.damage,
                    weapon.damageType,
                    if (!weapon.isRanged) {
                        if (weapon.properties.contains("finesse", true))
                            maxOf(char.dexterity.modifier(), char.strength.modifier())
                        else char.strength.modifier()
                    } else {
                        if (weapon.properties.contains("thrown", true))
                            maxOf(char.dexterity.modifier(), char.strength.modifier())
                        else char.dexterity.modifier()
                    },
                    char.proficiencies.find {
                        it.name == weapon.type && it.type == Proficiency.Type.WEAPON.name
                    } != null,
                    char.proficiencyBonus())

    constructor(char: Character) :
            this("Unarmed Strike",
                    "", "1", Weapon.DamageType.BLUDGEONING,
                    if (char.primary.job == Job.Type.MONK.name)
                        maxOf(char.dexterity.modifier(), char.strength.modifier())
                    else char.strength.modifier(),
                    true,
                    char.proficiencyBonus())

    override fun isSameAs(model: BaseViewModel): Boolean =
            if (model is WeaponModel) model.name == name
            else false

    fun attackBonus(): String = Ability.format(modifier + if (proficient) proficiencyBonus else 0)

    fun damageRoll(): String = "$damage ${if (modifier != 0) Ability.formatDamage(modifier) else ""}".trim()

    fun damageDetail(): String = "${damageRoll()} ($damageType)"
}