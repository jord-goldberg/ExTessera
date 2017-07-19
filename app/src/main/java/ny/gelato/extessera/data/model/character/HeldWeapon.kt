package ny.gelato.extessera.data.model.character

import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey
import java.util.*

/**
 * Created by jord.goldberg on 7/18/17.
 */

open class HeldWeapon(
        @PrimaryKey var id: String = UUID.randomUUID().toString(),
        @Index var name: String = "",
        @Index var isSimple: Boolean = true,
        @Index var isRanged: Boolean = false,
        var damage: String = "",
        var damageType: String = "",
        @Index var properties: String = "",
        @Index var isCustom: Boolean = false,
        var type: String = name,
        var customProperties: String = ""
)