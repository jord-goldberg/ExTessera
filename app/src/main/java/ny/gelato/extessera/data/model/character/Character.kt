package ny.gelato.extessera.data.model.character

import io.realm.Case
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey
import ny.gelato.extessera.data.model.Armor
import ny.gelato.extessera.data.model.Weapon
import java.util.*

/**
 * Created by jord.goldberg on 5/8/17.
 */

open class Character(
        @PrimaryKey var id: String = UUID.randomUUID().toString(),
        @Index var created: Date = Date(),
        @Index var updated: Date = Date(),
        @Index var player: String = "",
        @Index var name: String = "",

        var imagePath: String = "",
        var imageUrl: String = "",
        var hasInspiration: Boolean = false,

        var exp: Int = 0,
        var primary: Job = Job(),
        var multiclasses: RealmList<Job> = RealmList(),

        // private because they're backing fields
        private var raceName: String = Trait.Race.HUMAN.name,
        private var subraceName: String? = null,
        private var alignmentName: String = Trait.Alignment.TRUE_NEUTRAL.name,

        var background: String = "",
        var about: String = "",

        var strength: Ability = Ability(),
        var dexterity: Ability = Ability(),
        var constitution: Ability = Ability(),
        var intelligence: Ability = Ability(),
        var wisdom: Ability = Ability(),
        var charisma: Ability = Ability(),

        var armor: Int = 10,
        var initiativeModifier: Int = 0,
        var speedModifier: Int = 0,
        var hp: Int = 1,
        var maxHp: Int = 1,
        var tempHp: Int = 0,
        var successes: Int = 0,
        var failures: Int = 0,

        var copper: Int = 0,
        var silver: Int = 0,
        var electrum: Int = 0,
        var gold: Int = 0,
        var platinum: Int = 0,

        var notes: RealmList<Note> = RealmList(Note(text = "Welcome to Ex Tessera!\n\n" +
                "Set your skills and abilities by clicking on them below. " +
                "Check the box to the left and swipe to dismiss once you're done.")),

        var traits: RealmList<Trait> = RealmList(),
        var proficiencies: RealmList<Proficiency> = RealmList(),
        var skills: RealmList<Skill> = RealmList(*Skill.Type.values().map { Skill(it.name) }.toTypedArray()),
        var equipment: RealmList<Equipment> = RealmList(),
        var weapons: RealmList<HeldWeapon> = RealmList(),
        var spells: RealmList<KnownSpell> = RealmList(),

        var spellSlots: SpellSlots = SpellSlots(),

        var preferences: Preferences = Preferences()

) : RealmObject() {

    var race: Trait.Race
        get() = Trait.Race.valueOf(raceName)
        set(value) {
            raceName = value.name
        }

    var subrace: Trait.Subrace?
        get() = subraceName?.let { Trait.Subrace.valueOf(it) }
        set(value) {
            subraceName = value?.name
        }

    var alignment: Trait.Alignment
        get() = Trait.Alignment.valueOf(alignmentName)
        set(value) {
            alignmentName = value.name
        }

    fun proficiencyBonus(): Int {
        if (level() < 5) return 2
        else if (level() < 9) return 3
        else if (level() < 13) return 4
        else if (level() < 17) return 5
        else return 6
    }

    fun isJackOfAllTrades(): Boolean = (primary.job == Job.Type.BARD && primary.level > 1)

    fun armorClass(): Int = armor + dexterity.modifier()

    fun initiative(): Int = initiativeModifier + dexterity.modifier() +
            if (isJackOfAllTrades()) proficiencyBonus() / 2
            else 0

    fun speed(): Int = speedModifier + (subrace?.speed ?: race.speed)

    fun passivePerception(): Int = 10 + wisdom.modifier() +
            when (skills.where()
                    .equalTo("typeName", "perception", Case.INSENSITIVE)
                    .findFirst()
                    .proficiency) {
                Skill.Proficiency.FULL -> proficiencyBonus()
                Skill.Proficiency.EXPERT -> proficiencyBonus() * 2
                else -> if (isJackOfAllTrades()) proficiencyBonus() / 2
                else 0
            }

    fun expToNextLevel(): Int = when (exp) {
        in 0..299 -> 300
        in 0..899 -> 900
        in 0..2_699 -> 2_700
        in 0..6_499 -> 6_500
        in 0..13_999 -> 14_000
        in 0..22_999 -> 23_000
        in 0..33_999 -> 34_000
        in 0..47_999 -> 48_000
        in 0..63_999 -> 64_000
        in 0..84_999 -> 85_000
        in 0..99_999 -> 100_000
        in 0..119_999 -> 120_000
        in 0..139_999 -> 140_000
        in 0..164_999 -> 165_000
        in 0..194_999 -> 195_000
        in 0..224_999 -> 225_000
        in 0..264_999 -> 265_000
        in 0..304_999 -> 305_000
        in 0..354_999 -> 355_000
        else -> exp
    } - exp

    fun setExpToLevel() {
        exp = when (level()) {
            1 -> 0
            2 -> 300
            3 -> 900
            4 -> 2_700
            5 -> 6_500
            6 -> 14_000
            7 -> 23_000
            8 -> 34_000
            9 -> 48_000
            10 -> 64_000
            11 -> 85_000
            12 -> 100_000
            13 -> 120_000
            14 -> 140_000
            15 -> 165_000
            16 -> 195_000
            17 -> 225_000
            18 -> 265_000
            19 -> 305_000
            20 -> 355_000
            else -> exp
        }
    }

    fun hasToLevelUp(): Boolean = expLevel() > level()

    fun setTraitsAndProficiencies() {
        setRacialTraits()
        setSaveProficiencies()
        setPrimaryJobProficiencies()
    }

    fun description(): String =
            "${subrace?.let { it.formatted } ?: race.formatted} " +
                    "${primary.job.formatted}, Level ${primary.level}"


    fun level(): Int = primary.level + multiclasses.sumBy { it.level }

    fun expLevel(): Int = when (exp) {
        in 0..299 -> 1
        in 0..899 -> 2
        in 0..2_699 -> 3
        in 0..6_499 -> 4
        in 0..13_999 -> 5
        in 0..22_999 -> 6
        in 0..33_999 -> 7
        in 0..47_999 -> 8
        in 0..63_999 -> 9
        in 0..84_999 -> 10
        in 0..99_999 -> 11
        in 0..119_999 -> 12
        in 0..139_999 -> 13
        in 0..164_999 -> 14
        in 0..194_999 -> 15
        in 0..224_999 -> 16
        in 0..264_999 -> 17
        in 0..304_999 -> 18
        in 0..354_999 -> 19
        else -> 20
    }

    fun levelUpPrimary() {
        primary.level += 1
        primary.dice += 1
        addLevelNotes()
    }

    fun proficienciesFormatted(type: Proficiency.Type): String = when (type) {
        Proficiency.Type.WEAPON ->
            if (proficiencies.map { it.name }
                    .containsAll(Weapon.Type.values().map { it.formatted }))
                listOf("All Weapons") // this block returns a list
            else if (proficiencies.map { it.name }
                    .containsAll(Weapon.Type.values().filter { it.isSimple }.map { it.formatted }))
                proficiencies.filter {
                    it.type == Proficiency.Type.WEAPON
                            && !(Weapon.Type.values().filter { it.isSimple }.map { it.formatted }.contains(it.name))
                }
                        .map { if (it.name.contains(",")) it.name.replace(", ", " (").plus(")") else it.name }
                        .toMutableList()
                        .apply { add(0, "Simple Weapons") }
            else proficiencies.where().equalTo("typeName", Proficiency.Type.WEAPON.name)
                    .findAll()
                    .map { if (it.name.contains(",")) it.name.replace(", ", " (").plus(")") else it.name }
        else -> proficiencies.where()
                .equalTo("typeName", type.name)
                .findAll()
                .map { it.name }

    }.toString().substring(1).dropLast(1)

    private fun addLevelNotes() {
        preferences.showNotes = true
        notes.addAll(primary.levelNotes())
    }

    private fun setSaveProficiencies() {
        strength.save = false
        dexterity.save = false
        constitution.save = false
        intelligence.save = false
        wisdom.save = false
        charisma.save = false

        when (primary.job) {
            Job.Type.BARBARIAN -> {
                strength.save = true
                constitution.save = true
            }
            Job.Type.BARD -> {
                dexterity.save = true
                charisma.save = true
            }
            Job.Type.CLERIC -> {
                wisdom.save = true
                charisma.save = true
            }
            Job.Type.DRUID -> {
                intelligence.save = true
                wisdom.save = true
            }
            Job.Type.FIGHTER -> {
                strength.save = true
                constitution.save = true
            }
            Job.Type.MONK -> {
                strength.save = true
                dexterity.save = true
            }
            Job.Type.PALADIN -> {
                wisdom.save = true
                charisma.save = true
            }
            Job.Type.RANGER -> {
                strength.save = true
                dexterity.save = true
            }
            Job.Type.ROGUE -> {
                dexterity.save = true
                intelligence.save = true
            }
            Job.Type.SORCERER -> {
                constitution.save = true
                charisma.save = true
            }
            Job.Type.WARLOCK -> {
                wisdom.save = true
                charisma.save = true
            }
            Job.Type.WIZARD -> {
                intelligence.save = true
                wisdom.save = true
            }
        }
    }

    private fun setPrimaryJobProficiencies() = when (primary.job) {
        Job.Type.BARBARIAN -> {
            proficiencies.add(Proficiency(Proficiency.Type.ARMOR.name, Armor.Category.LIGHT.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.ARMOR.name, Armor.Category.MEDIUM.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.ARMOR.name, Armor.Category.SHIELD.formatted))
            proficiencies.addAll(Weapon.Type.values().map { Proficiency(Proficiency.Type.WEAPON.name, it.formatted) })
        }
        Job.Type.BARD -> {
            proficiencies.add(Proficiency(Proficiency.Type.ARMOR.name, Armor.Category.LIGHT.formatted))
            proficiencies.addAll(Weapon.Type.values().filter { it.isSimple }
                    .map { Proficiency(Proficiency.Type.WEAPON.name, it.formatted) })
            proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.CROSSBOW_HAND.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.LONGSWORD.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.RAPIER.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.SHORTSWORD.formatted))
        }
        Job.Type.CLERIC -> {
            proficiencies.add(Proficiency(Proficiency.Type.ARMOR.name, Armor.Category.LIGHT.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.ARMOR.name, Armor.Category.MEDIUM.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.ARMOR.name, Armor.Category.SHIELD.formatted))
            proficiencies.addAll(Weapon.Type.values().filter { it.isSimple }
                    .map { Proficiency(Proficiency.Type.WEAPON.name, it.formatted) })
        }
        Job.Type.DRUID -> {
            proficiencies.add(Proficiency(Proficiency.Type.ARMOR.name, Armor.Category.LIGHT.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.ARMOR.name, Armor.Category.MEDIUM.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.ARMOR.name, Armor.Category.SHIELD.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.CLUB.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.DAGGER.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.DART.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.JAVELIN.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.MACE.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.QUARTERSTAFF.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.SCIMITAR.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.SICKLE.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.SLING.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.SPEAR.formatted))
        }
        Job.Type.FIGHTER -> {
            proficiencies.add(Proficiency(Proficiency.Type.ARMOR.name, Armor.Category.LIGHT.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.ARMOR.name, Armor.Category.MEDIUM.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.ARMOR.name, Armor.Category.HEAVY.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.ARMOR.name, Armor.Category.SHIELD.formatted))
            proficiencies.addAll(Weapon.Type.values().map { Proficiency(Proficiency.Type.WEAPON.name, it.formatted) })
        }
        Job.Type.MONK -> {
            proficiencies.addAll(Weapon.Type.values().filter { it.isSimple }
                    .map { Proficiency(Proficiency.Type.WEAPON.name, it.formatted) })
            proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.SHORTSWORD.formatted))
        }
        Job.Type.PALADIN -> {
            proficiencies.add(Proficiency(Proficiency.Type.ARMOR.name, Armor.Category.LIGHT.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.ARMOR.name, Armor.Category.MEDIUM.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.ARMOR.name, Armor.Category.HEAVY.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.ARMOR.name, Armor.Category.SHIELD.formatted))
            proficiencies.addAll(Weapon.Type.values().map { Proficiency(Proficiency.Type.WEAPON.name, it.formatted) })
        }
        Job.Type.RANGER -> {
            proficiencies.add(Proficiency(Proficiency.Type.ARMOR.name, Armor.Category.LIGHT.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.ARMOR.name, Armor.Category.MEDIUM.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.ARMOR.name, Armor.Category.SHIELD.formatted))
            proficiencies.addAll(Weapon.Type.values().map { Proficiency(Proficiency.Type.WEAPON.name, it.formatted) })
        }
        Job.Type.ROGUE -> {
            proficiencies.add(Proficiency(Proficiency.Type.ARMOR.name, Armor.Category.LIGHT.formatted))
            proficiencies.addAll(Weapon.Type.values().filter { it.isSimple }
                    .map { Proficiency(Proficiency.Type.WEAPON.name, it.formatted) })
            proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.CROSSBOW_HAND.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.LONGSWORD.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.RAPIER.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.SHORTSWORD.formatted))
        }
        Job.Type.SORCERER -> {
            proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.DAGGER.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.DART.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.SLING.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.QUARTERSTAFF.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.CROSSBOW_LIGHT.formatted))
        }
        Job.Type.WARLOCK -> {
            proficiencies.add(Proficiency(Proficiency.Type.ARMOR.name, Armor.Category.LIGHT.formatted))
            proficiencies.addAll(Weapon.Type.values().filter { it.isSimple }
                    .map { Proficiency(Proficiency.Type.WEAPON.name, it.formatted) })
        }
        Job.Type.WIZARD -> {
            proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.DAGGER.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.DART.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.SLING.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.QUARTERSTAFF.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.CROSSBOW_LIGHT.formatted))
        }
    }

    private fun setRacialTraits() = when (race) {
        Trait.Race.DWARF -> {
            traits.add(Trait("Darkvision (60 ft)"))
            traits.add(Trait("Dwarven Resilience"))
            traits.add(Trait("Stonecunning"))
            proficiencies.add(Proficiency(Proficiency.Type.LANGUAGE.name, Proficiency.Language.COMMON.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.LANGUAGE.name, Proficiency.Language.DWARVISH.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.BATTLEAXE.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.HANDAXE.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.LIGHT_HAMMER.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.WARHAMMER.formatted))
            subrace.let {
                if (it == Trait.Subrace.HILL_DWARF) traits.add(Trait("Dwarven Toughness"))
                else {
                    proficiencies.add(Proficiency(Proficiency.Type.ARMOR.name, Armor.Category.LIGHT.formatted))
                    proficiencies.add(Proficiency(Proficiency.Type.ARMOR.name, Armor.Category.MEDIUM.formatted))
                }
            }
        }
        Trait.Race.ELF -> {
            traits.add(Trait("Darkvision (60 ft)"))
            traits.add(Trait("Fey Ancestry"))
            traits.add(Trait("Trance"))
            proficiencies.add(Proficiency(Proficiency.Type.LANGUAGE.name, Proficiency.Language.COMMON.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.LANGUAGE.name, Proficiency.Language.ELVISH.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.SHORTSWORD.formatted))
            subrace.let {
                if (it == Trait.Subrace.HIGH_ELF) {
                    proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.LONGSWORD.formatted))
                    proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.LONGBOW.formatted))
                    proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.SHORTBOW.formatted))
                } else if (it == Trait.Subrace.WOOD_ELF) {
                    traits.add(Trait("Mask of the Wild"))
                    proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.LONGSWORD.formatted))
                    proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.LONGBOW.formatted))
                    proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.SHORTBOW.formatted))
                } else {
                    traits[0] = Trait("Darkvision (120 ft)")
                    traits.add(Trait("Sunlight Sensitivity"))
                    traits.add(Trait("Drow Magic (3rd & 5th level)"))
                    proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.RAPIER.formatted))
                    proficiencies.add(Proficiency(Proficiency.Type.WEAPON.name, Weapon.Type.CROSSBOW_HAND.formatted))
                }
            }
        }
        Trait.Race.HALFLING -> {
            traits.add(Trait("Lucky"))
            traits.add(Trait("Brave"))
            traits.add(Trait("Halfling Nimbleness"))
            proficiencies.add(Proficiency(Proficiency.Type.LANGUAGE.name, Proficiency.Language.COMMON.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.LANGUAGE.name, Proficiency.Language.HALFLING.formatted))
            subrace.let {
                if (it == Trait.Subrace.LIGHTFOOT) traits.add(Trait("Naturally Stealthy"))
                else traits.add(Trait("Stout Resilience"))
            }
        }
        Trait.Race.HUMAN -> {
            proficiencies.add(Proficiency(Proficiency.Type.LANGUAGE.name, Proficiency.Language.COMMON.formatted))
        }
        Trait.Race.DRAGONBORN -> {
            traits.add(Trait("Draconic Ancestry"))
            traits.add(Trait("Breath Weapon"))
            traits.add(Trait("Damage Resistance"))
            proficiencies.add(Proficiency(Proficiency.Type.LANGUAGE.name, Proficiency.Language.COMMON.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.LANGUAGE.name, Proficiency.Language.DRACONIC.formatted))
        }
        Trait.Race.GNOME -> {
            traits.add(Trait("Darkvision (60 ft)"))
            traits.add(Trait("Gnome Cunning"))
            proficiencies.add(Proficiency(Proficiency.Type.LANGUAGE.name, Proficiency.Language.COMMON.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.LANGUAGE.name, Proficiency.Language.GNOMISH.formatted))
            subrace.let {
                if (it == Trait.Subrace.FOREST_GNOME) {
                    traits.add(Trait("Natural Illusionist"))
                    traits.add(Trait("Speak with Small Beasts"))
                } else {
                    traits.add(Trait("Artificer's Lore"))
                    traits.add(Trait("Tinker"))
                    proficiencies.add(Proficiency(Proficiency.Type.TOOL.name, Proficiency.Tool.TINKER.formatted))
                }
            }
        }
        Trait.Race.HALF_ELF -> {
            traits.add(Trait("Darkvision (60 ft)"))
            traits.add(Trait("Fey Ancestry"))
            proficiencies.add(Proficiency(Proficiency.Type.LANGUAGE.name, Proficiency.Language.COMMON.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.LANGUAGE.name, Proficiency.Language.ELVISH.formatted))
        }
        Trait.Race.HALF_ORC -> {
            traits.add(Trait("Darkvision (60 ft)"))
            traits.add(Trait("Relentless Endurance"))
            traits.add(Trait("Savage Attacks"))
            proficiencies.add(Proficiency(Proficiency.Type.LANGUAGE.name, Proficiency.Language.COMMON.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.LANGUAGE.name, Proficiency.Language.ORC.formatted))
        }
        Trait.Race.TIEFLING -> {
            traits.add(Trait("Darkvision (60 ft)"))
            traits.add(Trait("Hellish Resistance"))
            traits.add(Trait("Infernal Legacy (3rd & 5th level)"))
            proficiencies.add(Proficiency(Proficiency.Type.LANGUAGE.name, Proficiency.Language.COMMON.formatted))
            proficiencies.add(Proficiency(Proficiency.Type.LANGUAGE.name, Proficiency.Language.INFERNAL.formatted))
        }
    }
}