package ny.gelato.extessera.feature.character_sheet.view_model

import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.data.model.character.Ability
import ny.gelato.extessera.data.model.character.Character
import ny.gelato.extessera.data.model.character.KnownSpell

/**
 * Created by jord.goldberg on 5/12/17.
 */

data class SpellModel(
        val name: String = "No Spells",
        val level: Int = 0,
        val requirements: String = "",
        val range: String = "",
        val type: String = "Click to add one",
        var prepared: Boolean = false,
        var castsSinceLongRest: Int = 0

) : BaseViewModel() {

    constructor(knownSpell: KnownSpell) :
            this(knownSpell.name,
                    knownSpell.level,
                    knownSpell.requirements,
                    knownSpell.range,
                    knownSpell.type,
                    knownSpell.prepared,
                    knownSpell.castsSinceLongRest)

    override fun isSameAs(model: BaseViewModel): Boolean =
            if (model is SpellModel) model.name == name
            else false

    fun updatePrepared(): SpellModel {
        prepared = !prepared
        notifyChange()
        return this.copy().apply { action = Action.UPDATE }
    }

    fun isEmpty(): Boolean = requirements.isEmpty()
}
