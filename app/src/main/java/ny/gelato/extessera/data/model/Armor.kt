package ny.gelato.extessera.data.model

import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey

/**
 * Created by jord.goldberg on 6/19/17.
 */

open class Armor(
        @PrimaryKey var name: String = "",
        @Index var category: String = Armor.Category.LIGHT.formatted,
        var armor: Int = 10,
        @Index var strRequired: Int = 0,
        @Index var stealthDisadvantage: Boolean = false,
        var type: String = name

) : RealmObject() {

    enum class Category(val formatted: String) {
        LIGHT("Light Armor"),
        MEDIUM("Medium Armor"),
        HEAVY("Heavy Armor"),
        SHIELD("Shields")
    }

    enum class Type(val formatted: String, val category: Category) {
        PADDED("Padded", Category.LIGHT),
        LEATHER("Leather", Category.LIGHT),
        STUDDED_LEATHER("Studded leather", Category.LIGHT),
        HIDE("Hide", Category.MEDIUM),
        CHAIN_SHIRT("Chain shirt", Category.MEDIUM),
        SCALE_MAIL("Scale mail", Category.MEDIUM),
        BREASTPLATE("Breastplate", Category.MEDIUM),
        HALF_PLATE("Half plate", Category.MEDIUM),
        RING_MAIL("Ring mail", Category.HEAVY),
        CHAIN_MAIL("Chain mail", Category.HEAVY),
        SPLINT("Splint", Category.HEAVY),
        PLATE("Plate", Category.HEAVY),
        SHIELD("Shield", Category.SHIELD)
    }
}