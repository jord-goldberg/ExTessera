package ny.gelato.extessera.data.model.character

import io.realm.RealmObject
import io.realm.annotations.Index

/**
 * Created by jord.goldberg on 8/17/17.
 */

open class Counter(
        @Index var name: String = "",
        var description: String = "",
        var current: Int = 1,
        var max: Int = 1,
        @Index var resetOnShortRest: Boolean = false

) : RealmObject()