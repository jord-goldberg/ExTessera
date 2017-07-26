package ny.gelato.extessera.feature.character

import dagger.Module
import dagger.Provides
import ny.gelato.extessera.data.source.CharacterManager
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
    fun providePresenter(manager: CharacterManager):
            CharacterSheetPresenter = CharacterSheetPresenter(manager)
}
