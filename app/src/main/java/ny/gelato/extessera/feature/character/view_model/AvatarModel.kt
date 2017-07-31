package ny.gelato.extessera.feature.character.view_model

import android.support.design.widget.BottomSheetDialog
import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.base.BaseAvatar
import ny.gelato.extessera.data.model.character.Character

/**
 * Created by jord.goldberg on 6/8/17.
 *
 * @layout bottom_sheet_character_avatar.xml
 * @layout item_character_avatar.xml
 */

data class AvatarModel(
        override val name: String = "",
        val description: String = "",
        override val isInspired: Boolean = false,
        override val imagePath: String = "",
        override val imageUrl: String = ""

) : BaseAvatar, BaseViewModel() {

    private var newImageUrl: String = ""

    constructor(char: Character) :
            this(char.name,
                    char.description(),
                    char.hasInspiration,
                    char.imagePath,
                    char.imageUrl)

    fun menu(): AvatarModel = copy().apply { action = Action.CONTEXT_MENU }

    fun toggleInspiration(): AvatarModel = copy(isInspired = !isInspired)

    fun setImageUrl(url: CharSequence) {
        if (url.isEmpty()) newImageUrl = ""
        else newImageUrl = url.toString()
        notifyChange()
    }

    fun validateNewUrl(): Boolean = newImageUrl.isNotBlank()

    fun clearImage(sheet: BottomSheetDialog): AvatarModel {
        sheet.dismiss()
        return this.copy(imagePath = "", imageUrl = "").apply { action = Action.UPDATE }

    }

    fun updateImage(sheet: BottomSheetDialog): AvatarModel {
        sheet.dismiss()
        return this.copy(imageUrl = newImageUrl).apply { action = Action.UPDATE }
    }

    fun about(): AboutModel = AboutModel()

    fun experience(): ExpModel = ExpModel()

    fun goTo(): GoToModel = GoToModel()
}