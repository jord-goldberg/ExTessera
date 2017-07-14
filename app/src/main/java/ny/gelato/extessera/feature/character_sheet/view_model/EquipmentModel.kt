package ny.gelato.extessera.feature.character_sheet.view_model

import android.support.design.widget.BottomSheetDialog
import ny.gelato.extessera.base.BaseViewModel

/**
 * Created by jord.goldberg on 7/12/17.
 */

data class EquipmentModel(
        var name: String = "",
        var amount: Int = 1

) : BaseViewModel() {

    var change = 0

    fun isEmpty(): Boolean = name.isBlank()

    fun showAmount(): String = amount.toString()

    fun setName(new: CharSequence) {
        name = new.toString()
        notifyChange()
    }

    fun setChange(amount: CharSequence) {
        if (amount.isEmpty()) change = 0
        else change = amount.toString().toInt()
    }

    fun save(): EquipmentModel {
        editable = false
        notifyChange()
        return this
    }

    fun add(sheet: BottomSheetDialog): EquipmentModel {
        sheet.dismiss()
        amount += change
        return this
    }

    fun remove(sheet: BottomSheetDialog): EquipmentModel {
        sheet.dismiss()
        amount += change
        return this
    }
}