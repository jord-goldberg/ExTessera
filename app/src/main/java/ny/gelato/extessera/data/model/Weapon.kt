package ny.gelato.extessera.data.model

import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey

/**
 * Created by jord.goldberg on 4/27/17.
 */

open class Weapon(
        @PrimaryKey var name: String = "",
        @Index var isSimple: Boolean = true,
        @Index var isRanged: Boolean = false,
        var damage: String = "",
        var damageType: String = DamageType.BLUDGEONING,
        @Index var properties: String = "",
        @Index var type: String = name,
        @Index var isCustom: Boolean = false,
        var description: String = "",
        var bonus: Int = 0

) : RealmObject() {

    enum class Type(val formatted: String) {
        CLUB("Club") {
            override fun isSimple(): Boolean = true
        },
        DAGGER("Dagger") {
            override fun isSimple(): Boolean = true
        },
        GREATCLUB("Greatclub") {
            override fun isSimple(): Boolean = true
        },
        HANDAXE("Handaxe") {
            override fun isSimple(): Boolean = true
        },
        JAVELIN("Javelin") {
            override fun isSimple(): Boolean = true
        },
        LIGHT_HAMMER("Light hammer") {
            override fun isSimple(): Boolean = true
        },
        MACE("Mace") {
            override fun isSimple(): Boolean = true
        },
        QUARTERSTAFF("Quarterstaff") {
            override fun isSimple(): Boolean = true
        },
        SICKLE("Sickle") {
            override fun isSimple(): Boolean = true
        },
        SPEAR("Spear") {
            override fun isSimple(): Boolean = true
        },
        CROSSBOW_LIGHT("Crossbow, light") {
            override fun isSimple(): Boolean = true
        },
        DART("Dart") {
            override fun isSimple(): Boolean = true
        },
        SHORTBOW("Shortbow") {
            override fun isSimple(): Boolean = true
        },
        SLING("Sling") {
            override fun isSimple(): Boolean = true
        },
        BATTLEAXE("Battleaxe") {
            override fun isSimple(): Boolean = false
        },
        FLAIL("Flail") {
            override fun isSimple(): Boolean = false
        },
        GLAIVE("Glaive") {
            override fun isSimple(): Boolean = false
        },
        GREATAXE("Greataxe") {
            override fun isSimple(): Boolean = false
        },
        GREATSWORD("Greatsword") {
            override fun isSimple(): Boolean = false
        },
        HALBERD("Halberd") {
            override fun isSimple(): Boolean = false
        },
        LANCE("Lance") {
            override fun isSimple(): Boolean = false
        },
        LONGSWORD("Longsword") {
            override fun isSimple(): Boolean = false
        },
        MAUL("Maul") {
            override fun isSimple(): Boolean = false
        },
        MORNINGSTAR("Morningstar") {
            override fun isSimple(): Boolean = false
        },
        PIKE("Pike") {
            override fun isSimple(): Boolean = false
        },
        RAPIER("Rapier") {
            override fun isSimple(): Boolean = false
        },
        SCIMITAR("Scimitar") {
            override fun isSimple(): Boolean = false
        },
        SHORTSWORD("Shortsword") {
            override fun isSimple(): Boolean = false
        },
        TRIDENT("Trident") {
            override fun isSimple(): Boolean = false
        },
        WAR_PICK("War pick") {
            override fun isSimple(): Boolean = false
        },
        WARHAMMER("Warhammer") {
            override fun isSimple(): Boolean = false
        },
        WHIP("Whip") {
            override fun isSimple(): Boolean = false
        },
        BLOWGUN("Blowgun") {
            override fun isSimple(): Boolean = false
        },
        CROSSBOW_HAND("Crossbow, hand") {
            override fun isSimple(): Boolean = false
        },
        CROSSBOW_HEAVY("Crossbow, heavy") {
            override fun isSimple(): Boolean = false
        },
        LONGBOW("Longbow") {
            override fun isSimple(): Boolean = false
        },
        NET("Net") {
            override fun isSimple(): Boolean = false
        };

        abstract fun isSimple(): Boolean
    }

    object DamageType {
        @JvmField val BLUDGEONING = "Bludgeoning"
        @JvmField val PIERCING = "Piercing"
        @JvmField val SLASHING = "Slashing"
    }

    fun category(): String = "${if (isSimple) "Simple" else "Martial"} ${if (isRanged) "Ranged" else "Melee"}"
}