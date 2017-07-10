package ny.gelato.extessera.feature.character_sheet

import dagger.Component
import ny.gelato.extessera.AppComponent
import ny.gelato.extessera.injection.ForView


/**
 * Created by jord.goldberg on 5/1/17.
 */

@ForView
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(CharacterModule::class))
interface CharacterComponent {

    fun inject(view: CharacterFragment)
}
