package ny.gelato.extessera.feature.player

import android.support.v7.util.DiffUtil
import ny.gelato.extessera.R
import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.base.BaseViewModelAdapter
import ny.gelato.extessera.feature.player.view_model.CharacterModel
import ny.gelato.extessera.feature.player.view_model.HeaderModel
import ny.gelato.extessera.feature.player.view_model.NewCharacterModel
import javax.inject.Inject

/**
 * Created by jord.goldberg on 7/11/17.
 */

class PlayerAdapter constructor(override val parent: PlayerView) : BaseViewModelAdapter(parent) {

    var feed: MutableList<BaseViewModel> = mutableListOf()
        set(value) {
            val diffResult = DiffUtil.calculateDiff(BaseViewModelDiffUtil(field, value))
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun getViewModelForPosition(position: Int): Any = feed[position]

    override fun getLayoutIdForPosition(position: Int): Int = when (feed[position]) {
        is HeaderModel -> R.layout.item_player_header
        is CharacterModel -> R.layout.item_player_character
        is NewCharacterModel -> R.layout.item_player_new_character
        else -> throw ModelLayoutException(feed[position]::class.java.simpleName, this::class.java.simpleName)
    }

    override fun getItemCount(): Int = feed.size
}