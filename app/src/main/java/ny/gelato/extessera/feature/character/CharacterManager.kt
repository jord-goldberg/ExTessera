package ny.gelato.extessera.feature.character

import io.realm.Case
import io.realm.Realm
import ny.gelato.extessera.data.model.Weapon
import ny.gelato.extessera.data.model.character.*
import ny.gelato.extessera.feature.character.view_model.*
import rx.Observable
import java.util.*
import javax.inject.Inject

/**
 * Created by jord.goldberg on 5/2/17.
 */

class CharacterManager @Inject constructor(val realm: Realm, val id: String) : CharacterDataSource {

    override fun getCharacter(): Observable<Character> = realm.where(Character::class.java)
            .equalTo("id", id)
            .findFirstAsync()
            .asObservable<Character>()
            .filter { it.isLoaded }

    override fun updateAvatar(avatar: AvatarModel) {
        realm.executeTransactionAsync { realm ->
            val character = realm.where(Character::class.java).equalTo("id", id).findFirst()
            character.hasInspiration = avatar.isInspired
            character.imagePath = avatar.imagePath
            character.imageUrl = avatar.imageUrl
            character.updated = Date()
        }
    }

    override fun updateExp(experience: ExpModel) {
        realm.executeTransactionAsync { realm ->
            val character = realm.where(Character::class.java).equalTo("id", id).findFirst()
            character.exp = experience.current
            character.updated = Date()
        }
    }

    override fun updateLevel(level: LevelUpModel) {
        realm.executeTransactionAsync { realm ->
            val character = realm.where(Character::class.java).equalTo("id", id).findFirst()
            if (character.primary.job == level.selectedJob)
                character.levelUpPrimary()
            character.updated = Date()
        }
    }

    override fun updateDeathSaves(deathSaves: DeathSaveModel) {
        realm.executeTransactionAsync { realm ->
            val character = realm.where(Character::class.java).equalTo("id", id).findFirst()
            if (deathSaves.successes == 3) {
                character.successes = 0
                character.failures = 0
                character.hp = 1
            } else {
                character.successes = deathSaves.successes
                character.failures = deathSaves.failures
            }
            character.updated = Date()
        }
    }

    override fun updateStatus(status: StatusModel) {
        realm.executeTransactionAsync { realm ->
            val character = realm.where(Character::class.java).equalTo("id", id).findFirst()
            character.hp = status.hp
            character.armor = status.armor - character.dexterity.modifier()
            character.initiative = status.initiative - character.dexterity.modifier()
            character.speed = status.speed
            character.primary.dice = status.dice
            character.updated = Date()
        }
    }

    override fun updateHp(hp: HpModel) {
        realm.executeTransactionAsync { realm ->
            val character = realm.where(Character::class.java).equalTo("id", id).findFirst()
            character.hp = hp.current
            character.updated = Date()
        }
    }

    override fun updateMaxHp(maxHp: MaxHpModel) {
        realm.executeTransactionAsync { realm ->
            val character = realm.where(Character::class.java).equalTo("id", id).findFirst()
            character.maxHp = maxHp.current
            character.hp = maxHp.current
            character.updated = Date()
        }
    }

    override fun updateAbilities(abilities: AbilitiesModel) {
        realm.executeTransactionAsync { realm ->
            val character = realm.where(Character::class.java).equalTo("id", id).findFirst()
            character.strength.score = abilities.strength
            character.dexterity.score = abilities.dexterity
            character.constitution.score = abilities.constitution
            character.intelligence.score = abilities.intelligence
            character.wisdom.score = abilities.wisdom
            character.charisma.score = abilities.charisma
            character.updated = Date()
        }
    }

    override fun updateSaves(savingThrows: SavingThrowsModel) {
        realm.executeTransactionAsync { realm ->
            val character = realm.where(Character::class.java).equalTo("id", id).findFirst()
            character.strength.save = savingThrows.strSave
            character.dexterity.save = savingThrows.dexSave
            character.constitution.save = savingThrows.conSave
            character.intelligence.save = savingThrows.intSave
            character.wisdom.save = savingThrows.wisSave
            character.charisma.save = savingThrows.chaSave
            character.updated = Date()
        }
    }

    override fun updateSkill(skill: SkillModel) {
        realm.executeTransactionAsync { realm ->
            val character = realm.where(Character::class.java).equalTo("id", id).findFirst()
            character.skills.where().equalTo("type", skill.type.name).findFirst()
                    .proficiency = skill.proficiency
            character.updated = Date()
        }
    }

    override fun createNote(note: NoteModel) {
        realm.executeTransactionAsync { realm ->
            val character = realm.where(Character::class.java).equalTo("id", id).findFirst()
            character.notes.add(Note(text = note.text, isDone = note.isDone))
            character.updated = Date()
        }
    }

    override fun updateNote(note: NoteModel) {
        realm.executeTransactionAsync { realm ->
            val character = realm.where(Character::class.java).equalTo("id", id).findFirst()
            val characterNote = character.notes.where().equalTo("id", note.id).findFirst()
            characterNote.isDone = note.isDone
            character.updated = Date()
        }
    }

    override fun deleteNote(note: NoteModel) {
        realm.executeTransactionAsync { realm ->
            val character = realm.where(Character::class.java).equalTo("id", id).findFirst()
            val deleteNote = character.notes.where().equalTo("id", note.id).findFirst()
            character.notes.remove(deleteNote)
            character.updated = Date()
            deleteNote.deleteFromRealm()
        }
    }

    override fun deleteCheckedNotes() {
        realm.executeTransactionAsync { realm ->
            val character = realm.where(Character::class.java).equalTo("id", id).findFirst()
            character.notes
                    .filter { it.isDone }
                    .forEach {
                        character.notes.remove(it)
                        it.deleteFromRealm()
                    }
            character.updated = Date()
        }
    }

    override fun updateCoin(coin: CoinModel) {
        realm.executeTransactionAsync { realm ->
            val character = realm.where(Character::class.java).equalTo("id", id).findFirst()
            when (coin.type) {
                CoinModel.Type.COPPER -> character.copper = coin.amount
                CoinModel.Type.SILVER -> character.silver = coin.amount
                CoinModel.Type.ELECTRUM -> character.electrum = coin.amount
                CoinModel.Type.GOLD -> character.gold = coin.amount
                CoinModel.Type.PLATINUM -> character.platinum = coin.amount
            }
            character.updated = Date()
        }
    }

    override fun createEquipment(equipment: EquipmentModel) {
        realm.executeTransactionAsync { realm ->
            val character = realm.where(Character::class.java).equalTo("id", id).findFirst()
            val currentEquipment = character.equipment.where().equalTo("name", equipment.name, Case.INSENSITIVE)
                    .findFirst()

            if (currentEquipment == null) character.equipment.add(Equipment(equipment.name))
            else currentEquipment.number += 1
            character.updated = Date()
        }
    }

    override fun updateEquipment(equipment: EquipmentModel) {
        realm.executeTransactionAsync { realm ->
            val character = realm.where(Character::class.java).equalTo("id", id).findFirst()
            val currentEquipment = character.equipment.where().equalTo("name", equipment.name, Case.INSENSITIVE)
                    .findFirst()

            if (equipment.amount > 0) currentEquipment.number = equipment.amount
            else {
                character.equipment.remove(currentEquipment)
                currentEquipment.deleteFromRealm()
            }
            character.updated = Date()
        }
    }

    override fun deleteEquipment(equipment: EquipmentModel) {
        realm.executeTransactionAsync { realm ->
            val character = realm.where(Character::class.java).equalTo("id", id).findFirst()
            val deleteEquipment = character.equipment.where().equalTo("name", equipment.name, Case.INSENSITIVE)
                    .findFirst()
            character.equipment.remove(deleteEquipment)
            character.updated = Date()
            deleteEquipment.deleteFromRealm()
        }
    }

    override fun createWeapon(weaponCreate: WeaponCreateModel) {
        realm.executeTransactionAsync { realm ->
            val character = realm.where(Character::class.java).equalTo("id", id).findFirst()
            val weaponType = realm.where(Weapon::class.java).equalTo("name", weaponCreate.type.formatted)
                    .findFirst()
            val heldWeapon = HeldWeapon(
                    name = weaponCreate.name,
                    isSimple = weaponType.isSimple,
                    isRanged = weaponType.isRanged,
                    damage = weaponType.damage,
                    damageType = weaponType.damageType,
                    properties = weaponType.properties,
                    type = weaponType.type,
                    isCustom = true,
                    description = weaponCreate.description,
                    bonus = weaponCreate.bonus,
                    isProficient = weaponCreate.isProficient)
            character.weapons.add(heldWeapon)
            character.updated = Date()
        }
    }

    override fun deleteWeapon(weaponId: String) {
        realm.executeTransaction { realm ->
            val character = realm.where(Character::class.java).equalTo("id", id).findFirst()
            val deleteWeapon = character.weapons.where().equalTo("id", weaponId).findFirst()
            character.weapons.remove(deleteWeapon)
            character.updated = Date()
            deleteWeapon.deleteFromRealm()
        }
    }

    override fun updateSpell(spell: SpellModel) {
        realm.executeTransactionAsync { realm ->
            val character = realm.where(Character::class.java).equalTo("id", id).findFirst()
            val knownSpell = character.spells.where().equalTo("name", spell.name).findFirst()
            knownSpell.prepared = spell.prepared
            character.updated = Date()
        }
    }

    override fun deleteSpell(spellName: String) {
        realm.executeTransaction { realm ->
            val character = realm.where(Character::class.java).equalTo("id", id).findFirst()
            val deleteSpell = character.spells.where().equalTo("name", spellName).findFirst()
            character.spells.remove(deleteSpell)
            character.updated = Date()
            deleteSpell.deleteFromRealm()
        }
    }

    override fun updatePreference(preference: Preferences.Toggle) {
        realm.executeTransaction { realm ->
            val character = realm.where(Character::class.java).equalTo("id", id).findFirst()
            val preferences = character.preferences
            when (preference) {
                Preferences.Toggle.SORT_SKILLS -> preferences.sortSkillsByAbility = !preferences.sortSkillsByAbility
                Preferences.Toggle.SHOW_NOTES -> preferences.showNotes = !preferences.showNotes
                Preferences.Toggle.SHOW_SPELLS -> preferences.showSpells = !preferences.showSpells
            }
            character.updated = Date()
        }
    }
}