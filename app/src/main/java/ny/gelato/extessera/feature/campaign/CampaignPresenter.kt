package ny.gelato.extessera.feature.campaign

import ny.gelato.extessera.base.BasePresenter
import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.data.model.character.Character
import ny.gelato.extessera.feature.character.CharacterManager
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by jord.goldberg on 6/19/17.
 */

class CampaignPresenter @Inject constructor(val characterManager: CharacterManager) : BasePresenter<CampaignView>() {

    val subscriptions = CompositeSubscription()

    val feed = Observable.Transformer<Character, MutableList<BaseViewModel>> {
        it.filter { it.isValid }.map { character ->
            mutableListOf<BaseViewModel>().apply {

            }
        }.observeOn(AndroidSchedulers.mainThread())
    }

    lateinit var character: Character

    fun start() {
        val subscription = characterManager
                .getCharacter()
                .doOnNext { character = it }
                .compose(feed)
                .subscribe(
                        { feed -> view.showCampaign(feed) },
                        { t -> t.printStackTrace() })

        subscriptions.add(subscription)
    }

    fun stop() = subscriptions.clear()
}