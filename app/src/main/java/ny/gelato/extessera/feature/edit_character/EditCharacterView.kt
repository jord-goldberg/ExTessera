package ny.gelato.extessera.feature.edit_character

import ny.gelato.extessera.base.BaseView

/**
 * Created by jord.goldberg on 6/23/17.
 */

interface EditCharacterView : BaseView {

    fun validateInput(): Boolean

    fun showNext()
}