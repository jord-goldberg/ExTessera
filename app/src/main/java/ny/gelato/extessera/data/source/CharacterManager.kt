package ny.gelato.extessera.data.source

import io.realm.Realm
import ny.gelato.extessera.data.model.Spell
import ny.gelato.extessera.data.model.Weapon
import ny.gelato.extessera.data.model.character.*
import ny.gelato.extessera.feature.character_sheet.view_model.*
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

    override fun updateAvatar(avatar: AvatarModel) {
        realm.executeTransactionAsync { realm ->
            val character = realm.where(Character::class.java).equalTo("id", id).findFirst()
            character.name = avatar.name
            character.hasInspiration = avatar.isInspired
            character.imagePath = avatar.imagePath
            character.imageUrl = avatar.newImageUrl ?: avatar.imageUrl
            character.updated = Date()
        }
    }

    override fun updateExp(experience: ExpModel) {
        realm.executeTransactionAsync { realm ->
            val character = realm.where(Character::class.java).equalTo("id", id).findFirst()
            character.exp += experience.additional
        }
    }

    override fun updateLevel(level: LevelUpModel) {
        realm.executeTransactionAsync { realm ->
            val character = realm.where(Character::class.java).equalTo("id", id).findFirst()
            if (Job.Type.valueOf(character.primary.job) == level.selectedJob)
                character.primary.level += 1
            character.addLevelNotes()
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
            character.preferences.dcAbility = status.dcAbility.name
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
            character.skills.where().equalTo("type", skill.type.name).findFirst().update(skill)
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

    override fun addWeapon(weaponName: String) {
        realm.executeTransaction { realm ->
            val character = realm.where(Character::class.java).equalTo("id", id).findFirst()
            val newWeapon = realm.where(Weapon::class.java).equalTo("name", weaponName).findFirst()
            if (character.weapons.where().equalTo("name", newWeapon.name).findFirst()?.name != newWeapon.name) {
                character.weapons.add(newWeapon)
                character.updated = Date()
            }
        }
    }

    override fun removeWeapon(weaponName: String) {
        realm.executeTransaction { realm ->
            val character = realm.where(Character::class.java).equalTo("id", id).findFirst()
            val removeWeapon = character.weapons.where().equalTo("name", weaponName).findFirst()
            character.weapons.remove(removeWeapon)
            character.updated = Date()
        }
    }

    override fun learnSpell(spellName: String) {
        realm.executeTransaction { realm ->
            val character = realm.where(Character::class.java).equalTo("id", id).findFirst()
            val newSpell = realm.where(Spell::class.java).equalTo("name", spellName).findFirst().toSpellbook()
            if (character.spells.where().equalTo("name", newSpell.name).findFirst()?.name != newSpell.name) {
                character.spells.add(newSpell)
                character.updated = Date()
            }
        }
    }

    override fun updateSpell(spell: SpellModel) {
        realm.executeTransactionAsync { realm ->
            val character = realm.where(Character::class.java).equalTo("id", id).findFirst()
            val knownSpell = character.spells.where().equalTo("name", spell.name).findFirst()
            if (knownSpell == null) character.spells.add(KnownSpell(spell))
            else knownSpell.prepared = spell.prepared
            character.updated = Date()
        }
    }

    override fun forgetSpell(spellName: String) {
        realm.executeTransaction { realm ->
            val character = realm.where(Character::class.java).equalTo("id", id).findFirst()
            val deleteSpell = character.spells.where().equalTo("name", spellName).findFirst()
            character.spells.remove(deleteSpell)
            character.updated = Date()
            deleteSpell.deleteFromRealm()
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

    override fun fullRest() {
        realm.executeTransactionAsync { realm ->
            val character = realm.where(Character::class.java).equalTo("id", id).findFirst()
            character.spellSlots.longRest(character)
            character.updated = Date()
        }
    }
}