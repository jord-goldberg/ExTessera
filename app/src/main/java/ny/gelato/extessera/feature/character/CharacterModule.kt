package ny.gelato.extessera.feature.character

import dagger.Module
import dagger.Provides
import io.realm.Realm
import ny.gelato.extessera.feature.character.sheet.CharacterSheetPresenter
import ny.gelato.extessera.injection.ForView


/**
 * Created by jord.goldberg on 5/1/17.
 */

@Module
class CharacterModule(private val id: String) {

    @Provides
    fun provideId(): String = id

    @Provides
    @ForView
    fun provideManager(realm: Realm): CharacterManager = CharacterManager(realm, id)

    @Provides
    @ForView
    fun provideSheetPresenter(manager: CharacterManager):
            CharacterSheetPresenter = CharacterSheetPresenter(manager)
}
