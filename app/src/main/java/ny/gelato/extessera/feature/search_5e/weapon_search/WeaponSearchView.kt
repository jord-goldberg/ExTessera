package ny.gelato.extessera.feature.search_5e.weapon_search

import android.view.View
import ny.gelato.extessera.data.model.Weapon
import ny.gelato.extessera.feature.search_5e.Search5eView

/**
 * Created by jord.goldberg on 6/14/17.
 */

interface WeaponSearchView : Search5eView {

    fun showResult(result: List<Any>)

    fun showEmpty()

    fun showAddWeapon(v: View, weapon: Weapon)
}