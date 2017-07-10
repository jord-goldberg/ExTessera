package ny.gelato.extessera.feature.search_5e.spell_search

import android.os.Parcel
import android.os.Parcelable
import ny.gelato.extessera.feature.search_5e.Search5eFilter

/**
 * Created by jord.goldberg on 5/22/17.
 */

class SpellSearchFilter(
        val jobs: MutableList<String> = mutableListOf(),
        val schools: MutableList<String> = mutableListOf(),
        var level: Int = 9,
        var ritual: Boolean = false,
        var sortByLevel: Boolean = true) : Search5eFilter<SpellSearchFilter>() {

    override fun isEmpty(): Boolean = jobs.isEmpty() && schools.isEmpty() && level == 9 && !ritual

    override fun filtering(): String =
            if (isEmpty()) " all spells"
            else " ${jobs.toString().trim('[', ']')}".trimEnd() +
                    " ${schools.toString().trim('[', ']')}".trimEnd() +
                    " ${if (ritual) "rituals" else "spells"}" +
                    " ${if (level < 9) levelDescription().replace("...", "") else ""}".trimEnd()

    override fun clear() {
        jobs.clear()
        schools.clear()
        level = 9
        ritual = false
        notifyChange()
    }

    override fun restore(filter: SpellSearchFilter) {
        clear()
        jobs.addAll(filter.jobs)
        schools.addAll(filter.schools)
        level = filter.level
        ritual = filter.ritual
        sortByLevel = filter.sortByLevel
        notifyChange()
    }

    fun levelDescription(): String =
            if (level == 0) "...only cantrips"
            else if (level > 8) "...of all levels"
            else "...up to level $level"

    fun toggleJob(job: String) {
        if (jobs.contains(job)) jobs.remove(job)
        else jobs.add(job)
        notifyChange()
    }

    fun toggleSchool(school: String) {
        if (schools.contains(school)) schools.remove(school)
        else schools.add(school)
        notifyChange()
    }

    fun updateLevel(level: Int) {
        this.level = level
        notifyChange()
    }

    fun toggleRitual() {
        ritual = !ritual
        notifyChange()
    }

    fun toggleSort() {
        sortByLevel = !sortByLevel
        notifyChange()
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<SpellSearchFilter> = object : Parcelable.Creator<SpellSearchFilter> {
            override fun createFromParcel(source: Parcel): SpellSearchFilter = SpellSearchFilter(source)
            override fun newArray(size: Int): Array<SpellSearchFilter?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.createStringArrayList(),
            source.createStringArrayList(),
            source.readInt(),
            1 == source.readInt(),
            1 == source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeStringList(jobs)
        dest.writeStringList(schools)
        dest.writeInt(level)
        dest.writeInt((if (ritual) 1 else 0))
        dest.writeInt((if (sortByLevel) 1 else 0))
    }
}