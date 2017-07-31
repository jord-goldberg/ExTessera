package ny.gelato.extessera.base

import android.view.View

/**
 * Base interface that any class that wants to act as a View in the MVP (Model View Presenter)
 * pattern must implement. Generally this interface will be extended by a more specific interface
 * that then usually will be implemented by an Activity or Fragment. Generally displays BaseViewModels
 * so any implementation can optionally handle clicks on viewModels
 */

interface BaseView {

    fun onClick(v: View, viewModel: BaseViewModel) {}

    fun onLongClick(v: View, viewModel: BaseViewModel): Boolean = false

    fun onClick(v: View, any: Any) {} // This is here because the Search5eView displays RealmObjects directly
}
