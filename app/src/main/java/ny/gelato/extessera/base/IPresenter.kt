package ny.gelato.extessera.base

/**
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the BaseView type that wants to be attached with.
 */

interface IPresenter<in V : BaseView> {

    fun attachView(baseView: V)

    fun detachView()
}
