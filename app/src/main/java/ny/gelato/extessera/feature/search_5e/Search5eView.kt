package ny.gelato.extessera.feature.search_5e

import ny.gelato.extessera.base.BaseView
import rx.Observable

/**
 * Created by jord.goldberg on 5/15/17.
 */

interface Search5eView : BaseView {

    fun queryText(): Observable<String>

    fun showFiltering(filter: String)

    fun showFilterOptions()
}
