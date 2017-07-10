package ny.gelato.extessera.feature.edit_character.edit_proficiencies

import ny.gelato.extessera.R
import ny.gelato.extessera.base.BaseViewModelAdapter

/**
 * Created by jord.goldberg on 6/26/17.
 */

class EditProficienciesAdapter : BaseViewModelAdapter() {

    var feed: List<Any> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getViewModelForPosition(position: Int): Any = feed[position]

    override fun getLayoutIdForPosition(position: Int): Int = when (feed[position]) {
        is EditProficiencyModel -> R.layout.item_edit_proficiency
        else -> R.layout.item_edit_proficiency_header
    }

    override fun getItemCount(): Int = feed.size
}