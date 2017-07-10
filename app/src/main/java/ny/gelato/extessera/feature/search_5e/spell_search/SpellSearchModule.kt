package ny.gelato.extessera.feature.search_5e.spell_search

import dagger.Module
import dagger.Provides
import ny.gelato.extessera.injection.ForView

/**
 * Created by jord.goldberg on 5/16/17.
 */

@Module
class SpellSearchModule(private val job: String, private val level: Int) {

    @Provides
    @ForView
    fun providePresenter():
            SpellSearchPresenter = if (job.isEmpty()) SpellSearchPresenter() else SpellSearchPresenter(job, level)
}
