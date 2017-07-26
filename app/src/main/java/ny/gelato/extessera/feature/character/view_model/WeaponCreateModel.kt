package ny.gelato.extessera.feature.character.view_model

import android.support.design.widget.BottomSheetDialog
import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.data.model.Weapon

/**
 * Created by jord.goldberg on 7/24/17.
 *
 * @layout bottom_sheet_character_weapon_create.xml
 */

data class WeaponCreateModel(
        var name: String = "",
        var description: String = "",
        var bonus: Int = 0,
        var type: Weapon.Type = Weapon.Type.CLUB,
        var isProficient: Boolean = false

) : BaseViewModel() {

    private val weapons = Weapon.Type.values()

    fun weaponTypes(): Array<String> = weapons.map { it.formatted }.toTypedArray()

    fun setName(nameInput: CharSequence) {
        name = nameInput.toString()
        notifyChange()
    }

    fun setDescription(descriptionInput: CharSequence) {
        description = descriptionInput.toString()
    }

    fun setBonus(bonusInput: CharSequence) {
        if (bonusInput.isEmpty())
            bonus = 0
        else bonus = bonusInput.toString().toInt()
    }

    fun selectType(position: Int) {
        type = weapons[position]
        notifyChange()
    }

    fun selectedTypePosition(): Int = type.ordinal

    fun toggleIsProficient(isChecked: Boolean) {
        isProficient = isChecked
        notifyChange()
    }

    fun validateInput(): Boolean = name.isNotBlank()

    fun createAndDismiss(sheet: BottomSheetDialog): WeaponCreateModel {
        action = Action.CREATE
        sheet.dismiss()
        return this
    }
}