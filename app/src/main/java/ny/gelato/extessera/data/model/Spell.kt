package ny.gelato.extessera.data.model

import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey
import ny.gelato.extessera.data.model.character.KnownSpell

/**
 * Created by jord.goldberg on 4/25/17.
 */

open class Spell(
        @PrimaryKey var name: String = "",
        @Index var level: Int = 0,
        @Index var classes: String = "",
        var castingTime: String = "",
        var components: String = "",
        var description: String = "",
        var duration: String = "",
        var range: String = "",
        @Index var isRitual: Boolean = false,
        @Index var school: String = "",
        var type: String = "",
        var higherLevels: String = ""

) : RealmObject() {

    enum class School(val formatted: String) {
        ABJURATION("Abjuration"),
        CONJURATION("Conjuration"),
        DIVINATION("Divination"),
        ENCHANTMENT("Enchantment"),
        EVOCATION("Evocation"),
        ILLUSION("Illusion"),
        NECROMANCY("Necromancy"),
        TRANSMUTATION("Transmutation")
    }

    fun hasHigherLevels(): Boolean = !higherLevels.isEmpty()

    fun requirements(): String = "${castingTime.substringBefore(",")} (${components.substringBefore("(").trim()})"

    fun toSpellbook(): KnownSpell = KnownSpell(this)
}
