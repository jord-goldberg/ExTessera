package ny.gelato.extessera.feature.search_5e.spell_search

import ny.gelato.extessera.data.model.Spell
import ny.gelato.extessera.feature.search_5e.Search5eView
import rx.Observable

/**
 * Created by jord.goldberg on 6/14/17.
 */

interface SpellSearchView : Search5eView {

    fun showResult(result: List<Any>)

    fun showEmpty()

    fun showSpellDetail(spell: Spell)
}