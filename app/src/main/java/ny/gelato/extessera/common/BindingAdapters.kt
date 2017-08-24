package ny.gelato.extessera.common

import android.databinding.BindingAdapter
import com.robinhood.ticker.TickerUtils
import com.robinhood.ticker.TickerView

/**
 * Created by jord.goldberg on 8/24/17.
 */

@BindingAdapter("android:text")
fun showAmount(tickerView: TickerView, text: String) {
    tickerView.setCharacterList(TickerUtils.getDefaultNumberList())
    tickerView.setText(text)
}