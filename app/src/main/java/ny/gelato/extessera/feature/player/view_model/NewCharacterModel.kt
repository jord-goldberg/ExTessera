package ny.gelato.extessera.feature.player.view_model

import ny.gelato.extessera.base.BaseViewModel

/**
 * Created by jord.goldberg on 7/11/17.
 *
 * @layout item_player_new_character.xml
 */

data class NewCharacterModel(
        var playerId: String? = null

) : BaseViewModel()