package ny.gelato.extessera.data.model.character

import io.realm.RealmObject
import io.realm.annotations.Index
import ny.gelato.extessera.data.model.Spell
import ny.gelato.extessera.feature.character.view_model.SpellModel

/**
 * Created by jord.goldberg on 5/22/17.
 */

open class KnownSpell(
        @Index var name: String = "",
        @Index var level: Int = 0,
        var requirements: String = "",
        var range: String = "",
        var type: String = "",
        @Index var prepared: Boolean = false,
        var castsSinceLongRest: Int = 0

) : RealmObject() {

    constructor(spell: Spell) :
            this(spell.name,
                    spell.level,
                    spell.requirements(),
                    spell.range,
                    spell.type)

    constructor(model: SpellModel) :
            this(model.name,
                    model.level,
                    model.requirements,
                    model.range,
                    model.type,
                    model.prepared,
                    model.castsSinceLongRest)
}