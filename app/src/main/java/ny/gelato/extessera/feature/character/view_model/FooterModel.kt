package ny.gelato.extessera.feature.character.view_model

import ny.gelato.extessera.base.BaseViewModel

/**
 * Created by jord.goldberg on 5/17/17.
 *
 * FooterModel always follows a HeaderModel; everything between should be visually congruent.
 * To ensure that the RecyclerView DiffUtil's animation reflects this visual congruity,
 * we must hold a reference to the header to differentiate between footers
 *
 * @param headerTitle to differentiate between Footers
 *
 * @layout item_character_footer.xml
 */

data class FooterModel(val headerTitle: String) : BaseViewModel() {

    override fun isSameAs(model: BaseViewModel): Boolean =
            if (model is FooterModel) headerTitle == model.headerTitle
            else false
}