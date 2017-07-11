package ny.gelato.extessera.feature.player

import dagger.Component
import ny.gelato.extessera.AppComponent
import ny.gelato.extessera.injection.ForView

/**
 * Created by jord.goldberg on 7/11/17.
 */

@ForView
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(PlayerModule::class))
interface PlayerComponent {

    fun inject(view: PlayerActivity)
}