package ny.gelato.extessera.feature.spell_detail

import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.data.model.Spell

/**
 * Created by jord.goldberg on 5/22/17.
 */

data class SpellDetailModel(
        val name: String,
        val level: Int,
        val classes: String,
        val castingTime: String,
        val components: String,
        val description: String,
        val duration: String,
        val range: String,
        val isRitual: Boolean,
        val school: String,
        val type: String,
        val higherLevels: String,
        val isInSpellBook: Boolean

) : BaseViewModel() {

    constructor(spell: Spell, isInSpellBook: Boolean) :
            this(spell.name,
                    spell.level,
                    spell.classes,
                    spell.castingTime,
                    spell.components,
                    spell.description,
                    spell.duration,
                    spell.range,
                    spell.isRitual,
                    spell.school,
                    spell.type,
                    spell.higherLevels,
                    isInSpellBook)

    fun hasHigherLevels(): Boolean = !higherLevels.isEmpty()

    fun requirements(): String = "${castingTime.substringBefore(",")} (${components.substringBefore("(").trim()})"
}
