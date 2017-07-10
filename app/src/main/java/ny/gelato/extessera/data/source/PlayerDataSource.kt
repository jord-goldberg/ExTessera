package ny.gelato.extessera.data.source

import io.realm.RealmResults
import ny.gelato.extessera.data.model.character.Character
import rx.Observable

/**
 * Created by jord.goldberg on 6/27/17.
 */

interface PlayerDataSource {

    fun getPlayerCharacters(): Observable<RealmResults<Character>>
}