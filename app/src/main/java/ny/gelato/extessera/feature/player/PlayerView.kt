package ny.gelato.extessera.feature.player

import ny.gelato.extessera.base.BaseView
import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.feature.player.view_model.CharacterModel
import ny.gelato.extessera.feature.player.view_model.NewCharacterModel

/**
 * Created by jord.goldberg on 6/27/17.
 *
 * PlayerActivity implements this interface. It's a RecyclerView that displays a list of all
 * Characters currently in the Realm database. This assumes one player per device.
 */

interface PlayerView : BaseView {

    fun showPlayer(feed: MutableList<BaseViewModel>)

    fun showCreateCharacter(newCharacter: NewCharacterModel? = null)
    
    fun showCharacter(character: CharacterModel)
}