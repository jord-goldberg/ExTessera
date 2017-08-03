package ny.gelato.extessera.data.model.character

import io.realm.RealmObject
import io.realm.annotations.Index

/**
 * Created by jord.goldberg on 5/10/17.
 */

open class Skill(
        @Index private var typeName: String = Skill.Type.ACROBATICS.name,
        private var proficiencyName: String = Skill.Proficiency.NONE.name

) : RealmObject() {

    val type: Type
        get() = Type.valueOf(typeName)

    var proficiency: Proficiency
        get() = Proficiency.valueOf(proficiencyName)
        set(value) {
            proficiencyName = value.name
        }

    enum class Type(val formatted: String) {
        ACROBATICS("Acrobatics") {
            override fun ability(): Ability.Type = Ability.Type.DEX
            override fun nameOrder(): Int = ordinal * 2
            override fun abilityOrder(): Int = 7
        },

        ANIMAL_HANDLING("Animal Handling") {
            override fun ability(): Ability.Type = Ability.Type.WIS
            override fun nameOrder(): Int = ordinal * 2
            override fun abilityOrder(): Int = 4
        },

        ARCANA("Arcana") {
            override fun ability(): Ability.Type = Ability.Type.INT
            override fun nameOrder(): Int = ordinal * 2
            override fun abilityOrder(): Int = 15
        },

        ATHLETICS("Athletics") {
            override fun ability(): Ability.Type = Ability.Type.STR
            override fun nameOrder(): Int = ordinal * 2
            override fun abilityOrder(): Int = 3
        },

        DECEPTION("Deception") {
            override fun ability(): Ability.Type = Ability.Type.CHA
            override fun nameOrder(): Int = ordinal * 2
            override fun abilityOrder(): Int = 16
        },

        HISTORY("History") {
            override fun ability(): Ability.Type = Ability.Type.INT
            override fun nameOrder(): Int = ordinal * 2
            override fun abilityOrder(): Int = 17
        },

        INSIGHT("Insight") {
            override fun ability(): Ability.Type = Ability.Type.WIS
            override fun nameOrder(): Int = ordinal * 2
            override fun abilityOrder(): Int = 6
        },

        INTIMIDATION("Intimidation") {
            override fun ability(): Ability.Type = Ability.Type.CHA
            override fun nameOrder(): Int = ordinal * 2
            override fun abilityOrder(): Int = 18
        },

        INVESTIGATION("Investigation") {
            override fun ability(): Ability.Type = Ability.Type.INT
            override fun nameOrder(): Int = ordinal * 2
            override fun abilityOrder(): Int = 19
        },

        MEDICINE("Medicine") {
            override fun ability(): Ability.Type = Ability.Type.WIS
            override fun nameOrder(): Int = (ordinal * 2) - values().size
            override fun abilityOrder(): Int = 8
        },

        NATURE("Nature") {
            override fun ability(): Ability.Type = Ability.Type.INT
            override fun nameOrder(): Int = (ordinal * 2) - values().size
            override fun abilityOrder(): Int = 21
        },

        PERCEPTION("Perception") {
            override fun ability(): Ability.Type = Ability.Type.WIS
            override fun nameOrder(): Int = (ordinal * 2) - values().size
            override fun abilityOrder(): Int = 10
        },

        PERFORMANCE("Performance") {
            override fun ability(): Ability.Type = Ability.Type.CHA
            override fun nameOrder(): Int = (ordinal * 2) - values().size
            override fun abilityOrder(): Int = 20
        },

        PERSUASION("Persuasion") {
            override fun ability(): Ability.Type = Ability.Type.CHA
            override fun nameOrder(): Int = (ordinal * 2) - values().size
            override fun abilityOrder(): Int = 22
        },

        RELIGION("Religion") {
            override fun ability(): Ability.Type = Ability.Type.INT
            override fun nameOrder(): Int = (ordinal * 2) - values().size
            override fun abilityOrder(): Int = 23
        },

        SLEIGHT_OF_HAND("Sleight of Hand") {
            override fun ability(): Ability.Type = Ability.Type.DEX
            override fun nameOrder(): Int = (ordinal * 2) - values().size
            override fun abilityOrder(): Int = 9
        },

        STEALTH("Stealth") {
            override fun ability(): Ability.Type = Ability.Type.DEX
            override fun nameOrder(): Int = (ordinal * 2) - values().size
            override fun abilityOrder(): Int = 11
        },

        SURVIVAL("Survival") {
            override fun ability(): Ability.Type = Ability.Type.WIS
            override fun nameOrder(): Int = (ordinal * 2) - values().size
            override fun abilityOrder(): Int = 12
        };

        abstract fun ability(): Ability.Type
        abstract fun nameOrder(): Int
        abstract fun abilityOrder(): Int
    }

    enum class Proficiency {
        NONE,
        FULL,
        EXPERT
    }
}