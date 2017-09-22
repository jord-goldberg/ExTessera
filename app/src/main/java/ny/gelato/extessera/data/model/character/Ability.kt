package ny.gelato.extessera.data.model.character

import io.realm.RealmObject
import java.text.DecimalFormat

/**
 * Created by jord.goldberg on 5/23/17.
 */

open class Ability(
        var score: Int = 10,
        var save: Boolean = false,
        var scoreModifier: Int = 0,
        var saveModifier: Int = 0

) : RealmObject() {

    companion object {
        fun modify(score: Int): Int =
                if (score < 10) (score - 11) / 2
                else (score - 10) / 2

        fun format(score: Int): String =
                if (score == 0) "0"
                else DecimalFormat("+#;-#").format(score)

        fun modifyAndFormat(score: Int): String = format(modify(score))

        fun formatDamage(modifier: Int): String =
                if (modifier == 0) ""
                else DecimalFormat("+ #;- #").format(modifier)
    }

    enum class Type(val formatted: String) {
        STR("Strength"),
        DEX("Dexterity"),
        CON("Constitution"),
        INT("Intelligence"),
        WIS("Wisdom"),
        CHA("Charisma")
    }

    fun modifier(): Int = Companion.modify(score + scoreModifier)
}