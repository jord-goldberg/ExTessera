package ny.gelato.extessera.base


/**
 * Created by jord.goldberg on 4/30/17.
 */

open class BasePresenter<T : BaseView> : IPresenter<T> {

    private var _view: T? = null

    val view: T
        get() = _view ?: throw ViewNotAttachedException()

    override fun attachView(baseView: T) {
        _view = baseView
    }

    override fun detachView() {
        _view = null
    }

    class ViewNotAttachedException :
            RuntimeException("Please call Presenter.attachView(BaseView) before requesting data")
}
