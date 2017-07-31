package ny.gelato.extessera.feature.search_5e.spell_search

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.mvp_view_wait_error_empty.*
import ny.gelato.extessera.R
import ny.gelato.extessera.data.model.Spell
import ny.gelato.extessera.databinding.BottomSheetSearchSpellFiltersBinding
import ny.gelato.extessera.feature.search_5e.*
import ny.gelato.extessera.feature.spell_detail.SpellDetailBottomFragment
import rx.Observable

/**
 * Created by jord.goldberg on 6/14/17.
 */

class SpellSearchFragment : Fragment(), SpellSearchView {

    companion object {
        fun newInstance(job: String = "", level: Int = 9): SpellSearchFragment =
                SpellSearchFragment().apply {
                    arguments = Bundle().apply {
                        putString("job", job)
                        putInt("level", level)
                    }
                }
    }

    val sheet: BottomSheetDialog by lazy {
        BottomSheetDialog(activity).apply {
            setOnDismissListener { showFiltering(presenter.filters.filtering()) }
            setContentView(filterBinding.root)
        }
    }

    val LAYOUT_KEY = "layout"

    var isAttached = false

    lateinit var parentView: Search5eView
    lateinit var filterBinding: BottomSheetSearchSpellFiltersBinding

    val presenter = SpellSearchPresenter()
    val search5eRecyclerAdapter = Search5eRecyclerAdapter(this)

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        parentView = context as Search5eView
        isAttached = true
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycler_view.apply {
            adapter = search5eRecyclerAdapter
            layoutManager = LinearLayoutManager(activity)
            layoutManager.onRestoreInstanceState(savedInstanceState?.getParcelable(LAYOUT_KEY))
        }

        filterBinding = DataBindingUtil.inflate(activity.layoutInflater, R.layout.bottom_sheet_search_spell_filters, null, false)
        presenter.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        sheet.dismiss()
        presenter.detachView()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.apply {
            putParcelable(LAYOUT_KEY, recycler_view.layoutManager.onSaveInstanceState())
            putParcelable("filters", presenter.filters)
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let { presenter.restoreFilters(it.getParcelable("filters")) }
    }

    // Since this fragment is displayed in a ViewPager, onResume isn't always called when the
    // fragment becomes visible. To make sure we display what we're filtering properly, we'll use
    // isVisibleToUser. We must check if we're attached first because this can be called out of lifecycle
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isAttached && isVisibleToUser)
            parentView.showFiltering(presenter.filters.filtering())
    }

    override fun onClick(v: View, any: Any) {
        when (any) {
            is Spell -> showSpellDetail(any)
        }
    }

    override fun queryText(): Observable<String> = parentView.queryText()

    override fun showFilterOptions() {
        filterBinding.presenter = presenter
        sheet.show()
    }

    // Have to check for visibility here, too, for when the presenter is first attached
    override fun showFiltering(filter: String) {
        if (userVisibleHint) parentView.showFiltering(filter)
    }

    override fun showResult(result: List<Any>) {
        text_empty.visibility = View.GONE
        search5eRecyclerAdapter.feed = result
    }

    override fun showEmpty() {
        text_empty.text = "No spells match that search"
        text_empty.visibility = View.VISIBLE
    }

    override fun showSpellDetail(spell: Spell) {
        SpellDetailBottomFragment.newInstance(spell.name).show(activity.supportFragmentManager, spell.name)
    }
}