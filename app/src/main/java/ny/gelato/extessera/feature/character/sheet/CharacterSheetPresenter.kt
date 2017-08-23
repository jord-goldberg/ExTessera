package ny.gelato.extessera.feature.character.sheet

import ny.gelato.extessera.R
import ny.gelato.extessera.base.BasePresenter
import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.data.model.character.Character
import ny.gelato.extessera.data.model.character.Preferences
import ny.gelato.extessera.feature.character.CharacterManager
import ny.gelato.extessera.feature.character.*
import ny.gelato.extessera.feature.character.view_model.*
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject


/**
 * Created by jord.goldberg on 4/30/17.
 */

class CharacterSheetPresenter @Inject constructor(val characterManager: CharacterManager) : BasePresenter<CharacterSheetView>() {

    val subscriptions = CompositeSubscription()

    val feed = Observable.Transformer<Character, MutableList<BaseViewModel>> {
        it.filter { it.isValid }.map { character ->
            mutableListOf<BaseViewModel>().apply {
                add(AvatarModel(character))
                if (character.preferences.showNotes || character.hasToLevelUp())
                    addAll(character.noteModels())
                if (character.hp == 0)
                    add(DeathSaveModel(character))
                add(StatusModel(character))
                add(AbilitiesModel(character))
                add(SavingThrowsModel(character))
                addAll(character.skillModels())
                addAll(character.equipmentModelsSheet())
                addAll(character.weaponModels())
                if (character.preferences.showSpells)
                    addAll(character.spellModels())
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
                        { feed -> view.showCharacter(feed) },
                        { t -> t.printStackTrace() })

        subscriptions.add(subscription)
    }

    fun stop() = subscriptions.clear()

    fun routeOnClick(model: BaseViewModel) {
        when (model.action) {
            BaseViewModel.Action.CREATE -> create(model)
            BaseViewModel.Action.UPDATE -> update(model)
            BaseViewModel.Action.DELETE -> delete(model)
            else -> when (model) {
                is AvatarModel ->
                    if (view.isAtScrollTop())
                        view.showImageSelect(model)
                    else view.showScrollToTop()
                is AboutModel -> view.showAbout(AboutModel(character))
                is ExpModel -> view.showAddExp(ExpModel(character))
                is GoToModel -> when (model.destination) {
                    GoToModel.Destination.NONE -> view.showGoTo(model)
                    GoToModel.Destination.NOTES -> {
                        if (!character.preferences.showNotes)
                            characterManager.updatePreference(Preferences.Toggle.SHOW_NOTES)
                    }
                    GoToModel.Destination.ABILITIES -> view.showScrollToDestination(AbilitiesModel())
                    GoToModel.Destination.SAVES -> view.showScrollToDestination(SavingThrowsModel())
                    GoToModel.Destination.SKILLS -> view.showScrollToDestination(SkillModel())
                    GoToModel.Destination.WEAPONS -> view.showScrollToDestination(WeaponModel())
                    GoToModel.Destination.SPELLS -> {
                        if (!character.preferences.showSpells)
                            characterManager.updatePreference(Preferences.Toggle.SHOW_SPELLS)
                        view.showScrollToDestination(SpellModel())
                    }
                    GoToModel.Destination.EQUIPMENT -> view.showScrollToDestination(CoinModel())
                }
                is NoteModel -> if (model.isEmpty()) view.showCreateNote()
                is HpModel -> view.showEditHp(HpModel(character))
                is MaxHpModel -> view.showEditMaxHp(MaxHpModel(character))
                is SkillModel -> view.showSelectSkillProficiency(model)
                is WeaponModel -> view.showWeaponDetail(model)
                is SpellModel ->
                    if (model.isEmpty()) view.showSpellsFor(character)
                    else view.showSpellDetail(model)
                is CoinModel -> view.showCoin(model)
                is EquipmentModel ->
                    if (model.isEmpty()) view.showCreateEquipment()
                    else view.showEquipmentItem(model)
                is EquipmentFooterModel ->
                    if (model.equipmentSize > CoinModel.Type.values().size)
                        view.showEquipmentInventoryFor(character)
                    else view.showCreateEquipment()
            }
        }
    }

    fun routeOnLongClick(model: BaseViewModel): Boolean = when (model) {
        is AvatarModel -> {
            characterManager.updateAvatar(model)
            view.showHasInspiration(model)
            true
        }
        is NoteModel -> {
            view.showEditNote(model.copy())
            true
        }
        is StatusModel -> {
            characterManager.updateStatus(model)
            true
        }
        is MaxHpModel -> {
            view.showEditMaxHp(MaxHpModel(character))
            true
        }
        is AbilitiesModel -> {
            characterManager.updateAbilities(model)
            true
        }
        is SavingThrowsModel -> {
            characterManager.updateSaves(model)
            true
        }
        is SpellModel -> {
            characterManager.updateSpell(model)
            true
        }
        else -> false
    }

    fun onModelMenuClick(model: BaseViewModel, itemId: Int) {
        when (itemId) {
            R.id.action_avatar_edit_character -> view.showEditCharacter(character)
            R.id.action_avatar_select_picture -> view.showImageSelect(model as AvatarModel)
            R.id.action_avatar_toggle_inspiration -> {
                val avatar = (model as AvatarModel).toggleInspiration()
                characterManager.updateAvatar(avatar)
                view.showHasInspiration(avatar)
            }
            R.id.action_notes_create -> view.showCreateNote()
            R.id.action_notes_hide -> characterManager.updatePreference(Preferences.Toggle.SHOW_NOTES)
            R.id.action_death_save_reset -> characterManager.updateDeathSaves(DeathSaveModel())
            R.id.action_death_save_stabilize -> {
                characterManager.updateDeathSaves((model as DeathSaveModel).setSuccess(3))
                view.showIsStabilized()
            }
            R.id.action_status_long_rest -> view.showConfirmLongRest(model as StatusModel)
            R.id.action_status_edit_max_hp -> view.showEditMaxHp(MaxHpModel(character))
            R.id.action_skills_edit_proficiencies -> characterManager.updatePreference(Preferences.Toggle.EDIT_SKILLS)
            R.id.action_skills_toggle_sort -> characterManager.updatePreference(Preferences.Toggle.SORT_SKILLS)
            R.id.action_weapons_add -> view.showWeaponsFor(character)
            R.id.action_weapons_add_custom -> view.showCreateWeapon()
            R.id.action_spells_add -> view.showSpellsFor(character)
            R.id.action_spells_hide -> characterManager.updatePreference(Preferences.Toggle.SHOW_SPELLS)
            R.id.action_equipment_add -> view.showCreateEquipment()
        }
    }

    fun create(model: BaseViewModel) {
        when (model) {
            is NoteModel -> characterManager.createNote(model)
            is WeaponCreateModel -> characterManager.createWeapon(model)
            is EquipmentModel -> characterManager.createEquipment(model)
        }
    }

    fun update(model: BaseViewModel) {
        when (model) {
            is AvatarModel -> characterManager.updateAvatar(model)
            is ExpModel -> characterManager.updateExp(model)
            is LevelUpModel -> characterManager.updateLevel(model)
            is HeaderModel -> if (model.section == HeaderModel.Section.SKILLS)
                characterManager.updatePreference(Preferences.Toggle.EDIT_SKILLS)
            is NoteModel -> characterManager.updateNote(model)
            is DeathSaveModel -> {
                characterManager.updateDeathSaves(model)
                if (model.successes == 3) view.showIsStabilized()
            }
            is StatusModel -> characterManager.updateStatus(model)
            is HpModel -> characterManager.updateHp(model)
            is MaxHpModel -> characterManager.updateMaxHp(model)
            is AbilitiesModel -> characterManager.updateAbilities(model)
            is SavingThrowsModel -> characterManager.updateSaves(model)
            is SkillModel -> characterManager.updateSkill(model)
            is SpellModel -> characterManager.updateSpell(model)
            is CoinModel -> characterManager.updateCoin(model)
            is EquipmentModel -> characterManager.updateEquipment(model)
        }
    }

    fun delete(model: BaseViewModel) {
        when (model) {
            is NoteModel -> characterManager.deleteNote(model)
            is WeaponModel -> characterManager.deleteWeapon(model.id)
            is SpellModel -> characterManager.deleteSpell(model.name)
            is EquipmentModel -> characterManager.deleteEquipment(model)
        }
    }
}
