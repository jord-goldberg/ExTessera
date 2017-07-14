package ny.gelato.extessera.data.model.character

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

/**
 * Created by jord.goldberg on 6/7/17.
 */

open class Note(
        @PrimaryKey var id: String = UUID.randomUUID().toString(),
        var text: String = "",
        var isDone: Boolean = false

) : RealmObject()