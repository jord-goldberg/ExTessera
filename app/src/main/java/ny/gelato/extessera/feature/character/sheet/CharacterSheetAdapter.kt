package ny.gelato.extessera.feature.character.sheet

import android.support.v7.util.DiffUtil
import ny.gelato.extessera.R
import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.base.BaseViewModelAdapter
import ny.gelato.extessera.feature.character.view_model.*


/**
 * Created by jord.goldberg on 4/30/17.
 */

class CharacterSheetAdapter constructor(override val parent: CharacterSheetView) : BaseViewModelAdapter(parent) {

    var feed: MutableList<BaseViewModel> = mutableListOf()
        set(value) {
            val diffResult = DiffUtil.calculateDiff(BaseViewModelDiffUtil(field, value))
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun getViewModelForPosition(position: Int): Any = feed[position]

    override fun getLayoutIdForPosition(position: Int): Int = when (feed[position]) {
        is AvatarModel -> R.layout.item_character_avatar
        is HeaderModel -> R.layout.item_character_header
        is FooterModel -> R.layout.item_character_footer
        is NoteModel -> R.layout.item_character_note
        is LevelUpModel -> R.layout.item_character_level_up
        is DeathSaveModel -> R.layout.item_character_death_saves
        is StatusModel -> R.layout.item_character_status
        is AbilitiesModel -> R.layout.item_character_abilities
        is SavingThrowsModel -> R.layout.item_character_saving_throws
        is SkillModel -> R.layout.item_character_skill
        is SkillSubheaderModel -> R.layout.item_character_skill_subheader
        is SpellModel -> R.layout.item_character_spell
        is WeaponModel -> R.layout.item_character_weapon
        is CoinModel -> R.layout.item_character_equipment_coin
        is EquipmentModel -> R.layout.item_character_equipment_item
        is EquipmentFooterModel -> R.layout.item_character_equipment_footer
        else -> throw ModelLayoutException(feed[position]::class.java.simpleName, this::class.java.simpleName)
    }

    override fun getItemCount(): Int = feed.size
}
