package ny.gelato.extessera.feature.character.view_model

import ny.gelato.extessera.base.BaseViewModel

/**
 * Created by jord.goldberg on 5/12/17.
 *
 * @layout item_character_header.xml
 */

data class HeaderModel(
        val section: Section,
        val avatar: AvatarModel,
        val menuRes: Int,
        val titleInfo: String = "",
        val isEditingSection: Boolean = false

) : BaseViewModel(Action.CONTEXT_MENU) {

    enum class Section(val title: String) {
        NOTES("Notes"),
        SKILLS("Skills"),
        EQUIPMENT("Equipment"),
        WEAPONS("Weapons"),
        SPELLS("Spells")
    }

    override fun isSameAs(model: BaseViewModel): Boolean =
            if (model is HeaderModel) section.title == model.section.title
            else false

    fun title(): String = section.title

    fun updateSection(): HeaderModel = copy().apply { action = Action.UPDATE }
}