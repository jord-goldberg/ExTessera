package ny.gelato.extessera.data.model.character

import io.realm.RealmObject
import io.realm.annotations.Index

/**
 * Created by jord.goldberg on 5/8/17.
 */

open class Job(
        @Index private var jobName: String = Job.Type.BARBARIAN.name,
        @Index private var archetypeName: String? = null,
        var level: Int = 1,
        var dice: Int = 1,
        var counter1: Int = 0

) : RealmObject() {

    var job: Type
        get() = Type.valueOf(jobName)
        set(value) {
            jobName = value.name
        }

    var archetype: Archetype?
        get() = archetypeName?.let { Archetype.valueOf(it) }
        set(value) {
            archetypeName = value?.name
        }

    enum class Type(val formatted: String, val archetypes: Array<Archetype>) {
        BARBARIAN("Barbarian", archetypes = arrayOf(
                Archetype.BERSERKER,
                Archetype.TOTEM_WARRIOR)),
        BARD("Bard", archetypes = arrayOf(
                Archetype.COLLEGE_OF_LORE,
                Archetype.COLLEGE_OF_VALOR)),
        CLERIC("Cleric", archetypes = arrayOf(
                Archetype.KNOWLEDGE_DOMAIN,
                Archetype.LIFE_DOMAIN,
                Archetype.LIGHT_DOMAIN,
                Archetype.NATURE_DOMAIN,
                Archetype.TEMPEST_DOMAIN,
                Archetype.TRICKERY_DOMAIN,
                Archetype.WAR_DOMAIN)),
        DRUID("Druid", archetypes = arrayOf(
                Archetype.CIRCLE_OF_THE_LAND,
                Archetype.CIRCLE_OF_THE_MOON)),
        FIGHTER("Fighter", archetypes = arrayOf(
                Archetype.CHAMPION,
                Archetype.BATTLE_MASTER,
                Archetype.ELDRITCH_KNIGHT)),
        MONK("Monk", archetypes = arrayOf(
                Archetype.WAY_OF_THE_OPEN_HAND,
                Archetype.WAY_OF_SHADOW,
                Archetype.WAY_OF_THE_FOUR_ELEMENTS)),
        PALADIN("Paladin", archetypes = arrayOf(
                Archetype.OATH_OF_DEVOTION,
                Archetype.OATH_OF_THE_ANCIENTS,
                Archetype.OATH_OF_VENGEANCE)),
        RANGER("Ranger", archetypes = arrayOf(
                Archetype.HUNTER,
                Archetype.BEAST_MASTER)),
        ROGUE("Rogue", archetypes = arrayOf(
                Archetype.THIEF,
                Archetype.ASSASSIN,
                Archetype.ARCANE_TRICKSTER)),
        SORCERER("Sorcerer", archetypes = arrayOf(
                Archetype.DRACONIC_BLOODLINE,
                Archetype.WILD_MAGIC)),
        WARLOCK("Warlock", archetypes = arrayOf(
                Archetype.ARCHFEY,
                Archetype.FIEND,
                Archetype.GREAT_OLD_ONE)),
        WIZARD("Wizard", archetypes = arrayOf(
                Archetype.SCHOOL_OF_ABJURATION,
                Archetype.SCHOOL_OF_CONJURATION,
                Archetype.SCHOOL_OF_DIVINATION,
                Archetype.SCHOOL_OF_ENCHANTMENT,
                Archetype.SCHOOL_OF_EVOCATION,
                Archetype.SCHOOL_OF_ILLUSION,
                Archetype.SCHOOL_OF_NECROMANCY,
                Archetype.SCHOOL_OF_TRANSMUTATION))
    }

    enum class Archetype(val formatted: String) {
        BERSERKER("Berserker"),
        TOTEM_WARRIOR("Totem Warrior"),
        COLLEGE_OF_LORE("College of Lore"),
        COLLEGE_OF_VALOR("College of Valor"),
        KNOWLEDGE_DOMAIN("Knowledge Domain"),
        LIFE_DOMAIN("Life Domain"),
        LIGHT_DOMAIN("Light Domain"),
        NATURE_DOMAIN("Nature Domain"),
        TEMPEST_DOMAIN("Tempest Domain"),
        TRICKERY_DOMAIN("Trickery Domain"),
        WAR_DOMAIN("War Domain"),
        CIRCLE_OF_THE_LAND("Circle of the Land"),
        CIRCLE_OF_THE_MOON("Circle of the Moon"),
        CHAMPION("Champion"),
        BATTLE_MASTER("Battle Master"),
        ELDRITCH_KNIGHT("Eldritch Knight"),
        WAY_OF_THE_OPEN_HAND("Way of the Open Hand"),
        WAY_OF_SHADOW("Way of Shadow"),
        WAY_OF_THE_FOUR_ELEMENTS("Way of the Four Elements"),
        OATH_OF_DEVOTION("Oath of Devotion"),
        OATH_OF_THE_ANCIENTS("Oath of the Ancients"),
        OATH_OF_VENGEANCE("Oath of Vengeance"),
        HUNTER("Hunter"),
        BEAST_MASTER("Beast Master"),
        THIEF("Thief"),
        ASSASSIN("Assassin"),
        ARCANE_TRICKSTER("Arcane Trickster"),
        DRACONIC_BLOODLINE("Draconic Bloodline"),
        WILD_MAGIC("Wild Magic"),
        ARCHFEY("The Archfey"),
        FIEND("The Fiend"),
        GREAT_OLD_ONE("The Great Old One"),
        SCHOOL_OF_ABJURATION("School of Abjuration"),
        SCHOOL_OF_CONJURATION("School of Conjuration"),
        SCHOOL_OF_DIVINATION("School of Divination"),
        SCHOOL_OF_ENCHANTMENT("School of Enchantment"),
        SCHOOL_OF_EVOCATION("School of Evocation"),
        SCHOOL_OF_ILLUSION("School of Illusion"),
        SCHOOL_OF_NECROMANCY("School of Necromancy"),
        SCHOOL_OF_TRANSMUTATION("School of Transmutation"),
    }

    fun features(): List<String> = mutableListOf<String>().apply{
        (1..level)
                .flatMap { featuresForLevel(it) }
                .forEach { feature ->
                    if (feature.contains(" (")) {
                        val oldFeature = find { it.contains(feature.substringBefore(" (")) }
                        oldFeature?.let {
                            add(indexOf(it), feature)
                            remove(it)
                        } ?: add(feature)
                    } else add(feature)
                }
    }

    fun playersHandbookPage(): Int = when (job) {
        Job.Type.BARBARIAN -> 46
        Job.Type.BARD -> 51
        Job.Type.CLERIC -> 56
        Job.Type.DRUID -> 64
        Job.Type.FIGHTER -> 70
        Job.Type.MONK -> 76
        Job.Type.PALADIN -> 82
        Job.Type.RANGER -> 89
        Job.Type.ROGUE -> 94
        Job.Type.SORCERER -> 99
        Job.Type.WARLOCK -> 105
        Job.Type.WIZARD -> 112
    }

    fun hitDieMax(): Int = when (job) {
        Type.BARBARIAN -> 12
        Type.FIGHTER, Type.PALADIN, Type.RANGER -> 10
        Type.BARD, Type.CLERIC, Type.DRUID, Type.MONK, Type.ROGUE, Type.WARLOCK -> 8
        Type.SORCERER, Type.WIZARD -> 6
    }

    fun hitDieMaxFormatted(): String = "d${hitDieMax()}"

    fun hitDiceFormatted(): String = "$dice${hitDieMaxFormatted()}"

    fun castingAbility(): Ability.Type = when (job) {
        Type.BARD, Type.PALADIN, Type.SORCERER, Type.WARLOCK -> Ability.Type.CHA
        Type.CLERIC, Type.DRUID, Type.MONK, Type.RANGER -> Ability.Type.WIS
        Type.FIGHTER, Type.ROGUE, Type.WIZARD -> Ability.Type.INT
        else -> Ability.Type.STR
    }

    fun casterLevel(): Int = when (job) {
        Type.BARD, Type.CLERIC, Type.DRUID, Type.SORCERER, Type.WIZARD -> level
        Type.PALADIN, Type.RANGER -> level / 2
        Type.ROGUE, Type.FIGHTER -> level / 3
        Type.WARLOCK -> level
        else -> 0
    }

    fun spellLevel(): Int = when (casterLevel()) {
        1, 2 -> 1
        3, 4 -> 2
        5, 6 -> 3
        7, 8 -> 4
        9, 10 -> 5
        11, 12 -> 6
        13, 14 -> 7
        15, 16 -> 8
        in 17..20 -> 9
        else -> 0
    }

    fun hasToSelectArchetype(): Boolean = (archetypeName == null) && level >= when (job) {
        Type.BARBARIAN, Type.BARD, Type.FIGHTER, Type.MONK, Type.PALADIN, Type.RANGER, Type.ROGUE -> 3
        Type.CLERIC, Type.SORCERER, Type.WARLOCK -> 1
        Type.DRUID, Type.WIZARD -> 2
    }

    fun levelNotes(): List<Note> = ArrayList<Note>().apply {
        val features = featuresForLevel(level)

        if (features.isNotEmpty()) {
            add(Note(text = "${job.formatted} $level " +
                    "${if (features.size > 1) "features" else "feature"} " +
                    "(PHB ${playersHandbookPage()}):\n" +
                    features.toString().substring(1).dropLast(1)))
        }

        when (level) {
            4, 8, 12, 16, 19 -> add(Note(text = "Ability Score Improvement"))
        }

        when (job) {
            Type.BARBARIAN -> when (level) {
                6, 10, 14 -> add(Note(text = "Choose Path feature"))
            }
            Type.BARD -> when (level) {
                3 -> add(Note(text = "Expertise: choose 2 skill proficiencies"))
                6 -> add(Note(text = "Choose Bard College feature"))
                10 -> {
                    add(Note(text = "Expertise: choose 2 skill proficiencies"))
                    add(Note(text = "Magical Secrets: learn 2 spells"))
                }
                14 -> {
                    add(Note(text = "Magical Secrets: learn 2 spells"))
                    add(Note(text = "Choose Bard College feature"))
                }
                18 -> add(Note(text = "Magical Secrets: learn 2 spells"))
            }
            Type.CLERIC -> when (level) {
                2, 6, 8, 17 -> add(Note(text = "Choose Divine Domain feature"))
            }
            Type.DRUID -> when (level) {
                4, 8 -> add(Note(text = "Wild Shape improvement"))
                6, 10, 14 -> add(Note(text = "Choose Druid Circle feature"))

                18 -> add(Note(text = "Magical Secrets: learn 2 spells"))
            }
            Type.FIGHTER -> when (level) {
                6 -> add(Note(text = "Ability Score Improvement"))
                7, 10, 15, 18 -> add(Note(text = "Choose Martial Archetype feature"))
            }
            Type.MONK -> when (level) {
                6, 11, 17 -> add(Note(text = "Choose Monastic Tradition feature"))
            }
            Type.PALADIN -> when (level) {
                7, 15, 20 -> add(Note(text = "Choose Sacred Oath feature"))
            }
            Type.RANGER -> when (level) {
                6 -> {
                    add(Note(text = "Favored Enemy improvement"))
                    add(Note(text = "Natural Explorer improvement"))
                }
                7, 11, 15 -> add(Note(text = "Choose Ranger Archetype feature"))
                10 -> add(Note(text = "Natural Explorer improvement"))
                14 -> add(Note(text = "Favored Enemy improvement"))
            }
            Type.ROGUE -> when (level) {
                1, 6 -> add(Note(text = "Expertise: choose 2 skill proficiencies"))
                9, 13, 17 -> add(Note(text = "Choose Roguish Archetype feature"))
                10 -> add(Note(text = "Ability Score Improvement"))
            }
            Job.Type.SORCERER -> when (level) {
                10, 17 -> add(Note(text = "Choose Metamagic option"))
                6, 14, 18 -> add(Note(text = "Choose Sorcerous Origin feature"))
            }
            Job.Type.WARLOCK -> when (level) {
                5, 7, 9, 12, 18 -> add(Note(text = "Choose additional Eldtritch Invocation"))
                6, 10, 14 -> add(Note(text = "Choose Otherworldly Patron feature"))
                11, 13, 17 -> add(Note(text = "Choose ${spellLevel()}th Mystic Arcanum"))
                15 -> {
                    add(Note(text = "Choose additional Eldtritch Invocation"))
                    add(Note(text = "Choose ${spellLevel()}th Mystic Arcanum"))
                }
            }
            Job.Type.WIZARD -> when (level) {
                6, 10, 14 -> add(Note(text = "Choose Arcane Tradition feature"))
            }
        }
    }

    private fun featuresForLevel(level: Int): List<String> = ArrayList<String>().apply {
        when (job) {
            Type.BARBARIAN -> when (level) {
                1 -> {
                    add("Rage")
                    add("Unarmored Defense")
                }
                2 -> {
                    add("Reckless Attack")
                    add("Danger Sense")
                }
                3 -> add("Primal Path")
                5 -> {
                    add("Extra Attack")
                    add("Fast Movement")
                }
                7 -> add("Feral Instinct")
                9 -> add("Brutal Critical (+1d)")
                11 -> add("Relentless Rage")
                13 -> add("Brutal Critical (+2d)")
                15 -> add("Persistent Rage")
                17 -> add("Brutal Critical (+3d)")
                18 -> add("Indomitable Might")
                20 -> add("Primal Champion")
            }
            Type.BARD -> when (level) {
                1 -> {
                    add("Spellcasting")
                    add("Bardic Inspiration (d6)")
                }
                2 -> {
                    add("Jack of All Trades")
                    add("Song of Rest (d6)")
                }
                3 -> add("Bard College")
                5 -> {
                    add("Bardic Inspiration (d8)")
                    add("Font of Inspiration")
                }
                6 -> add("Countercharm")
                9 -> add("Song of Rest d8")
                10 -> add("Bardic Inspiration (d10)")
                13 -> add("Song of Rest (d10)")
                15 -> add("Bardic Inspiration (d12)")
                17 -> add("Song of Rest (d12)")
                20 -> add("Superior Inspiration")
            }
            Type.CLERIC -> when (level) {
                1 -> {
                    add("Spellcasting")
                    add("Divine Domain")
                }
                2 -> add("Channel Divinity (1/rest)")
                5 -> add("Destroy Undead (CR 1/2)")
                6 -> add("Channel Divinity (2/rest)")
                8 -> add("Destroy Undead (CR 1)")
                10 -> add("Divine Intervention")
                11 -> add("Destroy Undead (CR 2)")
                14 -> add("Destroy Undead (CR 3)")
                17 -> add("Destroy Undead (CR 4)")
                18 -> add("Channel Divinity (3/rest)")
                20 -> add("Divine Intervention improvement")
            }
            Type.DRUID -> when (level) {
                1 -> {
                    add("Druidic")
                    add("Spellcasting")
                }
                2 -> {
                    add("Wild Shape")
                    add("Druid Circle")
                }
                18 -> {
                    add("Timeless Body")
                    add("Beast Spells")
                }
                20 -> add("Archdruid")
            }
            Type.FIGHTER -> when (level) {
                1 -> {
                    add("Fighting Style")
                    add("Second Wind")
                }
                2 -> add("Action Surge (one use)")
                3 -> add("Martial Archetype")
                5 -> add("Extra Attack (1)")
                9 -> add("Indomitable (one use)")
                11 -> add("Extra Attack (2)")
                13 -> add("Indomitable (two uses)")
                15 -> add("Persistent Rage")
                17 -> {
                    add("Action Surge (two uses)")
                    add("Indomitable (three uses)")
                }
                20 -> add("Extra Attack (3)")
            }
            Type.MONK -> when (level) {
                1 -> {
                    add("Unarmored Defense")
                    add("Martial Arts")
                }
                2 -> {
                    add("Ki")
                    add("Unarmored Movement")
                }
                3 -> {
                    add("Monastic Tradition")
                    add("Deflect Missiles")
                }
                4 -> add("Slow Fall")
                5 -> {
                    add("Extra Attack")
                    add("Stunning Strike")
                }
                6 -> add("Ki Empowered Strikes")
                7 -> {
                    add("Evasion")
                    add("Stillness of Mind")
                }
                9 -> add("Unarmored Movement improvement")
                10 -> add("Purity of Body")
                13 -> add("Tongue of the Sun and Moon")
                14 -> add("Diamond Soul")
                15 -> add("Timeless Body")
                18 -> add("Empty Body")
                20 -> add("Perfect Self")
            }
            Type.PALADIN -> when (level) {
                1 -> {
                    add("Divine Sense")
                    add("Lay on Hands")
                }
                2 -> {
                    add("Fighting Style")
                    add("Spellcasting")
                    add("Divine Smite")
                }
                3 -> {
                    add("Divine Health")
                    add("Sacred Oath")
                }
                5 -> add("Extra Attack")
                6 -> add("Aura of Protection")
                10 -> add("Aura of Courage")
                11 -> add("Improved Divine Smite")
                14 -> add("Cleansing Touch")
                18 -> add("Aura improvements")
            }

            Job.Type.RANGER -> when (level) {
                1 -> {
                    add("Favored Enemy")
                    add("Natural Explorer")
                }
                2 -> {
                    add("Fighting Style")
                    add("Spellcasting")
                }
                3 -> {
                    add("Ranger Archetype")
                    add("Primeval Awareness")
                }
                5 -> add("Extra Attack")
                8 -> add("Land's Stride")
                10 -> add("Hide in Plain Sight")
                14 -> add("Vanish")
                18 -> add("Feral Senses")
                20 -> add("Foe Slayer")
            }
            Job.Type.ROGUE -> when (level) {
                1 -> {
                    add("Sneak Attack")
                    add("Thieves' Cant")
                }
                2 -> add("Cunning Action")
                3 -> add("Roguish Archetype")
                5 -> add("Uncanny Dodge")
                7 -> add("Evasion")
                11 -> add("Reliable Talent")
                14 -> add("Blind Sense")
                15 -> add("Slippery Mind")
                18 -> add("Elusive")
                20 -> add("Stroke of Luck")
            }
            Job.Type.SORCERER -> when (level) {
                1 -> {
                    add("Spellcasting")
                    add("Sorcerous Origin")
                }
                2 -> add("Font of Magic")
                3 -> add("Metamagic")
                20 -> add("Sorcerous Restoration")
            }
            Job.Type.WARLOCK -> when (level) {
                1 -> {
                    add("Otherworldly Patron")
                    add("Pact Magic")
                }
                2 -> add("Eldritch Invocations")
                3 -> add("Pact Boon")
                11 -> add("Mystic Arcanum")
                20 -> add("Eldritch Master")
            }
            Job.Type.WIZARD -> when (level) {
                1 -> {
                    add("Spellcasting")
                    add("Arcane Recovery")
                }
                2 -> add("Arcane Tradition")
                18 -> add("Spell Mastery")
                20 -> add("Signature Spell")
            }
        }
    }
}