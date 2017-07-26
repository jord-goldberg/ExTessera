package ny.gelato.extessera.feature.character.view_model

import android.support.design.widget.BottomSheetDialog
import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.data.model.character.Character

/**
 * Created by jord.goldberg on 7/11/17.
 *
 * @layout bottom_sheet_character_hp.xml
 */

data class HpModel(
        var current: Int = 0,
        val max: Int = 0,
        var change: Int = 0

) : BaseViewModel() {

    constructor(character: Character) : this(character.hp, character.maxHp) {
        action = Action.UPDATE
    }

    fun setChange(hp: CharSequence) {
        if (hp.isEmpty()) change = 0
        else change = hp.toString().toInt()
    }

    fun healAndDismiss(sheet: BottomSheetDialog): HpModel {
        sheet.dismiss()
        current += change
        return this
    }

    fun damageAndDismiss(sheet: BottomSheetDialog): HpModel {
        sheet.dismiss()
        current -= change
        if (current < 0) current = 0
        return this
    }

    fun showCurrent(): String = current.toString()

    fun showOutOfMax(): String = " / $max"
}