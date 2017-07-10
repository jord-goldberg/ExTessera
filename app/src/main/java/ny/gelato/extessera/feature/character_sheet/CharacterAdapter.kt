package ny.gelato.extessera.feature.character_sheet

import android.support.v7.util.DiffUtil
import android.support.v7.widget.StaggeredGridLayoutManager
import ny.gelato.extessera.R
import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.base.BaseViewModelAdapter
import ny.gelato.extessera.feature.character_sheet.view_model.*
import javax.inject.Inject


/**
 * Created by jord.goldberg on 4/30/17.
 */

class CharacterAdapter @Inject constructor(override val presenter: CharacterPresenter) : BaseViewModelAdapter(presenter) {

    var feed: MutableList<BaseViewModel> = mutableListOf()
        set(value) {
            val diffResult = DiffUtil.calculateDiff(CharacterDiffUtil(field, value))
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onBindViewHolder(holder: ViewModelHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = feed[position]
        if (model !is SkillModel && model !is SkillSubheaderModel)
            (holder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams)
                    .isFullSpan = true
    }

    override fun getViewModelForPosition(position: Int): Any = feed[position]

    override fun getLayoutIdForPosition(position: Int): Int = when (feed[position]) {
        is AvatarModel -> R.layout.item_avatar
        is HeaderModel -> R.layout.item_header
        is FooterModel -> R.layout.item_footer
        is NoteModel -> R.layout.item_note
        is LevelUpModel -> R.layout.item_level_up
        is StatusModel -> R.layout.item_status
        is AbilitiesModel -> R.layout.item_abilities
        is SavingThrowsModel -> R.layout.item_saving_throws
        is SkillModel -> R.layout.item_skill
        is SkillSubheaderModel -> R.layout.item_skill_subheader
        is SpellModel -> R.layout.item_spell
        is WeaponModel -> R.layout.item_weapon
        else -> throw ModelLayoutException(feed[position]::class.java.simpleName)
    }

    override fun getItemCount(): Int = feed.size

    class ModelLayoutException(className: String) : RuntimeException("Please create branch for destination " +
            "$className in CharacterAdapter.getLayoutIdForPosition()")

    class CharacterDiffUtil(private val oldList: List<BaseViewModel>, private val newList: List<BaseViewModel>) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                oldList[oldItemPosition].isSameAs(newList[newItemPosition])

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                oldList[oldItemPosition] == newList[newItemPosition]
    }
}
