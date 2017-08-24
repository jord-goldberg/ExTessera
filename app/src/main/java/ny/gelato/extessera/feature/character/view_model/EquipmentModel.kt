package ny.gelato.extessera.feature.character.view_model

import android.support.design.widget.BottomSheetDialog
import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.data.model.character.Equipment

/**
 * Created by jord.goldberg on 7/12/17.
 *
 * @layout bottom_sheet_character_equipment_create.xml
 * @layout bottom_sheet_character_equipment_item.xml
 * @layout item_character_equipment_item.xml
 */

data class EquipmentModel(
        var name: String = "",
        var amount: Int = 1,
        val index: Int? = null

) : BaseViewModel() {

    constructor(equipment: Equipment, index: Int?) : this(equipment.name, equipment.number, index)

    var change = 1

    override fun isSameAs(model: BaseViewModel): Boolean =
            if (model is EquipmentModel) name == model.name
            else false

    fun isEmpty(): Boolean = name.isBlank()

    fun showAmount(): String = amount.toString()

    fun setName(new: CharSequence) {
        name = new.toString()
        notifyChange()
    }

    fun validateName(): Boolean = name.isNotBlank()

    fun setChange(amount: CharSequence) {
        change = if (amount.isEmpty()) 1
        else amount.toString().toInt()
    }

    fun createAndDismiss(sheet: BottomSheetDialog): EquipmentModel {
        action = Action.CREATE
        sheet.dismiss()
        return this
    }

    fun add(): EquipmentModel {
        amount += change
        notifyChange()
        return this.copy().apply { action = Action.UPDATE }
    }

    fun removeAndDismissIfZero(sheet: BottomSheetDialog): EquipmentModel {
        amount -= change
        notifyChange()
        return if (amount < 1) {
            amount += change
            sheet.dismiss()
            this.copy().apply { action = Action.DELETE }
        } else this.copy().apply { action = Action.UPDATE }
    }
}