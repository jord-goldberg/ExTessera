package ny.gelato.extessera.feature.character_sheet.view_model

import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.data.model.character.Ability
import ny.gelato.extessera.data.model.character.Character

/**
 * Created by jord.goldberg on 5/12/17.
 */

data class AbilitiesModel(
        private val proficiency: Int = 0,
        var strength: Int = 0,
        var dexterity: Int = 0,
        var constitution: Int = 0,
        var intelligence: Int = 0,
        var wisdom: Int = 0,
        var charisma: Int = 0,
        val avatar: AvatarModel = AvatarModel()

) : BaseViewModel() {

    constructor(char: Character) :
            this(char.proficiencyBonus(),
                    char.strength.score,
                    char.dexterity.score,
                    char.constitution.score,
                    char.intelligence.score,
                    char.wisdom.score,
                    char.charisma.score,
                    AvatarModel(char))

    fun isEditable(): Boolean = action == Action.EDIT

    fun edit() {
        action = Action.EDIT
        notifyChange()
    }

    fun longEdit(): Boolean {
        action = Action.EDIT
        notifyChange()
        return true
    }

    fun update(): AbilitiesModel {
        action = Action.VIEW
        notifyChange()
        return this.copy().apply { action = Action.UPDATE }
    }

    fun strUp() {
        strength += 1
        notifyChange()
    }

    fun dexUp() {
        dexterity += 1
        notifyChange()
    }

    fun conUp() {
        constitution += 1
        notifyChange()
    }

    fun intUp() {
        intelligence += 1
        notifyChange()
    }

    fun wisUp() {
        wisdom += 1
        notifyChange()
    }

    fun chaUp() {
        charisma += 1
        notifyChange()
    }

    fun strDown() {
        strength -= 1
        notifyChange()
    }

    fun dexDown() {
        dexterity -= 1
        notifyChange()
    }

    fun conDown() {
        constitution -= 1
        notifyChange()
    }

    fun intDown() {
        intelligence -= 1
        notifyChange()
    }

    fun wisDown() {
        wisdom -= 1
        notifyChange()
    }

    fun chaDown() {
        charisma -= 1
        notifyChange()
    }

    fun showStr(): String = when (this.isEditable()) {
        true -> "$strength"
        false -> Ability.modifyAndFormat(strength)
    }

    fun showDex(): String = when (this.isEditable()) {
        true -> "$dexterity"
        false -> Ability.modifyAndFormat(dexterity)
    }

    fun showCon(): String = when (this.isEditable()) {
        true -> "$constitution"
        false -> Ability.modifyAndFormat(constitution)
    }

    fun showInt(): String = when (this.isEditable()) {
        true -> "$intelligence"
        false -> Ability.modifyAndFormat(intelligence)
    }

    fun showWis(): String = when (this.isEditable()) {
        true -> "$wisdom"
        false -> Ability.modifyAndFormat(wisdom)
    }

    fun showCha(): String = when (this.isEditable()) {
        true -> "$charisma"
        false -> Ability.modifyAndFormat(charisma)
    }
}