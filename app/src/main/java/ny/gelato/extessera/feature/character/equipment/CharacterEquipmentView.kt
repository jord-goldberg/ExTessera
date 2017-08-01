package ny.gelato.extessera.feature.character.equipment

import ny.gelato.extessera.base.BaseView
import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.feature.character.view_model.EquipmentModel

/**
 * Created by jord.goldberg on 7/26/17.
 *
 * A pretty straightforward MVC that's a simplification of the character.sheet view. This one handles
 * only equipment, so that the character sheet wouldn't be overrun with equipment items if a
 * character had a large inventory.
 */

interface CharacterEquipmentView : BaseView {

    fun showEquipmentInventory(equipment: MutableList<BaseViewModel>)

    fun showEquipmentItem(equipment: EquipmentModel)

    fun showCreateEquipment()
}