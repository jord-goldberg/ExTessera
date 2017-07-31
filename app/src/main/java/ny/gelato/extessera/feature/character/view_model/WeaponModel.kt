package ny.gelato.extessera.feature.character.view_model

import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.data.model.Weapon
import ny.gelato.extessera.data.model.character.*

/**
 * Created by jord.goldberg on 6/8/17.
 *
 * @layout bottom_sheet_character_weapon.xml
 * @layout item_character_weapon.xml
 */

data class WeaponModel(
        val id: String = "",
        val name: String = "",
        private val properties: String = "",
        private val damage: String = "",
        private val damageType: String = "",
        private val modifier: Int = 0,
        val proficient: Boolean = false,
        private val proficiencyBonus: Int = 0,
        val type: String = "",
        val isCustom: Boolean = false,
        val description: String = "",
        val bonus: Int = 0

) : BaseViewModel() {

    constructor(weapon: HeldWeapon, char: Character) :
            this(weapon.id,
                    weapon.name,
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
                    (char.proficiencies.where().equalTo("name", weapon.type).findFirst() != null)
                            .or(weapon.isProficient),
                    char.proficiencyBonus(),
                    weapon.type,
                    weapon.isCustom,
                    weapon.description,
                    weapon.bonus)

    constructor(char: Character) :
            this("", "Unarmed Strike",
                    "", "1", Weapon.DamageType.BLUDGEONING,
                    if (char.primary.job == Job.Type.MONK.name)
                        maxOf(char.dexterity.modifier(), char.strength.modifier())
                    else char.strength.modifier(),
                    true,
                    char.proficiencyBonus())

    override fun isSameAs(model: BaseViewModel): Boolean =
            if (model is WeaponModel) model.id == id
            else false

    fun properties(): String =
            if (isCustom) "Custom" +
                    (if (bonus != 0) ", bonus (${Ability.format(bonus)})" else "") +
                    (if (properties.isNotEmpty()) ", ${properties.decapitalize()}" else "")
            else properties

    fun attackBonus(): String = Ability.format(modifier + bonus + if (proficient) proficiencyBonus else 0)

    fun damageRoll(): String = "$damage ${if (modifier != 0 || bonus != 0) Ability.formatDamage(modifier + bonus) else ""}".trim()

    fun damageDetail(): String = "${damageRoll()} ($damageType)"
}