package ny.gelato.extessera.feature.search_5e.spell_search

import dagger.Component
import ny.gelato.extessera.AppComponent
import ny.gelato.extessera.injection.ForView

/**
 * Created by jord.goldberg on 5/16/17.
 */

@ForView
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(SpellSearchModule::class))
interface SpellSearchComponent {

    fun inject(view: SpellSearchFragment)
}