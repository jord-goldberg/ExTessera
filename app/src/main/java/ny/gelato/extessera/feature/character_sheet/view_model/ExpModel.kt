package ny.gelato.extessera.feature.character_sheet.view_model

import android.os.Handler
import android.support.design.widget.BottomSheetDialog
import ny.gelato.extessera.base.BaseViewModel
import java.text.NumberFormat

/**
 * Created by jord.goldberg on 5/23/17.
 */

data class ExpModel(
        var total: Int = 0,
        val toNextLevel: Int = 0,
        var additional: Int = 0

) : BaseViewModel() {

    fun hint(): String = "+$toNextLevel exp to next level"

    fun showExp(): String = NumberFormat.getInstance().format(total)

    fun setAdditional(exp: CharSequence) {
        if (exp.isEmpty()) additional = 0
        else additional = exp.toString().toInt()
        notifyChange()
    }

    fun addExp(sheet: BottomSheetDialog): ExpModel {
        total += additional
        notifyChange()
        Handler().postDelayed({ sheet.dismiss() }, 1000)
        return this
    }

    fun setExp(sheet: BottomSheetDialog): ExpModel {
        total = additional
        notifyChange()
        Handler().postDelayed({ sheet.dismiss() }, 1000)
        return this
    }
}