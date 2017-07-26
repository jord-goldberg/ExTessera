package ny.gelato.extessera.feature.character.view_model

import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.data.model.character.Character

/**
 * Created by jord.goldberg on 7/18/17.
 *
 * @layout item_character_equipment_footer.xml
 */

data class EquipmentFooterModel(
        val equipmentSize: Int

) : BaseViewModel() {

    constructor(character: Character) : this(character.equipment.size)

    fun showAllItemsText(): String = "more equipment items ($equipmentSize)"
}