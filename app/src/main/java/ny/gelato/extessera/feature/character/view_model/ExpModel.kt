package ny.gelato.extessera.feature.character.view_model

import android.os.Handler
import android.support.design.widget.BottomSheetDialog
import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.data.model.character.Character
import java.text.NumberFormat

/**
 * Created by jord.goldberg on 5/23/17.
 *
 * @layout bottom_sheet_character_exp_add.xml
 */

data class ExpModel(
        var current: Int = 0,
        var toNextLevel: Int = 0,
        var change: Int = 0

) : BaseViewModel() {

    constructor(character: Character) : this(character.exp, character.expToNextLevel()) {
        action = Action.UPDATE
    }

    var isWaitingToDismiss = false

    fun hint(): String = "+$toNextLevel exp to next level"

    fun showExp(): String = NumberFormat.getInstance().format(current)

    fun setChange(exp: CharSequence) {
        change = if (exp.isEmpty()) 0
        else exp.toString().toInt()
        notifyChange()
    }

    fun addExpAndDismiss(sheet: BottomSheetDialog): ExpModel {
        current += change
        notifyChange()
        Handler().postDelayed({ sheet.dismiss() }, 1000)
        isWaitingToDismiss = true
        return this
    }

    fun setExpAndDismiss(sheet: BottomSheetDialog): ExpModel {
        current = change
        notifyChange()
        Handler().postDelayed({ sheet.dismiss() }, 1000)
        isWaitingToDismiss = true
        return this
    }
}