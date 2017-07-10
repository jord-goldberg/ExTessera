package ny.gelato.extessera.data.model.character

import io.realm.RealmObject

/**
 * Created by jord.goldberg on 6/7/17.
 */

open class Note(
        var text: String = "",
        var isDone: Boolean = false

) : RealmObject()