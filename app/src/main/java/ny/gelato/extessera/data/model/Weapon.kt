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
        @Index private var typeName: String = "",
        @Index var isCustom: Boolean = false,
        var description: String = "",
        var bonus: Int = 0

) : RealmObject() {

    val type: Type
        get() = Type.valueOf(typeName)

    enum class Type(val formatted: String, val isSimple: Boolean = true, val ammunitionType: AmmunitionType? = null) {
        CLUB("Club"),
        DAGGER("Dagger"),
        GREATCLUB("Greatclub"),
        HANDAXE("Handaxe"),
        JAVELIN("Javelin"),
        LIGHT_HAMMER("Light hammer"),
        MACE("Mace"),
        QUARTERSTAFF("Quarterstaff"),
        SICKLE("Sickle"),
        SPEAR("Spear"),
        CROSSBOW_LIGHT("Crossbow, light", ammunitionType = AmmunitionType.BOLT),
        DART("Dart"),
        SHORTBOW("Shortbow", ammunitionType = AmmunitionType.ARROW),
        SLING("Sling", ammunitionType = AmmunitionType.BULLET),
        BATTLEAXE("Battleaxe", isSimple = false),
        FLAIL("Flail", isSimple = false),
        GLAIVE("Glaive", isSimple = false),
        GREATAXE("Greataxe", isSimple = false),
        GREATSWORD("Greatsword", isSimple = false),
        HALBERD("Halberd", isSimple = false),
        LANCE("Lance", isSimple = false),
        LONGSWORD("Longsword", isSimple = false),
        MAUL("Maul", isSimple = false),
        MORNINGSTAR("Morningstar", isSimple = false),
        PIKE("Pike", isSimple = false),
        RAPIER("Rapier", isSimple = false),
        SCIMITAR("Scimitar", isSimple = false),
        SHORTSWORD("Shortsword", isSimple = false),
        TRIDENT("Trident", isSimple = false),
        WAR_PICK("War pick", isSimple = false),
        WARHAMMER("Warhammer", isSimple = false),
        WHIP("Whip", isSimple = false),
        BLOWGUN("Blowgun", isSimple = false, ammunitionType = AmmunitionType.NEEDLE),
        CROSSBOW_HAND("Crossbow, hand", isSimple = false, ammunitionType = AmmunitionType.BOLT),
        CROSSBOW_HEAVY("Crossbow, heavy", isSimple = false, ammunitionType = AmmunitionType.BOLT),
        LONGBOW("Longbow", isSimple = false, ammunitionType = AmmunitionType.ARROW),
        NET("Net", isSimple = false)
    }

    enum class AmmunitionType(val formatted: String) {
        ARROW("Arrow"),
        BOLT("Bolt"),
        BULLET("Sling Bullet"),
        NEEDLE("Blowgun Needle")
    }

    object DamageType {
        @JvmField
        val BLUDGEONING = "Bludgeoning"
        @JvmField
        val PIERCING = "Piercing"
        @JvmField
        val SLASHING = "Slashing"
    }

    fun category(): String = "${if (isSimple) "Simple" else "Martial"} ${if (isRanged) "Ranged" else "Melee"}"
}