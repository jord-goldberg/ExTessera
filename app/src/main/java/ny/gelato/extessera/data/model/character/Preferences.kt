package ny.gelato.extessera.data.model.character

import io.realm.RealmObject

/**
 * Created by jord.goldberg on 5/24/17.
 */

open class Preferences(
        var editAllSkills: Boolean = true,
        var sortSkillsByAbility: Boolean = false,
        var dcAbility: String = Ability.Type.DEX.name,
        var showNotes: Boolean = true,
        var showSpells: Boolean = true

) : RealmObject() {

    enum class Toggle {
        EDIT_SKILLS,
        SORT_SKILLS,
        SHOW_NOTES,
        SHOW_SPELLS
    }
}