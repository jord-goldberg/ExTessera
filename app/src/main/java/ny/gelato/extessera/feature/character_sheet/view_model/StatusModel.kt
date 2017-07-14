package ny.gelato.extessera.feature.character_sheet.view_model

import android.support.design.widget.BottomSheetDialog
import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.data.model.character.Ability
import ny.gelato.extessera.data.model.character.Character

/**
 * Created by jord.goldberg on 5/23/17.
 */

data class StatusModel(
        var hp: Int,
        val maxHp: Int,
        var armor: Int,
        var initiative: Int,
        var speed: Int,
        val proficiencyBonus: Int,
        val passiveWisdom: Int,
        var dice: Int,
        val hitDie: String,
        val dc: Int,
        val dcAbility: Ability.Type,
        val isDcEditable: Boolean = false,
        val avatar: AvatarModel

) : BaseViewModel() {

    constructor(character: Character) :
            this(hp = character.hp,
                    maxHp = character.maxHp,
                    armor = character.armor + character.dexterity.modifier(),
                    initiative = character.initiative + character.dexterity.modifier() +
                            if (character.isJackOfAllTrades()) character.proficiencyBonus() / 2
                            else 0,
                    speed = character.speed,
                    proficiencyBonus = character.proficiencyBonus(),
                    passiveWisdom = 10 + character.wisdom.modifier(),
                    dice = character.primary.dice,
                    hitDie = character.primary.hitDieMaxFormatted(),
                    avatar = AvatarModel(character),
                    dcAbility = Ability.Type.valueOf(character.preferences.dcAbility),
                    dc = 8 + when (Ability.Type.valueOf(character.preferences.dcAbility)) {
                        Ability.Type.STR -> character.strength.modifier()
                        Ability.Type.DEX -> character.dexterity.modifier()
                        Ability.Type.CON -> character.constitution.modifier()
                        Ability.Type.INT -> character.intelligence.modifier()
                        Ability.Type.WIS -> character.wisdom.modifier()
                        Ability.Type.CHA -> character.charisma.modifier()
                    } + character.proficiencyBonus())

    var isHpChanged: Boolean = false

    var isArmorEditable: Boolean = false
        get() = field || editable

    var isInitiativeEditable: Boolean = false
        get() = field || editable

    var isSpeedEditable: Boolean = false
        get() = field || editable

    var isHitDiceEditable: Boolean = false
        get() = field || editable

    fun isEditable(): Boolean = isHpChanged || isArmorEditable || isInitiativeEditable || isSpeedEditable || isHitDiceEditable || editable

    fun editArmor(): Boolean {
        isArmorEditable = true
        notifyChange()
        return true
    }

    fun editInitiative(): Boolean {
        isInitiativeEditable = true
        notifyChange()
        return true
    }

    fun editSpeed(): Boolean {
        isSpeedEditable = true
        notifyChange()
        return true
    }

    fun editHitDice(): Boolean {
        isHitDiceEditable = true
        notifyChange()
        return true
    }

    fun editDc(): StatusModel {
        return copy(isDcEditable = true)
    }

    fun update(): StatusModel {
        editable = false
        isHpChanged = false
        isArmorEditable = false
        isInitiativeEditable = false
        isSpeedEditable = false
        isHitDiceEditable = false
        notifyChange()
        return this
    }

    fun update(dcAbility: Ability.Type, sheet: BottomSheetDialog): StatusModel {
        editable = false
        isHpChanged = false
        isArmorEditable = false
        isInitiativeEditable = false
        isSpeedEditable = false
        notifyChange()
        sheet.dismiss()
        return copy(dcAbility = dcAbility)
    }

    fun hpUp() {
        hp += 1
        isHpChanged = true
        notifyChange()
    }

    fun hpDown() {
        hp -= 1
        isHpChanged = true
        notifyChange()
    }

    fun armorUp() {
        armor += 1
        notifyChange()
    }

    fun armorDown() {
        armor -= 1
        notifyChange()
    }

    fun initiativeUp() {
        initiative += 1
        notifyChange()
    }

    fun initiativeDown() {
        initiative -= 1
        notifyChange()
    }

    fun speedUp() {
        speed += 5
        notifyChange()
    }

    fun speedDown() {
        speed -= 5
        notifyChange()
    }

    fun hitDiceUp() {
        dice += 1
        notifyChange()
    }

    fun hitDiceDown() {
        dice -= 1
        notifyChange()
    }

    fun showHp(): String = "$hp"

    fun showOutOfMaxHp(): String = " / $maxHp hp"

    fun showArmor(): String = "$armor"

    fun showInitiative(): String = Ability.format(initiative)

    fun showSpeed(): String = "$speed"

    fun showProficiencyBonus(): String = Ability.format(proficiencyBonus)

    fun showPassiveWisdom(): String = "$passiveWisdom"

    fun showDiceNum(): String = dice.toString()

    fun showHitDice(): String = "Hit Dice\n($hitDie)"

    fun showDc(): String = "$dc"

    fun showEditHp(): HpModel = HpModel()

    fun showEditMaxHp(): MaxHpModel = MaxHpModel()
}