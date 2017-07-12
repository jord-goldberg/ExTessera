package ny.gelato.extessera.feature.character_sheet

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import kotlinx.android.synthetic.main.fragment_character_sheet.*

import ny.gelato.extessera.App
import ny.gelato.extessera.BR
import ny.gelato.extessera.R
import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.data.model.character.Character
import ny.gelato.extessera.feature.character_sheet.view_model.*
import ny.gelato.extessera.feature.edit_character.EditCharacterActivity
import ny.gelato.extessera.feature.search_5e.Search5eActivity
import ny.gelato.extessera.feature.spell_detail.SpellDetailBottomFragment
import javax.inject.Inject
import android.view.Gravity
import android.widget.TextView
import ny.gelato.extessera.common.SmoothStaggeredLayoutManager


/**
 * Created by jord.goldberg on 4/30/17.
 */

class CharacterFragment : Fragment(), CharacterView {

    companion object {
        fun newInstance(id: String): CharacterFragment = CharacterFragment().apply {
            arguments = Bundle().apply { putString("id", id) }
        }
    }

    val component: CharacterComponent by lazy {
        val id = arguments.getString("id")
        DaggerCharacterComponent.builder()
                .appComponent(App.component)
                .characterModule(CharacterModule(id))
                .build()
    }

    val sheet: BottomSheetDialog by lazy {
        BottomSheetDialog(activity)
    }

    val swipeToRemoveHelper: ItemTouchHelper by lazy {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
            override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?,
                                target: RecyclerView.ViewHolder?): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val model = adapter.feed.removeAt(position)
                when (model) {
                    is NoteModel -> {
                        adapter.notifyItemRemoved(position)
                        presenter.delete(model)
                    }
                    is WeaponModel -> {
                        val snackBar = Snackbar.make(coordinator, "Removed ${model.name}", Snackbar.LENGTH_LONG)
                                .setAction("undo") { _ ->
                                    adapter.feed.add(position, model)
                                    adapter.notifyItemInserted(position)
                                    presenter.save(model)
                                }
                        adapter.notifyItemRemoved(position)
                        presenter.delete(model)
                        snackBar.show()
                    }
                    is SpellModel -> {
                        val snackBar = Snackbar.make(coordinator, "Removed ${model.name}", Snackbar.LENGTH_LONG)
                                .setAction("undo") { _ ->
                                    adapter.feed.add(position, model)
                                    adapter.notifyItemInserted(position)
                                    presenter.save(model)
                                }
                        adapter.notifyItemRemoved(position)
                        presenter.delete(model)
                        snackBar.show()
                    }
                }
            }

            override fun getSwipeDirs(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder): Int {
                val model: BaseViewModel = adapter.feed[viewHolder.adapterPosition]
                if ((model is NoteModel && model.isDone)
                        .or(model is WeaponModel && model.name != "Unarmed Strike")
                        .or(model is SpellModel))
                    return super.getSwipeDirs(recyclerView, viewHolder)
                return 0
            }
        })
    }

    @Inject lateinit var presenter: CharacterPresenter

    @Inject lateinit var adapter: CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_character_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.apply {
            layoutManager = SmoothStaggeredLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = this@CharacterFragment.adapter
        }
        swipeToRemoveHelper.attachToRecyclerView(recycler_view)
        presenter.attachView(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.start()
    }

    override fun onStop() {
        super.onStop()
        presenter.stop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        sheet.dismiss()
        presenter.detachView()
    }

    override fun showCharacter(feed: MutableList<BaseViewModel>) {
        adapter.feed = feed
    }

    override fun showEditCharacter(character: Character) {
        EditCharacterActivity.show(activity, character.id)
    }

    override fun isAtScrollTop(): Boolean =
            (recycler_view.layoutManager as StaggeredGridLayoutManager)
                    .findFirstCompletelyVisibleItemPositions(IntArray(2)).contains(0)

    override fun showScrollToTop() {
        recycler_view.smoothScrollToPosition(0)
    }

    override fun showImageSelect(avatar: AvatarModel) {
        val binding: ViewDataBinding = DataBindingUtil.inflate(activity.layoutInflater,
                R.layout.bottom_sheet_character_avatar, null, false)
        binding.apply {
            setVariable(BR.viewModel, avatar)
            setVariable(BR.presenter, presenter)
            setVariable(BR.sheet, sheet)
        }
        sheet.setContentView(binding.root)
        sheet.show()
    }

    override fun showHasInspiration(avatar: AvatarModel) {
        Toast.makeText(activity, "${avatar.name.substringBefore(" ")} has gained inspiration!" +
                "\n+1 to role-playing", Toast.LENGTH_SHORT)
                .apply {
                    (view.findViewById(android.R.id.message) as TextView).gravity = Gravity.CENTER
                    show()
                }
    }

    override fun showAbout(about: AboutModel) {
        val binding: ViewDataBinding = DataBindingUtil.inflate(activity.layoutInflater,
                R.layout.bottom_sheet_character_about, null, false)
        binding.apply {
            setVariable(BR.viewModel, about)
            setVariable(BR.presenter, presenter)
        }
        sheet.setContentView(binding.root)
        sheet.show()
    }

    override fun showAddExp(additional: ExpModel) {
        val binding: ViewDataBinding = DataBindingUtil.inflate(activity.layoutInflater,
                R.layout.bottom_sheet_character_add_exp, null, false)
        binding.apply {
            setVariable(BR.viewModel, additional)
            setVariable(BR.presenter, presenter)
            setVariable(BR.sheet, sheet)
        }
        sheet.setContentView(binding.root)
        sheet.show()
    }

    override fun showGoTo(goTo: GoToModel) {
        val binding: ViewDataBinding = DataBindingUtil.inflate(activity.layoutInflater,
                R.layout.bottom_sheet_character_go_to, null, false)
        binding.apply {
            setVariable(BR.viewModel, goTo)
            setVariable(BR.presenter, presenter)
            setVariable(BR.sheet, sheet)
        }
        sheet.setContentView(binding.root)
        sheet.show()
    }

    override fun showScrollToDestination(destination: BaseViewModel) {
        Handler().postDelayed({
            for (i in 0 until adapter.feed.size)
                if (adapter.feed[i].javaClass == destination.javaClass) {
                    recycler_view.smoothScrollToPosition(i)
                    break
                }
        }, 200)
    }

    override fun showEditHp(hp: HpModel) {
        val binding: ViewDataBinding = DataBindingUtil.inflate(activity.layoutInflater,
                R.layout.bottom_sheet_character_hp, null, false)
        binding.apply {
            setVariable(BR.viewModel, hp)
            setVariable(BR.presenter, presenter)
            setVariable(BR.sheet, sheet)
        }
        sheet.setContentView(binding.root)
        sheet.show()
    }

    override fun showEditMaxHp(maxHp: MaxHpModel) {
        val binding: ViewDataBinding = DataBindingUtil.inflate(activity.layoutInflater,
                R.layout.bottom_sheet_character_max_hp, null, false)
        binding.apply {
            setVariable(BR.viewModel, maxHp)
            setVariable(BR.presenter, presenter)
            setVariable(BR.sheet, sheet)
        }
        sheet.setContentView(binding.root)
        sheet.show()
    }

    override fun showSelectDcAbility(status: StatusModel) {
        val binding: ViewDataBinding = DataBindingUtil.inflate(activity.layoutInflater,
                R.layout.bottom_sheet_character_dc_ability, null, false)
        binding.apply {
            setVariable(BR.viewModel, status)
            setVariable(BR.presenter, presenter)
            setVariable(BR.sheet, sheet)
        }
        sheet.setContentView(binding.root)
        sheet.show()
    }

    override fun showSelectSkillProficiency(skill: SkillModel) {
        val binding: ViewDataBinding = DataBindingUtil.inflate(activity.layoutInflater,
                R.layout.bottom_sheet_character_skill, null, false)
        binding.apply {
            setVariable(BR.viewModel, skill)
            setVariable(BR.presenter, presenter)
            setVariable(BR.sheet, sheet)
        }
        sheet.setContentView(binding.root)
        sheet.show()
    }


    override fun showWeaponDetail(weapon: WeaponModel) {
        val binding: ViewDataBinding = DataBindingUtil.inflate(activity.layoutInflater,
            R.layout.bottom_sheet_character_weapon, null, false)
        binding.apply {
            setVariable(BR.viewModel, weapon)
            setVariable(BR.presenter, presenter)
            setVariable(BR.sheet, sheet)
        }
        sheet.setContentView(binding.root)
        sheet.show()
    }

    override fun showWeaponsFor(character: Character) {
        Search5eActivity.showWeaponSearch(activity)
    }

    override fun showSpellDetail(spell: SpellModel) {
        SpellDetailBottomFragment.newInstance(spell.name, true).show(fragmentManager, spell.name)
    }

    override fun showSpellsFor(character: Character) {
        Search5eActivity.showSpellSearch(activity, character.primary.job, character.primary.spellLevel())
    }

    override fun showPopupMenu(view: View, menuRes: Int) {
        val isSkillMenu = menuRes == R.menu.menu_character_skills
        val isSortedByAbility = presenter.character.preferences.sortSkillsByAbility

        PopupMenu(activity, view).apply {
            menuInflater.inflate(menuRes, menu)
            setOnMenuItemClickListener { presenter.menuClick(it); true }
            if (isSkillMenu) {
                menu.findItem(R.id.action_skills_toggle_sort).title =
                        if (isSortedByAbility) "Sort by Name"
                        else "Sort by Ability"
            }
            show()
        }
    }
}
