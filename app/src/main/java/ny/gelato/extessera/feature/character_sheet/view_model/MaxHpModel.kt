package ny.gelato.extessera.feature.character_sheet.view_model

import android.support.design.widget.BottomSheetDialog
import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.data.model.character.Character

/**
 * Created by jord.goldberg on 7/12/17.
 */

data class MaxHpModel(
        var current: Int = 0,
        var change: Int = 0

) : BaseViewModel() {

    constructor(character: Character) : this(character.maxHp)

    fun setChange(maxHp: CharSequence) {
        if (maxHp.isEmpty()) change = 0
        else change = maxHp.toString().toInt()
    }

    fun plusToMax(sheet: BottomSheetDialog): MaxHpModel {
        sheet.dismiss()
        current += change
        return this
    }

    fun setNewMax(sheet: BottomSheetDialog): MaxHpModel {
        sheet.dismiss()
        current = change
        return this
    }

    fun showCurrent(): String = current.toString()
}