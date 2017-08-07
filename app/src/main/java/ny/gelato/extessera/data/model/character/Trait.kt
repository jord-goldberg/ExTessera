package ny.gelato.extessera.data.model.character

import io.realm.RealmObject
import io.realm.annotations.Index

/**
 * Created by jord.goldberg on 5/8/17.
 */

open class Trait(
        @Index var name: String = ""

) : RealmObject() {

    enum class Race(val formatted: String) {
        DWARF("Dwarf") {
            override fun speed(): Int = 25
            override fun hasSubrace(): Boolean = true
        },
        ELF("Elf") {
            override fun hasSubrace(): Boolean = true
        },
        HALFLING("Halfling") {
            override fun speed(): Int = 25
            override fun hasSubrace(): Boolean = true
        },
        HUMAN("Human"),
        DRAGONBORN("Dragonborn"),
        GNOME("Gnome") {
            override fun speed(): Int = 25
            override fun hasSubrace(): Boolean = true
        },
        HALF_ELF("Half-Elf"),
        HALF_ORC("Half-Orc"),
        TIEFLING("Tiefling");

        open fun speed(): Int = 30
        open fun hasSubrace(): Boolean = false
    }

    enum class Subrace(val formatted: String) {
        HILL_DWARF("Hill Dwarf") {
            override fun race(): Race = Race.DWARF
        },
        MOUNTAIN_DWARF("Mountain Dwarf") {
            override fun race(): Race = Race.DWARF
        },
        HIGH_ELF("High Elf") {
            override fun race(): Race = Race.ELF
        },
        WOOD_ELF("Wood Elf") {
            override fun race(): Race = Race.ELF
            override fun speed(): Int = 35
        },
        DARK_ELF("Drow") {
            override fun race(): Race = Race.ELF
        },
        LIGHTFOOT("Lightfoot Halfling") {
            override fun race(): Race = Race.HALFLING
        },
        STOUT("Stout Halfling") {
            override fun race(): Race = Race.HALFLING
        },
        FOREST_GNOME("Forest Gnome") {
            override fun race(): Race = Race.GNOME
        },
        ROCK_GNOME("Rock Gnome") {
            override fun race(): Race = Race.GNOME
        };

        abstract fun race(): Race
        open fun speed(): Int = race().speed()
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