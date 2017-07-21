package ny.gelato.extessera.feature.character_sheet.view_model

import android.os.Handler
import android.support.design.widget.BottomSheetDialog
import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.data.model.character.Character
import java.text.NumberFormat

/**
 * Created by jord.goldberg on 5/23/17.
 */

data class ExpModel(
        var current: Int = 0,
        var toNextLevel: Int = 0,
        var change: Int = 0

) : BaseViewModel() {

    constructor(character: Character) : this(character.exp, character.expToNextLevel()) {
        action = Action.UPDATE
    }

    fun hint(): String = "+$toNextLevel exp to next level"

    fun showExp(): String = NumberFormat.getInstance().format(current)

    fun setChange(exp: CharSequence) {
        if (exp.isEmpty()) change = 0
        else change = exp.toString().toInt()
        notifyChange()
    }

    fun addExpAndDismiss(sheet: BottomSheetDialog): ExpModel {
        current += change
        notifyChange()
        Handler().postDelayed({ sheet.dismiss() }, 1000)
        return this
    }

    fun setExpAndDismiss(sheet: BottomSheetDialog): ExpModel {
        current = change
        notifyChange()
        Handler().postDelayed({ sheet.dismiss() }, 1000)
        return this
    }
}