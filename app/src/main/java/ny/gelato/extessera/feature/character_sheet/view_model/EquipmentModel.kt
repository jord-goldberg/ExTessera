package ny.gelato.extessera.feature.character_sheet.view_model

import ny.gelato.extessera.base.BaseViewModel

/**
 * Created by jord.goldberg on 7/12/17.
 */

data class EquipmentModel(
        var name: String,
        var amount: Int = 0

) : BaseViewModel() {

    fun showAmount(): String = amount.toString()

    fun showNone(): String = ""
}