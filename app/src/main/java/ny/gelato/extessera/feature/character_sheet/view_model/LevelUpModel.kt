package ny.gelato.extessera.feature.character_sheet.view_model

import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.data.model.character.Character
import ny.gelato.extessera.data.model.character.Job

/**
 * Created by jord.goldberg on 6/7/17.
 */

data class LevelUpModel(
        var primary: Job.Type

) : BaseViewModel() {

    constructor(character: Character) :
            this(Job.Type.valueOf(character.primary.job))

    lateinit var selectedJob: Job.Type

    fun levelUpPrimary(): LevelUpModel {
        selectedJob = primary
        return this
    }
}