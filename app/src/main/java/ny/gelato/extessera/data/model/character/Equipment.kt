package ny.gelato.extessera.data.model.character

import io.realm.RealmObject
import io.realm.annotations.Index

/**
 * Created by jord.goldberg on 7/13/17.
 */

open class Equipment(
        @Index var name: String = "",
        var number: Int = 1,
        var description: String = ""

) : RealmObject()