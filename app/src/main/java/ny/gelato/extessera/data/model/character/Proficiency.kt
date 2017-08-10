package ny.gelato.extessera.data.model.character

import io.realm.RealmObject
import io.realm.annotations.Index

/**
 * Created by jord.goldberg on 6/16/17.
 */

open class Proficiency(
        @Index private var typeName: String = "",
        @Index var name: String = ""

) : RealmObject() {

    val type: Type get() = Type.valueOf(typeName)

    enum class Type {
        WEAPON, ARMOR, TOOL, LANGUAGE
    }

    enum class Tool(val formatted: String, val type: Type) {
        ALCHEMIST("Alchemist's supplies", Type.ARTISAN),
        BREWER("Brewer's supplies", Type.ARTISAN),
        CALLIGRAPHER("Calligrapher's tools", Type.ARTISAN),
        CARPENTER("Carpenter's tools", Type.ARTISAN),
        CARTOGRAPHER("Cartographer's tools", Type.ARTISAN),
        COBBLER("Cobbler's tools", Type.ARTISAN),
        COOK("Cook's utensils", Type.ARTISAN),
        GLASSBLOWER("Glassblower's tools", Type.ARTISAN),
        JEWELER("Jeweler's tools", Type.ARTISAN),
        LEATHERWORKER("Leatherworker's tools", Type.ARTISAN),
        MASON("Mason's tools", Type.ARTISAN),
        PAINTER("Painter's supplies", Type.ARTISAN),
        POTTER("Potter's tools", Type.ARTISAN),
        SMITH("Smith's tools", Type.ARTISAN),
        TINKER("Tinker's tools", Type.ARTISAN),
        WEAVER("Weaver's tools", Type.ARTISAN),
        WOODCARVER("Woodcarver's tools", Type.ARTISAN),

        DICE("Dice set", Type.GAMING),
        DRAGONCHESS("Dragonchess set", Type.GAMING),
        PLAYING_CARD("Playing card set", Type.GAMING),
        THREE_DRAGON_ANTE("Three-Dragon Ante set", Type.GAMING),

        BAGPIPE("Bagpipes", Type.MUSIC),
        DRUM("Drums", Type.MUSIC),
        DULCIMER("Dulcimer", Type.MUSIC),
        FLUTE("Flute", Type.MUSIC),
        LUTE("Lute", Type.MUSIC),
        LYRE("Lyre", Type.MUSIC),
        HORN("Horn", Type.MUSIC),
        PAN_FLUTE("Pan flute", Type.MUSIC),
        SHAWM("Shawm", Type.MUSIC),
        VIOL("Viol", Type.MUSIC),

        DISGUISE("Disguise kit", Type.OTHER),
        FORGERY("Forgery kit", Type.OTHER),
        HERBALISM("Herbalism kit", Type.OTHER),
        NAVIGATOR("Navigator's tools", Type.OTHER),
        POISONER("Poisoner's kit", Type.OTHER),
        THIEF("Thieves' tools", Type.OTHER),
        VEHICLE_LAND("Land vehicles", Type.OTHER),
        VEHICLE_WATER("Water vehicles", Type.OTHER);

        enum class Type {
            ARTISAN, GAMING, MUSIC, OTHER
        }
    }

    enum class Language(val formatted: String, val isCommon: Boolean = true, val script: String = formatted) {
        COMMON("Common"),
        DWARVISH("Dwarvish"),
        ELVISH("Elvish"),
        GIANT("Giant", script = DWARVISH.script),
        GNOMISH("Gnomish", script = DWARVISH.script),
        GOBLIN("Goblin", script = DWARVISH.script),
        HALFLING("Halfling", script = COMMON.script),
        ORC("Orc", script = DWARVISH.script),

        ABYSSAL("Abyssal", isCommon = false, script = "Infernal"),
        CELESTIAL("Celestial", isCommon = false),
        DRACONIC("Draconic", isCommon = false),
        DEEP_SPEECH("Deep Speech", isCommon = false, script = ""),
        INFERNAL("Infernal", isCommon = false),
        PRIMORDIAL("Primordial", isCommon = false, script = DWARVISH.script),
        SYLVAN("Sylvan", isCommon = false, script = ELVISH.script),
        UNDERCOMMON("Undercommon", isCommon = false, script = ELVISH.script)
    }
}