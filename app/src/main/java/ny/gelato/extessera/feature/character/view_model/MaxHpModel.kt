package ny.gelato.extessera.feature.character.view_model

import android.support.design.widget.BottomSheetDialog
import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.data.model.character.Character

/**
 * Created by jord.goldberg on 7/12/17.
 *
 * @layout bottom_sheet_character_max_hp.xml
 */

data class MaxHpModel(
        var current: Int = 0,
        var change: Int = 0

) : BaseViewModel() {

    constructor(character: Character) : this(character.maxHp) {
        action = Action.UPDATE
    }

    fun setChange(maxHp: CharSequence) {
        if (maxHp.isEmpty()) change = 0
        else change = maxHp.toString().toInt()
    }

    fun plusMaxAndDismiss(sheet: BottomSheetDialog): MaxHpModel {
        sheet.dismiss()
        current += change
        return this
    }

    fun setMaxAndDismiss(sheet: BottomSheetDialog): MaxHpModel {
        sheet.dismiss()
        current = change
        return this
    }

    fun showCurrent(): String = current.toString()
}