package ny.gelato.extessera.feature.character_sheet.view_model

import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.data.model.character.Character

/**
 * Created by jord.goldberg on 7/17/17.
 */

data class DeathSaveModel(
        var successes: Int = 0,
        var failures: Int = 0,
        val avatar: AvatarModel = AvatarModel()

) : BaseViewModel() {

    constructor(character: Character) :
            this(character.successes,
                    character.failures,
                    AvatarModel(character))

    fun edit(): DeathSaveModel = copy().apply { editable = true }

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