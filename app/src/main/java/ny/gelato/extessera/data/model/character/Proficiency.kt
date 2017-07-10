package ny.gelato.extessera.data.model.character

import io.realm.RealmObject

/**
 * Created by jord.goldberg on 6/16/17.
 */

open class Proficiency(
        var type: String = "",
        var name: String = ""

) : RealmObject() {

    enum class Type {
        WEAPON,
        ARMOR,
        TOOL,
        LANGUAGE
    }

    enum class Tool(val formatted: String) {
        ALCHEMIST("Alchemist's supplies") {
            override fun isArtisan(): Boolean = true
            override fun isGaming(): Boolean = false
            override fun isMusical(): Boolean = false
        },
        BREWER("Brewer's supplies") {
            override fun isArtisan(): Boolean = true
            override fun isGaming(): Boolean = false
            override fun isMusical(): Boolean = false
        },
        CALLIGRAPHER("Calligrapher's sools") {
            override fun isArtisan(): Boolean = true
            override fun isGaming(): Boolean = false
            override fun isMusical(): Boolean = false
        },
        CARPENTER("Carpenter's tools") {
            override fun isArtisan(): Boolean = true
            override fun isGaming(): Boolean = false
            override fun isMusical(): Boolean = false
        },
        CARTOGRAPHER("Cartographer's tools") {
            override fun isArtisan(): Boolean = true
            override fun isGaming(): Boolean = false
            override fun isMusical(): Boolean = false
        },
        COBBLER("Cobbler's tools") {
            override fun isArtisan(): Boolean = true
            override fun isGaming(): Boolean = false
            override fun isMusical(): Boolean = false
        },
        COOK("Cook's utensils") {
            override fun isArtisan(): Boolean = true
            override fun isGaming(): Boolean = false
            override fun isMusical(): Boolean = false
        },
        GLASSBLOWER("Glassblower's tools") {
            override fun isArtisan(): Boolean = true
            override fun isGaming(): Boolean = false
            override fun isMusical(): Boolean = false
        },
        JEWELER("Jeweler's tools") {
            override fun isArtisan(): Boolean = true
            override fun isGaming(): Boolean = false
            override fun isMusical(): Boolean = false
        },
        LEATHERWORKER("Leatherworker's tools") {
            override fun isArtisan(): Boolean = true
            override fun isGaming(): Boolean = false
            override fun isMusical(): Boolean = false
        },
        MASON("Mason's tools") {
            override fun isArtisan(): Boolean = true
            override fun isGaming(): Boolean = false
            override fun isMusical(): Boolean = false
        },
        PAINTER("Painter's supplies") {
            override fun isArtisan(): Boolean = true
            override fun isGaming(): Boolean = false
            override fun isMusical(): Boolean = false
        },
        POTTER("Potter's tools") {
            override fun isArtisan(): Boolean = true
            override fun isGaming(): Boolean = false
            override fun isMusical(): Boolean = false
        },
        SMITH("Smith's tools") {
            override fun isArtisan(): Boolean = true
            override fun isGaming(): Boolean = false
            override fun isMusical(): Boolean = false
        },
        TINKER("Tinker's tools") {
            override fun isArtisan(): Boolean = true
            override fun isGaming(): Boolean = false
            override fun isMusical(): Boolean = false
        },
        WEAVER("Weaver's tools") {
            override fun isArtisan(): Boolean = true
            override fun isGaming(): Boolean = false
            override fun isMusical(): Boolean = false
        },
        WOODCARVER("Woodcarver's tools") {
            override fun isArtisan(): Boolean = true
            override fun isGaming(): Boolean = false
            override fun isMusical(): Boolean = false
        },
        DICE("Dice set") {
            override fun isArtisan(): Boolean = false
            override fun isGaming(): Boolean = true
            override fun isMusical(): Boolean = false
        },
        DRAGONCHESS("Dragonchess set") {
            override fun isArtisan(): Boolean = false
            override fun isGaming(): Boolean = true
            override fun isMusical(): Boolean = false
        },
        PLAYING_CARD("Playing card set") {
            override fun isArtisan(): Boolean = false
            override fun isGaming(): Boolean = true
            override fun isMusical(): Boolean = false
        },
        THREE_DRAGON_ANTE("Three-Dragon Ante set") {
            override fun isArtisan(): Boolean = false
            override fun isGaming(): Boolean = true
            override fun isMusical(): Boolean = false
        },
        BAGPIPE("Bagpipes") {
            override fun isArtisan(): Boolean = false
            override fun isGaming(): Boolean = false
            override fun isMusical(): Boolean = true
        },
        DRUM("Drums") {
            override fun isArtisan(): Boolean = false
            override fun isGaming(): Boolean = false
            override fun isMusical(): Boolean = true
        },
        DULCIMER("Dulcimer") {
            override fun isArtisan(): Boolean = false
            override fun isGaming(): Boolean = false
            override fun isMusical(): Boolean = true
        },
        FLUTE("Flute") {
            override fun isArtisan(): Boolean = false
            override fun isGaming(): Boolean = false
            override fun isMusical(): Boolean = true
        },
        LUTE("Lute") {
            override fun isArtisan(): Boolean = false
            override fun isGaming(): Boolean = false
            override fun isMusical(): Boolean = true
        },
        LYRE("Lyre") {
            override fun isArtisan(): Boolean = false
            override fun isGaming(): Boolean = false
            override fun isMusical(): Boolean = true
        },
        HORN("Horn") {
            override fun isArtisan(): Boolean = false
            override fun isGaming(): Boolean = false
            override fun isMusical(): Boolean = true
        },
        PAN_FLUTE("Pan flute") {
            override fun isArtisan(): Boolean = false
            override fun isGaming(): Boolean = false
            override fun isMusical(): Boolean = true
        },
        SHAWM("Shawm") {
            override fun isArtisan(): Boolean = false
            override fun isGaming(): Boolean = false
            override fun isMusical(): Boolean = true
        },
        VIOL("Viol") {
            override fun isArtisan(): Boolean = false
            override fun isGaming(): Boolean = false
            override fun isMusical(): Boolean = true
        },
        DISGUISE("Disguise kit") {
            override fun isArtisan(): Boolean = false
            override fun isGaming(): Boolean = false
            override fun isMusical(): Boolean = false
        },
        FORGERY("Forgery kit") {
            override fun isArtisan(): Boolean = false
            override fun isGaming(): Boolean = false
            override fun isMusical(): Boolean = false
        },
        HERBALISM("Herbalism kit") {
            override fun isArtisan(): Boolean = false
            override fun isGaming(): Boolean = false
            override fun isMusical(): Boolean = false
        },
        NAVIGATOR("Navigator's tools") {
            override fun isArtisan(): Boolean = false
            override fun isGaming(): Boolean = false
            override fun isMusical(): Boolean = false
        },
        POISONER("Poisoner's kit") {
            override fun isArtisan(): Boolean = false
            override fun isGaming(): Boolean = false
            override fun isMusical(): Boolean = false
        },
        THIEF("Thieves' tools") {
            override fun isArtisan(): Boolean = false
            override fun isGaming(): Boolean = false
            override fun isMusical(): Boolean = false
        },
        VEHICLE_LAND("Land vehicles") {
            override fun isArtisan(): Boolean = false
            override fun isGaming(): Boolean = false
            override fun isMusical(): Boolean = false
        },
        VEHICLE_WATER("Water vehicles") {
            override fun isArtisan(): Boolean = false
            override fun isGaming(): Boolean = false
            override fun isMusical(): Boolean = false
        };

        abstract fun isArtisan(): Boolean
        abstract fun isGaming(): Boolean
        abstract fun isMusical(): Boolean
    }

    enum class Language(val formatted: String) {
        COMMON("Common") {
            override fun isCommon(): Boolean = true
            override fun script(): String = COMMON.formatted
        },
        DWARVISH("Dwarvish") {
            override fun isCommon(): Boolean = true
            override fun script(): String = DWARVISH.formatted
        },
        ELVISH("Elvish") {
            override fun isCommon(): Boolean = true
            override fun script(): String = ELVISH.formatted
        },
        GIANT("Giant") {
            override fun isCommon(): Boolean = true
            override fun script(): String = DWARVISH.formatted
        },
        GNOMISH("Gnomish") {
            override fun isCommon(): Boolean = true
            override fun script(): String = DWARVISH.formatted
        },
        GOBLIN("Goblin") {
            override fun isCommon(): Boolean = true
            override fun script(): String = DWARVISH.formatted
        },
        HALFLING("Halfling") {
            override fun isCommon(): Boolean = true
            override fun script(): String = COMMON.formatted
        },
        ORC("Orc") {
            override fun isCommon(): Boolean = true
            override fun script(): String = DWARVISH.formatted
        },
        ABYSSAL("Abyssal") {
            override fun isCommon(): Boolean = false
            override fun script(): String = INFERNAL.formatted
        },
        CELESTIAL("Celestial") {
            override fun isCommon(): Boolean = false
            override fun script(): String = CELESTIAL.formatted
        },
        DRACONIC("Draconic") {
            override fun isCommon(): Boolean = false
            override fun script(): String = DRACONIC.formatted
        },
        DEEP_SPEECH("Deep Speech") {
            override fun isCommon(): Boolean = false
            override fun script(): String = ""
        },
        INFERNAL("Infernal") {
            override fun isCommon(): Boolean = false
            override fun script(): String = INFERNAL.formatted
        },
        PRIMORDIAL("Primordial") {
            override fun isCommon(): Boolean = false
            override fun script(): String = DWARVISH.formatted
        },
        SYLVAN("Sylvan") {
            override fun isCommon(): Boolean = false
            override fun script(): String = ELVISH.script()
        },
        UNDERCOMMON("Undercommon") {
            override fun isCommon(): Boolean = false
            override fun script(): String = ELVISH.formatted
        };

        abstract fun isCommon(): Boolean
        abstract fun script(): String
    }
}