package ny.gelato.extessera.feature.search_5e

import android.databinding.BaseObservable
import android.os.Parcelable

/**
 * Created by jord.goldberg on 6/14/17.
 */

abstract class Search5eFilter<in T> : BaseObservable(), Parcelable {

    abstract fun isEmpty(): Boolean

    abstract fun filtering(): String

    abstract fun clear()

    abstract fun restore(filter: T)
}