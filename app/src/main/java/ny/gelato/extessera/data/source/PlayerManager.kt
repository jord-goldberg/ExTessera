package ny.gelato.extessera.data.source

import io.realm.Realm
import io.realm.RealmResults
import ny.gelato.extessera.data.model.character.Character
import rx.Observable
import javax.inject.Inject

/**
 * Created by jord.goldberg on 6/27/17.
 */

class PlayerManager @Inject constructor(val realm: Realm) : PlayerDataSource {

    override fun getPlayerCharacters(): Observable<RealmResults<Character>> = realm.where(Character::class.java)
            .findAll()
            .asObservable()

    override fun deleteCharacter(id: String) {
        realm.executeTransactionAsync { realm ->
            val character = realm.where(Character::class.java).equalTo("id", id).findFirst()
            character.deleteFromRealm()
        }
    }
}