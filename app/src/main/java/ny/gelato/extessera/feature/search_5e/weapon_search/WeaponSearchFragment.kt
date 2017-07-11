package ny.gelato.extessera.feature.search_5e.weapon_search

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomSheetDialog
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.mvp_view_wait_error_empty.*
import ny.gelato.extessera.App
import ny.gelato.extessera.R
import ny.gelato.extessera.data.model.Weapon
import ny.gelato.extessera.data.model.character.Character
import ny.gelato.extessera.data.source.CharacterManager
import ny.gelato.extessera.databinding.BottomSheetWeaponFiltersBinding
import ny.gelato.extessera.feature.edit_character.EditCharacterActivity
import ny.gelato.extessera.feature.search_5e.Search5eRecyclerAdapter
import ny.gelato.extessera.feature.search_5e.Search5eView
import rx.Observable

/**
 * Created by jord.goldberg on 6/14/17.
 */

class WeaponSearchFragment : Fragment(), WeaponSearchView {

    companion object {
        fun newInstance(): WeaponSearchFragment = WeaponSearchFragment()
    }

    val characters: RealmResults<Character> by lazy {
        App.component.realm()
                .where(Character::class.java)
                .findAllSorted("updated", Sort.DESCENDING)
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
    lateinit var filterBinding: BottomSheetWeaponFiltersBinding

    val presenter = WeaponSearchPresenter()
    val adapter = Search5eRecyclerAdapter(presenter)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        search5eView = activity as Search5eView
        isCreated = true
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycler_view.apply {
            adapter = this@WeaponSearchFragment.adapter
            layoutManager = LinearLayoutManager(activity)
            layoutManager.onRestoreInstanceState(savedInstanceState?.getParcelable(LAYOUT_KEY))
        }

        filterBinding = DataBindingUtil.inflate(activity.layoutInflater, R.layout.bottom_sheet_weapon_filters, null, false)
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
        savedInstanceState?.let { presenter.filters.restore(it.getParcelable("filters")) }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isCreated) search5eView.showFiltering(presenter.filters.filtering())
    }

    override fun queryText(): Observable<String> = search5eView.queryText()

    override fun showResult(result: List<Any>) {
        text_empty.visibility = View.GONE
        adapter.feed = result
    }

    override fun showEmpty() {
        text_empty.text = "No weapons match that search"
        text_empty.visibility = View.VISIBLE
    }

    override fun showFilterOptions() {
        filterBinding.presenter = presenter
        sheet.show()
    }

    override fun showFiltering(filter: String) {
        if (userVisibleHint) search5eView.showFiltering(filter)
    }

    override fun showAddWeapon(v: View, weapon: Weapon) {
        PopupMenu(activity, v, Gravity.END).apply {
            if (characters.isNotEmpty()) characters.forEachIndexed { index, character ->
                val name = character.name.substringBefore(" ")
                menu.add(0, index, index, "Add to $name")
            }
            setOnMenuItemClickListener {
                CharacterManager(App.component.realm(), characters[it.itemId].id).addWeapon(weapon.name)
                Handler().postDelayed({ dismiss(); activity.finish() }, 200)
                true
            }
            show()
        }
    }
}
