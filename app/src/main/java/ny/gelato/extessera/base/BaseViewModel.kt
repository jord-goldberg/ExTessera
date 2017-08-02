package ny.gelato.extessera.base

import android.databinding.BaseObservable

/**
 * Created by jord.goldberg on 5/12/17.
 *
 * Concrete ViewModels extend this class; they should be data classes.
 *
 * Generally they appear in lists that are displayed using BaseViewModelAdapters; isSameAs(...)
 * should be overridden if multiple versions need to be differentiated for the RecyclerView.DiffUtils.
 *
 * ViewModels should be bite-sized portions of the underlying data, allowing manageable
 * amounts to be shown and/or manipulated at once. They can sometimes be interacted with in multiple
 * ways (e.g. update underlying info with Action.UPDATE or open a menu with Action.CONTEXT_MENU)
 *
 * Using Android Data Binding their methods are used directly in .xml files; viewModels can update
 * the view by calling notifyChange(). Clicks on them can be passed to any BaseView implementation.
 *
 * @param action can be used to signal the click intention.
 *
 * @layout every BaseViewModel should have (a) corresponding .xml file(s) here
 */

abstract class BaseViewModel(var action: Action = Action.VIEW) : BaseObservable() {

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