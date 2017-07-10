package ny.gelato.extessera.feature.player

import io.realm.RealmResults
import ny.gelato.extessera.base.BaseView
import ny.gelato.extessera.data.model.character.Character

/**
 * Created by jord.goldberg on 6/27/17.
 */

interface PlayerView : BaseView {

    fun showPlayerCharacters(characters: RealmResults<Character>)

    fun showPlayerCampaigns()
}