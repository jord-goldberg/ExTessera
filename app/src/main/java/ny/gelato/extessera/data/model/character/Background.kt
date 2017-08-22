package ny.gelato.extessera.data.model.character

/**
 * Created by jord.goldberg on 8/21/17.
 */
enum class Background(val formatted: String,
                      val skills: Array<Skill.Type>,
                      val languageCount: Int = 0,
                      val goldPieces: Int) {

    ACOLYTE("Acolyte",
            skills = arrayOf(Skill.Type.INSIGHT, Skill.Type.RELIGION),
            languageCount = 2,
            goldPieces = 15) {
        override fun equipment(): Array<Equipment> = arrayOf(
                Equipment("Holy Symbol"),
                Equipment("Prayer book/wheel"),
                Equipment("Stick of incense", 5),
                Equipment("Vestments"),
                Equipment("Common clothes"))
    },

    CHARLATAN("Charlatan",
            skills = arrayOf(Skill.Type.DECEPTION, Skill.Type.SLEIGHT_OF_HAND),
            goldPieces = 15) {
        override fun tools() = arrayOf(
                Proficiency.Tool.DISGUISE,
                Proficiency.Tool.FORGERY)
        override fun equipment(): Array<Equipment> = arrayOf(
                Equipment("Fine clothes"),
                Equipment("Tools to run a con"))
    },

    CRIMINAL("Criminal",
            skills = arrayOf(Skill.Type.DECEPTION, Skill.Type.STEALTH),
            goldPieces = 15) {
        override fun tools() = arrayOf(
                Proficiency.Tool.random(Proficiency.Tool.Type.GAMING),
                Proficiency.Tool.THIEF)
        override fun equipment(): Array<Equipment> = arrayOf(
                Equipment("Crowbar"),
                Equipment("Dark common clothes w/ hood"))
    },

    ENTERTAINER("Entertainer",
            skills = arrayOf(Skill.Type.ACROBATICS, Skill.Type.PERFORMANCE),
            languageCount = 2,
            goldPieces = 15) {
        override fun tools() = arrayOf(
                Proficiency.Tool.DISGUISE,
                Proficiency.Tool.random(Proficiency.Tool.Type.MUSIC))
        override fun equipment(): Array<Equipment> = arrayOf(
                Equipment("Letter from an admirer"),
                Equipment("Costume"))
    },

    FOLK_HERO("Folk Hero",
            skills = arrayOf(Skill.Type.ANIMAL_HANDLING, Skill.Type.SURVIVAL),
            goldPieces = 10) {
        override fun tools() = arrayOf(
                Proficiency.Tool.random(Proficiency.Tool.Type.ARTISAN),
                Proficiency.Tool.VEHICLE_LAND)
        override fun equipment(): Array<Equipment> = arrayOf(
                Equipment("Shovel"),
                Equipment("Iron pot"),
                Equipment("Common clothes"))
    },

    GUILD_ARTISAN("Guild Artisan",
            skills = arrayOf(Skill.Type.INSIGHT, Skill.Type.PERSUASION),
            languageCount = 1,
            goldPieces = 15) {
        override fun tools() = arrayOf(Proficiency.Tool.random(Proficiency.Tool.Type.ARTISAN))
        override fun equipment(): Array<Equipment> = arrayOf(
                Equipment("Letter of introduction from guild"),
                Equipment("Traveler's clothes"))
    },

    HERMIT("Hermit",
            skills = arrayOf(Skill.Type.MEDICINE, Skill.Type.RELIGION),
            languageCount = 1,
            goldPieces = 5) {
        override fun tools() = arrayOf(Proficiency.Tool.HERBALISM)
        override fun equipment(): Array<Equipment> = arrayOf(
                Equipment("Case of notes from seclusion"),
                Equipment("Winter blanket"),
                Equipment("Common clothes"))
    },

    NOBLE("Noble",
            skills = arrayOf(Skill.Type.HISTORY, Skill.Type.PERSUASION),
            languageCount = 1,
            goldPieces = 25) {
        override fun tools() = arrayOf(Proficiency.Tool.random(Proficiency.Tool.Type.GAMING))
        override fun equipment(): Array<Equipment> = arrayOf(
                Equipment("Fine clothes"),
                Equipment("Signet Ring"),
                Equipment("Scroll of Pedigree"))
    },

    OUTLANDER("Outlander",
            skills = arrayOf(Skill.Type.ATHLETICS, Skill.Type.SURVIVAL),
            languageCount = 1,
            goldPieces = 10) {
        override fun tools() = arrayOf(Proficiency.Tool.random(Proficiency.Tool.Type.MUSIC))
        override fun equipment(): Array<Equipment> = arrayOf(
                Equipment("Staff"),
                Equipment("Hunting trap"),
                Equipment("Animal trophy"),
                Equipment("Traveler's clothes"))
    },

    SAGE("Sage",
            skills = arrayOf(Skill.Type.ARCANA, Skill.Type.HISTORY),
            languageCount = 2,
            goldPieces = 10) {
        override fun equipment(): Array<Equipment> = arrayOf(
                Equipment("Quill & bottle of ink"),
                Equipment("Small knife"),
                Equipment("Letter from dead colleague"),
                Equipment("Common clothes"))
    },

    SAILOR("Sailor",
            skills = arrayOf(Skill.Type.ATHLETICS, Skill.Type.PERCEPTION),
            goldPieces = 10) {
        override fun tools() = arrayOf(
                Proficiency.Tool.NAVIGATOR,
                Proficiency.Tool.VEHICLE_WATER)
        override fun equipment(): Array<Equipment> = arrayOf(
                Equipment("Belaying pin (club)"),
                Equipment("50 ft of silk rope"),
                Equipment("Lucky trinket"),
                Equipment("Common clothes"))
    },

    SOLDIER("Soldier",
            skills = arrayOf(Skill.Type.ATHLETICS, Skill.Type.INTIMIDATION),
            goldPieces = 10) {
        override fun tools() = arrayOf(
                Proficiency.Tool.random(Proficiency.Tool.Type.GAMING),
                Proficiency.Tool.VEHICLE_LAND)
        override fun equipment(): Array<Equipment> = arrayOf(
                Equipment("Insignia of rank"),
                Equipment("Trophy from a fallen enemy"),
                Equipment("Common clothes"))
    },

    URCHIN("Urchin",
            skills = arrayOf(Skill.Type.SLEIGHT_OF_HAND, Skill.Type.STEALTH),
            goldPieces = 10) {
        override fun tools() = arrayOf(
                Proficiency.Tool.DISGUISE,
                Proficiency.Tool.THIEF)
        override fun equipment(): Array<Equipment> = arrayOf(
                Equipment("Small knife"),
                Equipment("Map of home city"),
                Equipment("Pet mouse"),
                Equipment("Token to remember parents"),
                Equipment("Common clothes"))
    };

    open fun tools(): Array<Proficiency.Tool> = emptyArray()
    abstract fun equipment(): Array<Equipment>
}