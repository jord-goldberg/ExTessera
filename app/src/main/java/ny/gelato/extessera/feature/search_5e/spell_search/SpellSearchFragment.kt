package ny.gelato.extessera.feature.search_5e.spell_search

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.mvp_view_wait_error_empty.*
import ny.gelato.extessera.App
import ny.gelato.extessera.R
import ny.gelato.extessera.data.model.Spell
import ny.gelato.extessera.databinding.BottomSheetSearchSpellFiltersBinding
import ny.gelato.extessera.feature.search_5e.*
import ny.gelato.extessera.feature.spell_detail.SpellDetailBottomFragment
import rx.Observable
import javax.inject.Inject

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

    val component: SpellSearchComponent by lazy {
        val job = arguments.getString("job")
        val level = arguments.getInt("level")
        DaggerSpellSearchComponent.builder()
                .appComponent(App.component)
                .spellSearchModule(SpellSearchModule(job, level))
                .build()
    }

    val sheet: BottomSheetDialog by lazy {
        BottomSheetDialog(activity).apply {
            setOnDismissListener { showFiltering(presenter.filters.filtering()) }
            setContentView(filterBinding.root)
        }
    }

    val LAYOUT_KEY = "layout"

    var isCreated = false

    lateinit var search5eView: Search5eView
    lateinit var filterBinding: BottomSheetSearchSpellFiltersBinding

    @Inject lateinit var presenter: SpellSearchPresenter
    lateinit var adapter: Search5eRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        search5eView = activity as Search5eView
        component.inject(this)
        adapter = Search5eRecyclerAdapter(this)
        isCreated = true
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycler_view.apply {
            adapter = this@SpellSearchFragment.adapter
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

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isCreated) search5eView.showFiltering(presenter.filters.filtering())
    }

    override fun onClick(v: View, any: Any) {
        when (any) {
            is Spell -> showSpellDetail(any)
        }
    }

    override fun queryText(): Observable<String> = search5eView.queryText()

    override fun showResult(result: List<Any>) {
        text_empty.visibility = View.GONE
        adapter.feed = result
    }

    override fun showEmpty() {
        text_empty.text = "No spells match that search"
        text_empty.visibility = View.VISIBLE
    }

    override fun showSpellDetail(spell: Spell) {
        SpellDetailBottomFragment.newInstance(spell.name).show(activity.supportFragmentManager, spell.name)
    }

    override fun showFilterOptions() {
        filterBinding.presenter = presenter
        sheet.show()
    }

    override fun showFiltering(filter: String) {
        if (userVisibleHint) search5eView.showFiltering(filter)
    }
}