package ny.gelato.extessera.feature.character_sheet.view_model

import android.support.design.widget.BottomSheetDialog
import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.data.model.character.Character

/**
 * Created by jord.goldberg on 7/11/17.
 */

data class HpModel(
        var current: Int = 0,
        var change: Int = 0

) : BaseViewModel() {

    constructor(character: Character) : this(character.hp)

    fun setChange(hp: CharSequence) {
        if (hp.isEmpty()) change = 0
        else change = hp.toString().toInt()
        notifyChange()
    }

    fun heal(sheet: BottomSheetDialog): HpModel {
        current += change
        notifyChange()
        sheet.dismiss()
        return this
    }

    fun damage(sheet: BottomSheetDialog): HpModel {
        current -= change
        notifyChange()
        sheet.dismiss()
        return this
    }
}