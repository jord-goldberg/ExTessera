package ny.gelato.extessera.feature.character.equipment

import ny.gelato.extessera.base.BaseView
import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.feature.character.view_model.EquipmentModel

/**
 * Created by jord.goldberg on 7/26/17.
 */

interface CharacterEquipmentView : BaseView {

    fun showEquipmentInventory(equipment: MutableList<BaseViewModel>)

    fun showEquipmentItem(equipment: EquipmentModel)

    fun showCreateEquipment()
}