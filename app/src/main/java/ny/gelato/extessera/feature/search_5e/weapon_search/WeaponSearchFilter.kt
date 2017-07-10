package ny.gelato.extessera.feature.search_5e.weapon_search

import android.os.Parcel
import android.os.Parcelable
import ny.gelato.extessera.feature.search_5e.Search5eFilter

/**
 * Created by jord.goldberg on 6/14/17.
 */

class WeaponSearchFilter(
        var simple: Boolean = false,
        var martial: Boolean = false,
        var melee: Boolean = false,
        var ranged: Boolean = false,
        var finesse: Boolean = false,
        var thrown: Boolean = false) : Search5eFilter<WeaponSearchFilter>(), Parcelable {

    override fun isEmpty(): Boolean = !simple && !martial && !melee && !ranged && !finesse && !thrown

    override fun filtering(): String =
            if (isEmpty()) " all weapons"
            else " ${if (simple) "simple" else if (martial) "martial" else ""}".trimEnd() +
                    " ${if (melee) "melee" else if (ranged) "ranged" else ""}".trimEnd() +
                    " ${if (finesse) "finesse" else ""}".trimEnd() +
                    " ${if (thrown) "thrown" else ""}".trimEnd() +
                    " weapons"

    override fun clear() {
        simple = false
        martial = false
        melee = false
        ranged = false
        finesse = false
        thrown = false
        notifyChange()
    }

    override fun restore(filter: WeaponSearchFilter) {
        simple = filter.simple
        martial = filter.martial
        melee = filter.melee
        ranged = filter.ranged
        finesse = filter.finesse
        thrown = filter.thrown
    }

    fun toggleSimple(isChecked: Boolean) {
        simple = isChecked
        notifyChange()
    }

    fun toggleMartial(isChecked: Boolean) {
        martial = isChecked
        notifyChange()
    }

    fun toggleMelee(isChecked: Boolean) {
        melee = isChecked
        notifyChange()
    }

    fun toggleRanged(isChecked: Boolean) {
        ranged = isChecked
        notifyChange()
    }

    fun toggleFinesse() {
        finesse = !finesse
        notifyChange()
    }

    fun toggleThrown() {
        thrown = !thrown
        notifyChange()
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<WeaponSearchFilter> = object : Parcelable.Creator<WeaponSearchFilter> {
            override fun createFromParcel(source: Parcel): WeaponSearchFilter = WeaponSearchFilter(source)
            override fun newArray(size: Int): Array<WeaponSearchFilter?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            1 == source.readInt(),
            1 == source.readInt(),
            1 == source.readInt(),
            1 == source.readInt(),
            1 == source.readInt(),
            1 == source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt((if (simple) 1 else 0))
        dest.writeInt((if (martial) 1 else 0))
        dest.writeInt((if (melee) 1 else 0))
        dest.writeInt((if (ranged) 1 else 0))
        dest.writeInt((if (finesse) 1 else 0))
        dest.writeInt((if (thrown) 1 else 0))
    }
}