package ny.gelato.extessera.feature.player

import android.view.View
import io.realm.RealmResults
import ny.gelato.extessera.R
import ny.gelato.extessera.base.BasePresenter
import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.data.model.character.Character
import ny.gelato.extessera.data.source.PlayerManager
import ny.gelato.extessera.feature.player.view_model.CharacterModel
import ny.gelato.extessera.feature.player.view_model.HeaderModel
import ny.gelato.extessera.feature.player.view_model.NewCharacterModel
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by jord.goldberg on 6/27/17.
 */

class PlayerPresenter @Inject constructor(val playerManager: PlayerManager) : BasePresenter<PlayerView>() {

    val subscriptions = CompositeSubscription()

    val feed = Observable.Transformer<RealmResults<Character>, MutableList<BaseViewModel>> {
        it.filter { it.isLoaded }.map { characters ->
            mutableListOf<BaseViewModel>().apply {
                add(NewCharacterModel())
                add(HeaderModel("Local Characters", R.menu.menu_player_local_characters))
                for (character in characters)
                    add(CharacterModel(character))
            }
        }.observeOn(AndroidSchedulers.mainThread())
    }

    fun start() {
        val subscription = playerManager
                .getPlayerCharacters()
                .compose(feed)
                .subscribe(
                        { feed -> view.showPlayer(feed) },
                        { t -> t.printStackTrace() })

        subscriptions.add(subscription)
    }

    fun stop() = subscriptions.clear()

    fun click(v: View, model: BaseViewModel) {
        when (model) {
            is CharacterModel -> view.showCharacter(model)
            is NewCharacterModel -> view.showCreateCharacter(model)
        }
    }

    fun delete(model: BaseViewModel) {
        when (model) {
            is CharacterModel -> playerManager.deleteCharacter(model.id)
        }
    }
}