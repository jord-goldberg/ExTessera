package ny.gelato.extessera.feature.character_sheet.view_model

import ny.gelato.extessera.base.BaseViewModel

/**
 * Created by jord.goldberg on 5/12/17.
 */

data class HeaderModel(
        val title: String,
        val avatar: AvatarModel,
        val menuRes: Int,
        val titleInfo: String = ""

) : BaseViewModel()