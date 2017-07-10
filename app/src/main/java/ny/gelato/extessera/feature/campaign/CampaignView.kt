package ny.gelato.extessera.feature.campaign

import ny.gelato.extessera.base.BaseView
import ny.gelato.extessera.base.BaseViewModel

/**
 * Created by jord.goldberg on 6/19/17.
 */

interface CampaignView : BaseView {

    fun showCampaign(feed: MutableList<BaseViewModel>)
}