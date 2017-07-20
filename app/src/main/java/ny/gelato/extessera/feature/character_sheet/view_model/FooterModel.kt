package ny.gelato.extessera.feature.character_sheet.view_model

import ny.gelato.extessera.base.BaseViewModel

/**
 * Created by jord.goldberg on 5/17/17.
 *
 * FooterModel always follows a HeaderModel; everything between should be visually congruent.
 * To ensure that the RecyclerView DiffUtil's animation reflects this visual congruity,
 * we must hold a reference to the parent Header's title to differentiate between Footers
 *
 * Layout: item_character_footer.xml
 */

data class FooterModel(val headerTitle: String) : BaseViewModel() {

    override fun isSameAs(model: BaseViewModel): Boolean =
            if (model is FooterModel) headerTitle == model.headerTitle
            else false
}