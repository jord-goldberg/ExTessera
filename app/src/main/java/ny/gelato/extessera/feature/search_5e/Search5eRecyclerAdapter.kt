package ny.gelato.extessera.feature.search_5e

import ny.gelato.extessera.R
import ny.gelato.extessera.base.BaseViewModelAdapter
import ny.gelato.extessera.data.model.Spell
import ny.gelato.extessera.data.model.Weapon
import javax.inject.Inject

/**
 * Created by jord.goldberg on 5/15/17.
 */

class Search5eRecyclerAdapter @Inject constructor(override val presenter: Search5ePresenter<*>) : BaseViewModelAdapter(presenter) {

    var feed: List<Any> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getViewModelForPosition(position: Int): Any = feed[position]

    override fun getLayoutIdForPosition(position: Int): Int = when (feed[position]) {
        is Spell -> R.layout.item_search_spell
        is Weapon -> R.layout.item_search_weapon
        is String -> R.layout.item_search_header
        else -> R.layout.item_search_footer
    }

    override fun getItemCount(): Int = feed.size
}