package ny.gelato.extessera.base

import android.databinding.BaseObservable

/**
 * Created by jord.goldberg on 5/12/17.
 */

abstract class BaseViewModel(var action: Action = BaseViewModel.Action.VIEW) : BaseObservable() {

    enum class Action {
        VIEW, // View the viewModel normally
        EDIT, // Show input for user to edit the viewModel
        CREATE, // Create a new Realm object based on the viewModel
        UPDATE, // Update an existing Realm object
        DELETE, // Delete an existing Realm Object
        CONTEXT_MENU // Show additional contextual options based on the viewModel
    }

    open fun isSameAs(model: BaseViewModel): Boolean = this.javaClass == model.javaClass
}