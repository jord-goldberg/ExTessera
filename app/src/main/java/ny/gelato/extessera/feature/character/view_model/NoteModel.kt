package ny.gelato.extessera.feature.character.view_model

import android.support.design.widget.BottomSheetDialog
import android.text.format.DateUtils
import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.data.model.character.Note
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by jord.goldberg on 6/7/17.
 *
 * @layout bottom_sheet_character_note_create.xml
 * @layout item_character_note.xml
 */

data class NoteModel(
        val id: String = "",
        var text: String = "",
        var created: Date = Date()

) : BaseViewModel() {

    constructor(note: Note) : this(note.id, note.text, note.created)

    override fun isSameAs(model: BaseViewModel): Boolean =
            if (model is NoteModel) model.id == id
            else false

    fun isEmpty(): Boolean = id.isEmpty()

    fun setText(note: CharSequence) {
        text = note.toString()
    }

    fun validateText(): Boolean = text.isNotBlank()

    fun createAndDismiss(sheet: BottomSheetDialog): NoteModel {
        action = Action.CREATE
        sheet.dismiss()
        return this
    }

    fun createdFormatted(): String = DateUtils.getRelativeTimeSpanString(created.time).toString()
}