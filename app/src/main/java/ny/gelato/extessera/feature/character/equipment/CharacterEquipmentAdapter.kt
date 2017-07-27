package ny.gelato.extessera.feature.character.equipment

import android.support.v7.util.DiffUtil
import ny.gelato.extessera.R
import ny.gelato.extessera.base.BaseViewModelAdapter
import ny.gelato.extessera.feature.character.view_model.EquipmentModel

/**
 * Created by jord.goldberg on 7/26/17.
 */

class CharacterEquipmentAdapter(override val parent: CharacterEquipmentView) : BaseViewModelAdapter(parent) {

    var feed: MutableList<EquipmentModel> = mutableListOf()
        set(value) {
            val diffResult = DiffUtil.calculateDiff(BaseViewModelDiffUtil(field, value))
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun getViewModelForPosition(position: Int): Any = feed[position]

    override fun getLayoutIdForPosition(position: Int): Int = R.layout.item_character_equipment_item

    override fun getItemCount(): Int = feed.size

}