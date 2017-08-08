package ny.gelato.extessera.data.model.character

import io.realm.RealmObject
import io.realm.annotations.Index

/**
 * Created by jord.goldberg on 5/8/17.
 */

open class Trait(
        @Index var name: String = ""

) : RealmObject() {

    enum class Race(val formatted: String, val speed: Int = 30, val subraces: Array<Subrace> = emptyArray()) {
        DWARF("Dwarf", speed = 25, subraces = arrayOf(Subrace.HILL_DWARF, Subrace.MOUNTAIN_DWARF)),
        ELF("Elf", subraces = arrayOf(Subrace.HIGH_ELF, Subrace.WOOD_ELF, Subrace.DARK_ELF)),
        HALFLING("Halfling", speed = 25, subraces = arrayOf(Subrace.LIGHTFOOT, Subrace.STOUT)),
        HUMAN("Human"),
        DRAGONBORN("Dragonborn"),
        GNOME("Gnome", speed = 25, subraces = arrayOf(Subrace.FOREST_GNOME, Subrace.ROCK_GNOME)),
        HALF_ELF("Half-Elf"),
        HALF_ORC("Half-Orc"),
        TIEFLING("Tiefling")
    }

    enum class Subrace(val formatted: String, val speed: Int = 25) {
        HILL_DWARF("Hill Dwarf"),
        MOUNTAIN_DWARF("Mountain Dwarf"),
        HIGH_ELF("High Elf", speed = 30),
        WOOD_ELF("Wood Elf", speed = 35),
        DARK_ELF("Drow", speed = 30),
        LIGHTFOOT("Lightfoot Halfling"),
        STOUT("Stout Halfling"),
        FOREST_GNOME("Forest Gnome"),
        ROCK_GNOME("Rock Gnome")
    }

    enum class Alignment(val formatted: String) {
        LAWFUL_GOOD("Lawful Good"),
        NEUTRAL_GOOD("Neutral Good"),
        CHAOTIC_GOOD("Chaotic Good"),
        LAWFUL_NEUTRAL("Lawful Neutral"),
        TRUE_NEUTRAL("True Neutral"),
        CHAOTIC_NEUTRAL("Chaotic Neutral"),
        LAWFUL_EVIL("Lawful Evil"),
        NEUTRAL_EVIL("Neutral Evil"),
        CHAOTIC_EVIL("Chaotic Evil")
    }
}