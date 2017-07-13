package ny.gelato.extessera.data.model.character

import io.realm.RealmObject

/**
 * Created by jord.goldberg on 7/13/17.
 */

open class Equipment(
        var number: Int = 0,
        var name: String = "",
        var description: String = ""

) : RealmObject()