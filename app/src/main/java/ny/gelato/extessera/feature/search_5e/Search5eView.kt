package ny.gelato.extessera.feature.search_5e

import ny.gelato.extessera.base.BaseView
import rx.Observable

/**
 * Created by jord.goldberg on 5/15/17.
 *
 * Search5eActivity and the specific fragments it displays implement this interface.
 *
 * The activity toolbar's SearchView publishes the user's search query using queryText(), which
 * is observed by the fragments. The activity also interprets the intent passed to it and displays
 * the proper fragment. When a user clicks the toolbar options, the activity tells the current
 * fragment to display the appropriate filter options using showFilterOptions()
 *
 * The specific search fragments and their views show the results of the query after being filtered.
 * Each fragment has a presenter that handles fetching the data and acts as a filter that
 * combines the latest query and filter input; it uses showFiltering(...) to pass that information
 * to the activity to display in a SnackBar to the user.
 */

interface Search5eView : BaseView {

    fun queryText(): Observable<String>

    fun showFiltering(filter: String)

    fun showFilterOptions()
}
