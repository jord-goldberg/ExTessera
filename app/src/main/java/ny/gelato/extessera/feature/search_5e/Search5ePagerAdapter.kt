package ny.gelato.extessera.feature.search_5e

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.SparseArray
import android.view.ViewGroup
import ny.gelato.extessera.feature.search_5e.spell_search.SpellSearchFragment
import ny.gelato.extessera.feature.search_5e.weapon_search.WeaponSearchFragment

/**
 * Created by jord.goldberg on 6/14/17.
 */

class Search5ePagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    val fragments = SparseArray<Fragment>()

    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as Fragment
        fragments.put(position, fragment)
        return fragment
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        fragments.remove(position)
        super.destroyItem(container, position, `object`)
    }

    override fun getItem(position: Int): Fragment = when (position) {
        1 -> WeaponSearchFragment.newInstance()
        else -> SpellSearchFragment.newInstance()
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence = when (position) {
        0 -> "Spells"
        1 -> "Weapons"
        else -> "Monsters"
    }

    fun showSpellsForCharacter(job: String, level: Int) {
        (fragments[0] as SpellSearchFragment).apply {
            presenter.toggleJobFilter(job)
            presenter.updateLevelFilter(level)
        }
    }
}