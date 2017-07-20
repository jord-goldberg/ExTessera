package ny.gelato.extessera.base

import android.databinding.BaseObservable

/**
 * Created by jord.goldberg on 5/12/17.
 */

abstract class BaseViewModel(var editable: Boolean = false) : BaseObservable() {

    open fun isSameAs(model: BaseViewModel): Boolean = this.javaClass == model.javaClass

    enum class Action {
        VIEW, CREATE, UPDATE, DELETE
    }
}