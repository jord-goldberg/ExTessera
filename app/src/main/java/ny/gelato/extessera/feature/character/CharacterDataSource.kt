package ny.gelato.extessera.feature.character

import ny.gelato.extessera.data.model.character.Character
import ny.gelato.extessera.data.model.character.Preferences
import ny.gelato.extessera.feature.character.view_model.*
import rx.Observable


/**
 * Created by jord.goldberg on 5/1/17.
 *
 * Interface implemented by CharacterManager - this contract outlines the CRUD functions/interactions
 * between a Character data model and this features view models
 */

interface CharacterDataSource {

    fun getCharacter(): Observable<Character>

    fun updateAvatar(avatar: AvatarModel)

    fun updateExp(experience: ExpModel)

    fun updateLevel(level: LevelUpModel)

    fun updateDeathSaves(deathSaves: DeathSaveModel)

    fun updateStatus(status: StatusModel)

    fun updateHp(hp: HpModel)

    fun updateMaxHp(maxHp: MaxHpModel)

    fun updateAbilities(abilities: AbilitiesModel)

    fun updateSaves(savingThrows: SavingThrowsModel)

    fun updateSkill(skill: SkillModel)


    fun createNote(note: NoteModel)

    fun updateNote(note: NoteModel)

    fun deleteNote(note: NoteModel)


    fun updateCoin(coin: CoinModel)

    fun createEquipment(equipment: EquipmentModel)

    fun updateEquipment(equipment: EquipmentModel)

    fun deleteEquipment(equipment: EquipmentModel)


    fun createWeapon(weapon: WeaponModel)

    fun createCustomWeapon(weaponCustom: WeaponCustomModel)

    fun deleteWeapon(weaponId: String)


    fun createSpell(spell: SpellModel)

    fun updateSpell(spell: SpellModel)

    fun deleteSpell(spellName: String)


    fun updatePreference(preference: Preferences.Toggle)
}
