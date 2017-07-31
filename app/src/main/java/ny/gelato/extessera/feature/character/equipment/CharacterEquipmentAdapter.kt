package ny.gelato.extessera.feature.character.equipment

import android.support.v7.util.DiffUtil
import ny.gelato.extessera.R
import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.base.BaseViewModelAdapter
import ny.gelato.extessera.feature.character.view_model.EquipmentModel
import ny.gelato.extessera.feature.character.view_model.FooterModel
import ny.gelato.extessera.feature.character.view_model.HeaderModel

/**
 * Created by jord.goldberg on 7/26/17.
 */

class CharacterEquipmentAdapter(override val parent: CharacterEquipmentView) : BaseViewModelAdapter(parent) {

    var feed: MutableList<BaseViewModel> = mutableListOf()
        set(value) {
            val diffResult = DiffUtil.calculateDiff(BaseViewModelDiffUtil(field, value))
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun getViewModelForPosition(position: Int): Any = feed[position]

    override fun getLayoutIdForPosition(position: Int): Int = when (feed[position]) {
        is HeaderModel -> R.layout.item_character_equipment_header
        is EquipmentModel -> R.layout.item_character_equipment_item
        is FooterModel -> R.layout.item_character_footer
        else -> throw ModelLayoutException(feed[position]::class.java.simpleName, this::class.java.simpleName)
    }

    override fun getItemCount(): Int = feed.size

}