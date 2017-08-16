package ny.gelato.extessera.data.model.character

/**
 * Created by jord.goldberg on 8/10/17.
 */

enum class Race(val formatted: String, val speed: Int = 30, val subraces: Array<Subrace> = emptyArray()) {

    DWARF("Dwarf", speed = 25, subraces = arrayOf(Subrace.HILL_DWARF, Subrace.MOUNTAIN_DWARF)) {
        override fun abilityScoreIncrease(ability: Ability.Type): Int = when (ability) {
            Ability.Type.CON -> 2
            else -> 0
        }
    },

    ELF("Elf", subraces = arrayOf(Subrace.HIGH_ELF, Subrace.WOOD_ELF, Subrace.DARK_ELF)) {
        override fun abilityScoreIncrease(ability: Ability.Type): Int = when (ability) {
            Ability.Type.DEX -> 2
            else -> 0
        }
    },

    HALFLING("Halfling", speed = 25, subraces = arrayOf(Subrace.LIGHTFOOT, Subrace.STOUT)) {
        override fun abilityScoreIncrease(ability: Ability.Type): Int = when (ability) {
            Ability.Type.DEX -> 2
            else -> 0
        }
    },

    HUMAN("Human") {
        override fun abilityScoreIncrease(ability: Ability.Type): Int = 1
    },

    DRAGONBORN("Dragonborn") {
        override fun abilityScoreIncrease(ability: Ability.Type): Int = when (ability) {
            Ability.Type.STR -> 2
            Ability.Type.CHA -> 1
            else -> 0
        }
    },

    GNOME("Gnome", speed = 25, subraces = arrayOf(Subrace.FOREST_GNOME, Subrace.ROCK_GNOME)) {
        override fun abilityScoreIncrease(ability: Ability.Type): Int = when (ability) {
            Ability.Type.INT -> 2
            else -> 0
        }
    },

    HALF_ELF("Half-Elf") {
        override fun abilityScoreIncrease(ability: Ability.Type): Int = when (ability) {
            Ability.Type.CHA -> 2
            else -> 0
            // TODO() give half-elf +1 to 2 more ability scores depending on class
        }
    },

    HALF_ORC("Half-Orc") {
        override fun abilityScoreIncrease(ability: Ability.Type): Int = when (ability) {
            Ability.Type.STR -> 2
            Ability.Type.CON -> 1
            else -> 0
        }
    },

    TIEFLING("Tiefling") {
        override fun abilityScoreIncrease(ability: Ability.Type): Int = when (ability) {
            Ability.Type.INT -> 1
            Ability.Type.CHA -> 2
            else -> 0
        }
    };

    abstract fun abilityScoreIncrease(ability: Ability.Type): Int

    enum class Subrace(val formatted: String, val speed: Int = 25) {

        HILL_DWARF("Hill Dwarf") {
            override fun abilityScoreIncrease(ability: Ability.Type): Int = when (ability) {
                Ability.Type.WIS -> 1
                else -> 0
            }
        },

        MOUNTAIN_DWARF("Mountain Dwarf") {
            override fun abilityScoreIncrease(ability: Ability.Type): Int = when (ability) {
                Ability.Type.STR -> 2
                else -> 0
            }
        },

        HIGH_ELF("High Elf", speed = 30) {
            override fun abilityScoreIncrease(ability: Ability.Type): Int = when (ability) {
                Ability.Type.INT -> 1
                else -> 0
            }
        },

        WOOD_ELF("Wood Elf", speed = 35) {
            override fun abilityScoreIncrease(ability: Ability.Type): Int = when (ability) {
                Ability.Type.WIS -> 1
                else -> 0
            }
        },

        DARK_ELF("Drow", speed = 30) {
            override fun abilityScoreIncrease(ability: Ability.Type): Int = when (ability) {
                Ability.Type.CHA -> 1
                else -> 0
            }
        },

        LIGHTFOOT("Lightfoot Halfling") {
            override fun abilityScoreIncrease(ability: Ability.Type): Int = when (ability) {
                Ability.Type.CHA -> 1
                else -> 0
            }
        },

        STOUT("Stout Halfling") {
            override fun abilityScoreIncrease(ability: Ability.Type): Int = when (ability) {
                Ability.Type.CON -> 1
                else -> 0
            }
        },

        FOREST_GNOME("Forest Gnome") {
            override fun abilityScoreIncrease(ability: Ability.Type): Int = when (ability) {
                Ability.Type.DEX -> 1
                else -> 0
            }
        },

        ROCK_GNOME("Rock Gnome") {
            override fun abilityScoreIncrease(ability: Ability.Type): Int = when (ability) {
                Ability.Type.CON -> 1
                else -> 0
            }
        };

        abstract fun abilityScoreIncrease(ability: Ability.Type): Int
    }
}