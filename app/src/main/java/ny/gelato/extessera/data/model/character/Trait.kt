package ny.gelato.extessera.data.model.character

import io.realm.RealmObject
import io.realm.annotations.Index

/**
 * Created by jord.goldberg on 5/8/17.
 */

open class Trait(
        @Index var name: String = ""

): RealmObject() {

    enum class Race(val formatted: String) {
        DWARF("Dwarf") {
            override fun hasSubrace(): Boolean  = true
        },
        ELF("Elf") {
            override fun hasSubrace(): Boolean  = true
        },
        HALFLING("Halfling") {
            override fun hasSubrace(): Boolean  = true
        },
        HUMAN("Human") {
            override fun hasSubrace(): Boolean  = false
        },
        DRAGONBORN("Dragonborn") {
            override fun hasSubrace(): Boolean  = false
        },
        GNOME("Gnome") {
            override fun hasSubrace(): Boolean  = true
        },
        HALF_ELF("Half-Elf") {
            override fun hasSubrace(): Boolean  = false
        },
        HALF_ORC("Half-Orc") {
            override fun hasSubrace(): Boolean  = false
        },
        TIEFLING("Tiefling") {
            override fun hasSubrace(): Boolean  = false
        };

        abstract fun hasSubrace(): Boolean
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