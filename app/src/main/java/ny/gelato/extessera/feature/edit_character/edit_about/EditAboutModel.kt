package ny.gelato.extessera.feature.edit_character.edit_about

import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.data.model.character.Character
import ny.gelato.extessera.data.model.character.Job
import ny.gelato.extessera.data.model.character.Trait

/**
 * Created by jord.goldberg on 7/6/17.
 *
 * @layout fragment_edit_character_about.xml
 */

data class EditAboutModel(
        var alignment: Trait.Alignment = Trait.Alignment.TRUE_NEUTRAL,
        var background: String = "",
        var about: String = ""

) : BaseViewModel() {

    constructor(character: Character) :
            this(Trait.Alignment.valueOf(character.alignment),
                    character.background,
                    character.about)

    private val alignments: Array<Trait.Alignment> = Trait.Alignment.values()

    fun alignmentOptions(): Array<String> = alignments.map { it.formatted }.toTypedArray()

    fun selectAlignment(position: Int) {
        alignment = alignments[position]
        notifyChange()
    }

    fun selectedAlignmentPosition(): Int = alignment.ordinal

    fun setBackground(background: CharSequence) {
        this.background = background.toString()
        notifyChange()
    }

    fun setAbout(about: CharSequence) {
        this.about = about.toString()
        notifyChange()
    }
}