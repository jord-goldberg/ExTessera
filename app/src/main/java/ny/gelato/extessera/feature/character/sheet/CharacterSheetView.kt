package ny.gelato.extessera.feature.character.sheet

import ny.gelato.extessera.base.BaseView
import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.data.model.character.Character
import ny.gelato.extessera.feature.character.view_model.*


/**
 * Created by jord.goldberg on 4/30/17.
 *
 * Implemented by CharacterSheetFragment. CharacterSheetPresenter observes a character and transforms
 * that observable into a list of Character viewModels that is shown in a RecyclerView. The fragment/view
 * handles clicks if it's necessary to display a popup menu below the clicked view. If not, the click is
 * passed to the presenter which routes the appropriate action (e.g. telling the view to show a relevant bottom
 * sheet for user input, passing the model to the CharacterManager to change the underlying data model, etc.)
 * The interaction between viewHolder and viewModel, including clicks, happens in the .xml file related to the viewModel
 */

interface CharacterSheetView : BaseView {

    fun showCharacter(feed: MutableList<BaseViewModel>)

    fun showEditCharacter(character: Character)

    fun isAtScrollTop(): Boolean

    fun showScrollToTop()

    fun showImageSelect(avatar: AvatarModel)

    fun showHasInspiration(avatar: AvatarModel)

    fun showAbout(about: AboutModel)

    fun showAddExp(additional: ExpModel)

    fun showGoTo(goTo: GoToModel)

    fun showScrollToDestination(destination: BaseViewModel)

    fun showCreateNote()

    fun showEditNote(note: NoteModel)

    fun showIsStabilized()

    fun showEditHp(hp: HpModel)

    fun showEditMaxHp(maxHp: MaxHpModel)

    fun showConfirmLongRest(status: StatusModel)

    fun showSelectSkillProficiency(skill: SkillModel)

    fun showCoin(coin: CoinModel)

    fun showCreateEquipment()

    fun showEquipmentItem(equipment: EquipmentModel)

    fun showEquipmentDeleted(equipment: EquipmentModel)

    fun showEquipmentInventoryFor(character: Character)

    fun showWeaponDetail(weapon: WeaponModel)

    fun showWeaponDeleted(weapon: WeaponModel)

    fun showWeaponsFor(character: Character)

    fun showCreateWeapon()

    fun showSpellDetail(spell: SpellModel)

    fun showSpellsFor(character: Character)

    fun showSpellDeleted(spell: SpellModel)
}
