package ny.gelato.extessera.feature.edit_character.edit_proficiencies

import android.os.Parcel
import android.os.Parcelable
import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.data.model.character.Proficiency

/**
 * Created by jord.goldberg on 6/26/17.
 *
 * @layout item_edit_proficiency.xml
 */

data class EditProficiencyModel(
        val name: String,
        val proficiency: String,
        val type: Proficiency.Type,
        var isChecked: Boolean = false

) : BaseViewModel(), Parcelable {

    constructor(tool: Proficiency. Tool) : this(tool.name, tool.formatted, Proficiency.Type.TOOL)

    constructor(language: Proficiency. Language) : this(language.name, language.formatted, Proficiency.Type.LANGUAGE)

    fun toggleIsChecked(): EditProficiencyModel {
        isChecked = !isChecked
        notifyChange()
        return this
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<EditProficiencyModel> = object : Parcelable.Creator<EditProficiencyModel> {
            override fun createFromParcel(source: Parcel): EditProficiencyModel = EditProficiencyModel(source)
            override fun newArray(size: Int): Array<EditProficiencyModel?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            Proficiency.Type.values()[source.readInt()],
            1 == source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeString(proficiency)
        dest.writeInt(type.ordinal)
        dest.writeInt((if (isChecked) 1 else 0))
    }
}