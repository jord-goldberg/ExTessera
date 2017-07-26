package ny.gelato.extessera.feature.character.view_model

import android.support.design.widget.BottomSheetDialog
import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.data.model.character.Ability
import ny.gelato.extessera.data.model.character.Character

/**
 * Created by jord.goldberg on 5/23/17.
 *
 * @layout item_character_status.xml
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
        val maxDice: Int,
        val hitDie: String,
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
                    maxDice = character.primary.level,
                    hitDie = character.primary.hitDieMaxFormatted(),
                    avatar = AvatarModel(character))

    var isHpChanged: Boolean = false

    var isArmorEditable: Boolean = false
        get() = field || action == Action.EDIT

    var isInitiativeEditable: Boolean = false
        get() = field || action == Action.EDIT

    var isSpeedEditable: Boolean = false
        get() = field || action == Action.EDIT

    var isHitDiceEditable: Boolean = false
        get() = field || action == Action.EDIT

    fun isEditable(): Boolean = isHpChanged || isArmorEditable || isInitiativeEditable
            || isSpeedEditable || isHitDiceEditable || action == Action.EDIT

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

    fun menu(): StatusModel = this.copy().apply { action = Action.CONTEXT_MENU }

    fun update(): StatusModel {
        action = Action.VIEW
        isHpChanged = false
        isArmorEditable = false
        isInitiativeEditable = false
        isSpeedEditable = false
        isHitDiceEditable = false
        notifyChange()
        return this.copy().apply { action = Action.UPDATE }
    }

    fun longRestAndDismiss(sheet: BottomSheetDialog): StatusModel {
        sheet.dismiss()
        hp = maxHp
        dice = minOf(dice + 2, maxDice)
        action = Action.UPDATE
        return this
    }

    fun hpUp() {
        hp += 1
        isHpChanged = true
        notifyChange()
    }

    fun hpDown() {
        if (hp > 0) {
            hp -= 1
            isHpChanged = true
            notifyChange()
        }
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
        if (dice < maxDice) {
            dice += 1
            notifyChange()
        }
    }

    fun hitDiceDown() {
        if (dice > 0) {
            dice -= 1
            notifyChange()
        }
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

    fun showEditHp(): HpModel = HpModel()

    fun showEditMaxHp(): MaxHpModel = MaxHpModel()
}