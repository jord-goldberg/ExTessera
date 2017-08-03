package ny.gelato.extessera.feature.character.view_model

import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.data.model.character.Character
import ny.gelato.extessera.data.model.character.Job

/**
 * Created by jord.goldberg on 6/7/17.
 *
 * @layout item_character_level_up.xml
 */

data class LevelUpModel(
        var primary: Job.Type

) : BaseViewModel(Action.UPDATE) {

    constructor(character: Character) :
            this(character.primary.job)

    lateinit var selectedJob: Job.Type

    fun levelUpPrimary(): LevelUpModel {
        selectedJob = primary
        return this
    }
}