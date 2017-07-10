package ny.gelato.extessera.feature.player

import ny.gelato.extessera.base.BasePresenter
import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.data.model.character.Character
import ny.gelato.extessera.data.source.PlayerManager
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by jord.goldberg on 6/27/17.
 */

class PlayerPresenter @Inject constructor(val playerManager: PlayerManager) : BasePresenter<PlayerView>() {

    val subscriptions = CompositeSubscription()

    fun start() {
        val subscription = playerManager
                .getPlayerCharacters()
                .subscribe(
                        { feed -> view.showPlayerCharacters(feed) },
                        { t -> t.printStackTrace() })

        subscriptions.add(subscription)
    }

    fun stop() = subscriptions.clear()
}