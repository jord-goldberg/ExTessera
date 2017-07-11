package ny.gelato.extessera.feature.player

import dagger.Module
import dagger.Provides
import ny.gelato.extessera.data.source.PlayerManager
import ny.gelato.extessera.injection.ForView

/**
 * Created by jord.goldberg on 7/11/17.
 */

@Module
class PlayerModule {

    @Provides
    @ForView
    fun providePresenter(manager: PlayerManager):
            PlayerPresenter = PlayerPresenter(manager)
}