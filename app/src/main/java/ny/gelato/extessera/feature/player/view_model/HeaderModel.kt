package ny.gelato.extessera.feature.player.view_model

import ny.gelato.extessera.base.BaseViewModel

/**
 * Created by jord.goldberg on 7/11/17.
 *
 * @layout item_player_header.xml
 */

data class HeaderModel(
        val title: String,
        val menuRes: Int = 0

) : BaseViewModel(Action.CONTEXT_MENU)