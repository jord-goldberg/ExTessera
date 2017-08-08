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

    enum class Type(val formatted: String, val ability: Ability.Type) {

        ACROBATICS("Acrobatics", Ability.Type.DEX) {
            override fun nameOrder(): Int = ordinal * 2
            override fun abilityOrder(): Int = 7
        },

        ANIMAL_HANDLING("Animal Handling", Ability.Type.WIS) {
            override fun nameOrder(): Int = ordinal * 2
            override fun abilityOrder(): Int = 4
        },

        ARCANA("Arcana", Ability.Type.INT) {
            override fun nameOrder(): Int = ordinal * 2
            override fun abilityOrder(): Int = 15
        },

        ATHLETICS("Athletics", Ability.Type.STR) {
            override fun nameOrder(): Int = ordinal * 2
            override fun abilityOrder(): Int = 3
        },

        DECEPTION("Deception", Ability.Type.CHA) {
            override fun nameOrder(): Int = ordinal * 2
            override fun abilityOrder(): Int = 16
        },

        HISTORY("History", Ability.Type.INT) {
            override fun nameOrder(): Int = ordinal * 2
            override fun abilityOrder(): Int = 17
        },

        INSIGHT("Insight", Ability.Type.WIS) {
            override fun nameOrder(): Int = ordinal * 2
            override fun abilityOrder(): Int = 6
        },

        INTIMIDATION("Intimidation", Ability.Type.CHA) {
            override fun nameOrder(): Int = ordinal * 2
            override fun abilityOrder(): Int = 18
        },

        INVESTIGATION("Investigation", Ability.Type.INT) {
            override fun nameOrder(): Int = ordinal * 2
            override fun abilityOrder(): Int = 19
        },

        MEDICINE("Medicine", Ability.Type.WIS) {
            override fun nameOrder(): Int = (ordinal * 2) - values().size
            override fun abilityOrder(): Int = 8
        },

        NATURE("Nature", Ability.Type.INT) {
            override fun nameOrder(): Int = (ordinal * 2) - values().size
            override fun abilityOrder(): Int = 21
        },

        PERCEPTION("Perception", Ability.Type.WIS) {
            override fun nameOrder(): Int = (ordinal * 2) - values().size
            override fun abilityOrder(): Int = 10
        },

        PERFORMANCE("Performance", Ability.Type.CHA) {
            override fun nameOrder(): Int = (ordinal * 2) - values().size
            override fun abilityOrder(): Int = 20
        },

        PERSUASION("Persuasion", Ability.Type.CHA) {
            override fun nameOrder(): Int = (ordinal * 2) - values().size
            override fun abilityOrder(): Int = 22
        },

        RELIGION("Religion", Ability.Type.INT) {
            override fun nameOrder(): Int = (ordinal * 2) - values().size
            override fun abilityOrder(): Int = 23
        },

        SLEIGHT_OF_HAND("Sleight of Hand", Ability.Type.DEX) {
            override fun nameOrder(): Int = (ordinal * 2) - values().size
            override fun abilityOrder(): Int = 9
        },

        STEALTH("Stealth", Ability.Type.DEX) {
            override fun nameOrder(): Int = (ordinal * 2) - values().size
            override fun abilityOrder(): Int = 11
        },

        SURVIVAL("Survival", Ability.Type.WIS) {
            override fun nameOrder(): Int = (ordinal * 2) - values().size
            override fun abilityOrder(): Int = 12
        };

        abstract fun nameOrder(): Int
        abstract fun abilityOrder(): Int
    }

    enum class Proficiency {
        NONE, FULL, EXPERT
    }
}