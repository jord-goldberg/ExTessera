package ny.gelato.extessera.feature.edit_character.edit_about

import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.data.model.character.Background
import ny.gelato.extessera.data.model.character.Character

/**
 * Created by jord.goldberg on 7/6/17.
 *
 * @layout fragment_edit_character_about.xml
 */

data class EditAboutModel(
        var alignment: Character.Alignment = Character.Alignment.TRUE_NEUTRAL,
        var background: Background = Background.ACOLYTE,
        var about: String = ""

) : BaseViewModel() {

    constructor(character: Character) :
            this(character.alignment,
                    character.background,
                    character.about)

    private val backgrounds: Array<Background> = Background.values()
    private val alignments: Array<Character.Alignment> = Character.Alignment.values()

    fun backgroundOptions(): Array<String> = backgrounds.map { it.formatted }.toTypedArray()

    fun selectBackground(position: Int) {
        background = backgrounds[position]
    }

    fun selectedBackgroundPosition(): Int = background.ordinal

    fun alignmentOptions(): Array<String> = alignments.map { it.formatted }.toTypedArray()

    fun selectAlignment(position: Int) {
        alignment = alignments[position]
        notifyChange()
    }

    fun selectedAlignmentPosition(): Int = alignment.ordinal

    fun setAbout(about: CharSequence) {
        this.about = about.toString()
        notifyChange()
    }
}