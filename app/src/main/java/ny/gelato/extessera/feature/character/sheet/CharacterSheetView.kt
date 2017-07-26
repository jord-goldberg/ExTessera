package ny.gelato.extessera.feature.character.sheet

import ny.gelato.extessera.base.BaseView
import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.data.model.character.Character
import ny.gelato.extessera.feature.character.view_model.*


/**
 * Created by jord.goldberg on 4/30/17.
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

    fun showIsStabilized()

    fun showEditHp(hp: HpModel)

    fun showEditMaxHp(maxHp: MaxHpModel)

    fun showConfirmLongRest(status: StatusModel)

    fun showSelectSkillProficiency(skill: SkillModel)

    fun showWeaponDetail(weapon: WeaponModel)

    fun showWeaponsFor(character: Character)

    fun showCreateWeapon()

    fun showSpellDetail(spell: SpellModel)

    fun showSpellsFor(character: Character)

    fun showCoin(coin: CoinModel)

    fun showCreateEquipment()

    fun showEquipmentItem(equipment: EquipmentModel)
}
