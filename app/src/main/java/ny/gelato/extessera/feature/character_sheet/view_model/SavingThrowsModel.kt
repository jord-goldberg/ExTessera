package ny.gelato.extessera.feature.character_sheet.view_model

import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.data.model.character.Ability
import ny.gelato.extessera.data.model.character.Character

/**
 * Created by jord.goldberg on 5/12/17.
 */

data class SavingThrowsModel(
        private val proficiency: Int = 0,
        private val strMod: Int = 0,
        private val dexMod: Int = 0,
        private val conMod: Int = 0,
        private val intMod: Int = 0,
        private val wisMod: Int = 0,
        private val chaMod: Int = 0,
        var strSave: Boolean = false,
        var dexSave: Boolean = false,
        var conSave: Boolean = false,
        var intSave: Boolean = false,
        var wisSave: Boolean = false,
        var chaSave: Boolean = false,
        val avatar: AvatarModel = AvatarModel()

) : BaseViewModel() {

    constructor(char: Character) :
            this(char.proficiencyBonus(),
                    char.strength.modifier(),
                    char.dexterity.modifier(),
                    char.constitution.modifier(),
                    char.intelligence.modifier(),
                    char.wisdom.modifier(),
                    char.charisma.modifier(),
                    char.strength.save,
                    char.dexterity.save,
                    char.constitution.save,
                    char.intelligence.save,
                    char.wisdom.save,
                    char.charisma.save,
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

    fun update(): SavingThrowsModel {
        action = Action.VIEW
        notifyChange()
        return this.copy().apply { action = Action.UPDATE }
    }

    fun toggleStrSave(isProficient: Boolean) {
        strSave = isProficient
    }

    fun toggleDexSave(isProficient: Boolean) {
        dexSave = isProficient
    }

    fun toggleConSave(isProficient: Boolean) {
        conSave = isProficient
    }

    fun toggleIntSave(isProficient: Boolean) {
        intSave = isProficient
    }

    fun toggleWisSave(isProficient: Boolean) {
        wisSave = isProficient
    }

    fun toggleChaSave(isProficient: Boolean) {
        chaSave = isProficient
    }

    fun showStrSave(): String = Ability.format(strMod + if (strSave) proficiency else 0)
    fun showDexSave(): String = Ability.format(dexMod + if (dexSave) proficiency else 0)
    fun showConSave(): String = Ability.format(conMod + if (conSave) proficiency else 0)
    fun showIntSave(): String = Ability.format(intMod + if (intSave) proficiency else 0)
    fun showWisSave(): String = Ability.format(wisMod + if (wisSave) proficiency else 0)
    fun showChaSave(): String = Ability.format(chaMod + if (chaSave) proficiency else 0)
}