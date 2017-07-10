package ny.gelato.extessera.data.model

import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey

/**
 * Created by jord.goldberg on 6/19/17.
 */

open class Item(
        @PrimaryKey var name: String = "",
        @Index var type: String = Item.Type.BASIC.name

) : RealmObject() {

    enum class Type(val formatted: String) {
        BASIC("Basic"),
        TOOL("Tool")
    }
}