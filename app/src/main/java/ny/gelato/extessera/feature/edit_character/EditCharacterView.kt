package ny.gelato.extessera.feature.edit_character

import ny.gelato.extessera.base.BaseView

/**
 * Created by jord.goldberg on 6/23/17.
 *
 * EditCharacterActivity and the fragments it displays implement this interface. The activity has a
 * bottom bar that provides next/back navigation for the fragments. The activity uses the interface
 * to check input and show the next fragment on bottom bar clicks.
 */

interface EditCharacterView : BaseView {

    fun validateInput(): Boolean = true

    fun showNext()
}