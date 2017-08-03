package ny.gelato.extessera.feature.edit_character.edit_basics

import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.data.model.character.Character
import ny.gelato.extessera.data.model.character.Job
import ny.gelato.extessera.data.model.character.Trait

/**
 * Created by jord.goldberg on 7/5/17.
 *
 * @layout fragment_edit_character_basics.xml
 */

data class EditBasicsModel(
        var name: String = "",
        var level: Int = 1,
        var job: Job.Type = Job.Type.FIGHTER,
        var race: Trait.Race = Trait.Race.HUMAN,
        var subrace: Trait.Subrace? = null

) : BaseViewModel() {

    constructor(character: Character) :
            this(character.name,
                    character.primary.level,
                    character.primary.job,
                    character.race,
                    character.subrace)

    private val jobs = Job.Type.values()
    private val races = Trait.Race.values()
    private val subraces = Trait.Subrace.values()

    fun jobOptions(): Array<String> = jobs.map { it.formatted }.toTypedArray()

    fun raceOptions(): Array<String> = races.map { it.formatted }.toTypedArray()

    fun subraceOptions(): Array<String> = subraces.filter { it.race() == race }.map { it.formatted }.toTypedArray()

    fun showLevelHint(): String = if (level < 10) "  $level  " else " $level "

    fun setName(name: CharSequence) {
        this.name = name.toString()
        notifyChange()
    }

    fun setLevel(level: CharSequence) {
        if (level.isEmpty())
            this.level = 1
        else {
            this.level = level.toString().toInt()
            if (this.level > 20) this.level = 20
            else if (this.level < 1) this.level = 1
        }
        notifyChange()
    }

    fun selectJob(position: Int) {
        job = jobs[position]
        notifyChange()
    }

    fun selectedJobPosition(): Int = job.ordinal

    fun selectRace(position: Int) {
        race = races[position]
        if (!race.hasSubrace()) subrace = null
        notifyChange()
    }

    fun selectedRacePosition(): Int = race.ordinal

    fun selectSubrace(position: Int) {
        subrace = subraces.filter { it.race() == race }[position]
    }

    fun selectedSubracePosition(): Int = subraces.filter { it.race() == race }.indexOf(subrace)
}