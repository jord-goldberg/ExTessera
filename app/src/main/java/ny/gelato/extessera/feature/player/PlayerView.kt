package ny.gelato.extessera.feature.player

import io.realm.RealmResults
import ny.gelato.extessera.base.BaseView
import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.data.model.character.Character
import ny.gelato.extessera.feature.player.view_model.CharacterModel
import ny.gelato.extessera.feature.player.view_model.NewCharacterModel

/**
 * Created by jord.goldberg on 6/27/17.
 */

interface PlayerView : BaseView {

    fun showPlayer(feed: MutableList<BaseViewModel>)

    fun showCreateCharacter(newCharacter: NewCharacterModel? = null)
    
    fun showCharacter(character: CharacterModel)
}