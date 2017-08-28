package ny.gelato.extessera.data.model.character

import io.realm.RealmObject
import io.realm.annotations.Index
import ny.gelato.extessera.data.model.Weapon

/**
 * Created by jord.goldberg on 7/13/17.
 */

open class Equipment(
        @Index var name: String = "",
        var number: Int = 1,
        var description: String = "",
        @Index private var ammunitionTypeName: String? = null

) : RealmObject() {

    val ammunitionType: Weapon.AmmunitionType?
        get() = ammunitionTypeName?.let { Weapon.AmmunitionType.valueOf(it) }
}