package ny.gelato.extessera.data.model.character

import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey
import ny.gelato.extessera.data.model.Weapon
import ny.gelato.extessera.feature.character_sheet.view_model.WeaponCreateModel
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
        @Index var type: String = name,
        @Index var isCustom: Boolean = false,
        var description: String = "",
        var bonus: Int = 0,
        var isProficient: Boolean = false

) : RealmObject() {

    constructor(weapon: Weapon) :
            this(name = weapon.name,
                    isSimple = weapon.isSimple,
                    isRanged = weapon.isRanged,
                    damage = weapon.damage,
                    damageType = weapon.damageType,
                    properties = weapon.properties,
                    type = weapon.type)

    constructor(model: WeaponCreateModel, weapon: Weapon) :
            this(name = model.name,
                    isSimple = weapon.isSimple,
                    isRanged = weapon.isRanged,
                    damage = weapon.damage,
                    damageType = weapon.damageType,
                    properties = weapon.properties,
                    type = weapon.type,
                    isCustom = true,
                    description = model.description,
                    bonus = model.bonus,
                    isProficient = model.isProficient)
}