package ny.gelato.extessera.feature.player.view_model

import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.base.BaseAvatar
import ny.gelato.extessera.data.model.character.Character
import java.text.SimpleDateFormat

/**
 * Created by jord.goldberg on 7/11/17.
 *
 * @layout item_player_character.xml
 */

data class CharacterModel(
        val id: String,
        override val name: String,
        val description: String,
        override val isInspired: Boolean,
        override val imagePath: String,
        override val imageUrl: String,
        val created: String,
        val updated: String

) : BaseAvatar, BaseViewModel() {

    var firstName = name

    constructor(char: Character) :
            this(char.id,
                    char.name,
                    char.description(),
                    char.hasInspiration,
                    char.imagePath,
                    char.imageUrl,
                    SimpleDateFormat("'Created: 'MM.dd.yy").format(char.created),
                    SimpleDateFormat("'Updated: 'MM.dd.yy").format(char.updated)) {

        firstName = char.firstName()
    }

    override fun isSameAs(model: BaseViewModel): Boolean =
            if (model is CharacterModel) this.name == model.name
            else false
}