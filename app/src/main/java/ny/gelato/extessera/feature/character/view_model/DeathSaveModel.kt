package ny.gelato.extessera.feature.character.view_model

import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.data.model.character.Character

/**
 * Created by jord.goldberg on 7/17/17.
 *
 * @layout item_character_death_saves.xml
 */

data class DeathSaveModel(
        var successes: Int = 0,
        var failures: Int = 0,
        val avatar: AvatarModel = AvatarModel()

) : BaseViewModel() {

    constructor(character: Character) :
            this(character.successes, character.failures, AvatarModel(character)) {
        action = Action.UPDATE
    }

    fun menu(): DeathSaveModel = copy().apply { action = Action.CONTEXT_MENU }

    fun setSuccess(number: Int): DeathSaveModel {
        successes = number
        notifyChange()
        return this
    }

    fun setFailure(number: Int): DeathSaveModel {
        failures = number
        notifyChange()
        return this
    }
}