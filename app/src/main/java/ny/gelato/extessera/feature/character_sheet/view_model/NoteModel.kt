package ny.gelato.extessera.feature.character_sheet.view_model

import android.support.design.widget.BottomSheetDialog
import ny.gelato.extessera.base.BaseViewModel

/**
 * Created by jord.goldberg on 6/7/17.
 */

data class NoteModel(
        val id: String = "",
        var text: String = "",
        var isDone: Boolean = false

) : BaseViewModel() {

    override fun isSameAs(model: BaseViewModel): Boolean =
            if (model is NoteModel) model.id == id
            else false

    fun isEmpty(): Boolean = id.isEmpty()

    fun toggleDone(checked: Boolean): NoteModel {
        isDone = checked
        return this
    }

    fun setText(note: CharSequence) {
        text = note.toString()
    }

    fun validateText(): Boolean = text.isNotBlank()

    fun createNote(sheet: BottomSheetDialog): NoteModel {
        sheet.dismiss()
        return this
    }
}