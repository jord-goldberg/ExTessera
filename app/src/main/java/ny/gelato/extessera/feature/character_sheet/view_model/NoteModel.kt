package ny.gelato.extessera.feature.character_sheet.view_model

import ny.gelato.extessera.base.BaseViewModel

/**
 * Created by jord.goldberg on 6/7/17.
 */

data class NoteModel(
        val text: String = "",
        var isDone: Boolean = false

) : BaseViewModel() {

    override fun isSameAs(model: BaseViewModel): Boolean =
            if (model is NoteModel) model.text == text
            else false

    fun toggleDone(checked: Boolean): NoteModel {
        isDone = checked
        return this
    }
}