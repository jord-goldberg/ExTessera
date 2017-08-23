package ny.gelato.extessera.feature.character.sheet

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.*
import android.widget.TextView
import android.widget.Toast

import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_character_sheet.*

import ny.gelato.extessera.App
import ny.gelato.extessera.BR
import ny.gelato.extessera.R
import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.data.model.character.Character
import ny.gelato.extessera.feature.character.view_model.*
import ny.gelato.extessera.feature.edit_character.EditCharacterActivity
import ny.gelato.extessera.feature.search_5e.Search5eActivity
import ny.gelato.extessera.feature.spell_detail.SpellDetailBottomFragment
import ny.gelato.extessera.common.SmoothGridLayoutManager
import ny.gelato.extessera.feature.character.CharacterComponent
import ny.gelato.extessera.feature.character.CharacterModule
import ny.gelato.extessera.feature.character.DaggerCharacterComponent
import ny.gelato.extessera.feature.character.equipment.CharacterEquipmentFragment


/**
 * Created by jord.goldberg on 4/30/17.
 */

class CharacterSheetFragment : Fragment(), CharacterSheetView {

    companion object {
        fun newInstance(id: String): CharacterSheetFragment = CharacterSheetFragment().apply {
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

    @Inject lateinit var presenter: CharacterSheetPresenter

    val sheetAdapter = CharacterSheetAdapter(this)

    val swipeToRemoveHelper: ItemTouchHelper =
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
                override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?,
                                    target: RecyclerView.ViewHolder?): Boolean = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val model = sheetAdapter.feed[position]

                    if (model is NoteModel) {
                        model.updateArchived()
                        presenter.update(model)
                        Snackbar.make(coordinator, "Note archived", Snackbar.LENGTH_LONG)
                                .setAction("undo") { _ -> presenter.update(model) }
                                .show()
                    } else {
                        val snackBarText = "Remove " + when (model) {
                            is WeaponModel -> model.name
                            is SpellModel -> model.name
                            is EquipmentModel -> model.name
                            else -> "note"
                        } + "?"
                        Snackbar.make(coordinator, snackBarText, Snackbar.LENGTH_LONG)
                                .setAction("confirm") { _ -> presenter.delete(model) }
                                .addCallback(object : Snackbar.Callback() {
                                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                                        if (event != Snackbar.Callback.DISMISS_EVENT_ACTION)
                                            sheetAdapter.notifyItemChanged(position)
                                    }
                                })
                                .show()
                    }
                }

                override fun getSwipeDirs(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder): Int {
                    val model: BaseViewModel = sheetAdapter.feed[viewHolder.adapterPosition]
                    if ((model is NoteModel && !model.isEmpty())
                            .or(model is WeaponModel && model.name != "Unarmed Strike")
                            .or(model is SpellModel && !model.isEmpty())
                            .or(model is EquipmentModel && !model.isEmpty()))
                        return super.getSwipeDirs(recyclerView, viewHolder)
                    return 0
                }
            })

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
            adapter = this@CharacterSheetFragment.sheetAdapter
            layoutManager = SmoothGridLayoutManager(context, 6).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        val model = this@CharacterSheetFragment.sheetAdapter.feed[position]
                        return when (model) {
                            is SkillModel -> 3
                            is SkillSubheaderModel -> 3
                            is CoinModel -> 2
                            is EquipmentModel -> 4
                            else -> 6
                        }
                    }
                }
            }
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

    override fun onClick(v: View, viewModel: BaseViewModel) {
        if (viewModel.action == BaseViewModel.Action.CONTEXT_MENU) {
            showPopupMenu(v, viewModel)
        } else presenter.routeOnClick(viewModel)
    }

    override fun onLongClick(v: View, viewModel: BaseViewModel): Boolean {
        if (viewModel.action == BaseViewModel.Action.CONTEXT_MENU) {
            showPopupMenu(v, viewModel)
        }
        return presenter.routeOnLongClick(viewModel)
    }

    override fun showCharacter(feed: MutableList<BaseViewModel>) {
        sheetAdapter.feed = feed
    }

    override fun showEditCharacter(character: Character) {
        EditCharacterActivity.show(activity, character.id)
    }

    override fun isAtScrollTop(): Boolean {
        val position = (recycler_view.layoutManager as GridLayoutManager)
                .findFirstCompletelyVisibleItemPosition()
        return position == 0 || position == RecyclerView.NO_POSITION
    }

    override fun showScrollToTop() {
        recycler_view.smoothScrollToPosition(0)
    }

    override fun showImageSelect(avatar: AvatarModel) {
        showBottomSheet(avatar, R.layout.bottom_sheet_character_avatar)
    }

    override fun showHasInspiration(avatar: AvatarModel) {
        if (avatar.isInspired) Toast.makeText(activity, "${avatar.name.substringBefore(" ")} " +
                "has gained inspiration!\n+1 to role-playing", Toast.LENGTH_SHORT)
                .apply {
                    (view.findViewById(android.R.id.message) as TextView).gravity = Gravity.CENTER
                    show()
                }
    }

    override fun showAbout(about: AboutModel) {
        showBottomSheet(about, R.layout.bottom_sheet_character_about)
    }

    override fun showAddExp(additional: ExpModel) {
        showBottomSheet(additional, R.layout.bottom_sheet_character_exp_add)
    }

    override fun showGoTo(goTo: GoToModel) {
        showBottomSheet(goTo, R.layout.bottom_sheet_character_go_to)
    }

    override fun showScrollToDestination(destination: BaseViewModel) {
        // The delay is to allow any hidden destinations to appear if necessary
        Handler().postDelayed({
            for (i in 0 until sheetAdapter.feed.size)
                if (sheetAdapter.feed[i].javaClass == destination.javaClass) {
                    recycler_view.smoothScrollToPosition(i)
                    break
                }
        }, 200)
    }

    override fun showCreateNote() {
        showBottomSheet(NoteModel(), R.layout.bottom_sheet_character_note_create)
    }

    override fun showEditNote(note: NoteModel) {
        showBottomSheet(note, R.layout.bottom_sheet_character_note_edit)
    }

    override fun showIsStabilized() {
        Toast.makeText(activity, "Stabilized with 1 Hit Point", Toast.LENGTH_SHORT).show()
    }

    override fun showEditHp(hp: HpModel) {
        showBottomSheet(hp, R.layout.bottom_sheet_character_hp)
    }

    override fun showEditMaxHp(maxHp: MaxHpModel) {
        showBottomSheet(maxHp, R.layout.bottom_sheet_character_max_hp)
    }

    override fun showConfirmLongRest(status: StatusModel) {
        showBottomSheet(status, R.layout.bottom_sheet_character_long_rest)
    }

    override fun showSelectSkillProficiency(skill: SkillModel) {
        showBottomSheet(skill, R.layout.bottom_sheet_character_skill)
    }

    override fun showCoin(coin: CoinModel) {
        showBottomSheet(coin, R.layout.bottom_sheet_character_coin)
    }

    override fun showCreateEquipment() {
        showBottomSheet(EquipmentModel(), R.layout.bottom_sheet_character_equipment_create)
    }

    override fun showEquipmentItem(equipment: EquipmentModel) {
        showBottomSheet(equipment, R.layout.bottom_sheet_character_equipment_item)
    }

    override fun showEquipmentInventoryFor(character: Character) {
        val fragment = CharacterEquipmentFragment.newInstance(character.id)
        fragmentManager.beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in_bottom,
                        R.anim.zoom_and_fade_out,
                        R.anim.zoom_and_fade_in,
                        R.anim.slide_out_bottom)
                .replace(R.id.container, fragment)
                .addToBackStack("equipment:${character.id}")
                .commit()
    }

    override fun showWeaponDetail(weapon: WeaponModel) {
        showBottomSheet(weapon, R.layout.bottom_sheet_character_weapon)
    }

    override fun showWeaponsFor(character: Character) {
        Search5eActivity.showWeaponSearch(activity)
    }

    override fun showCreateWeapon() {
        showBottomSheet(WeaponCreateModel(), R.layout.bottom_sheet_character_weapon_create)
    }

    override fun showSpellDetail(spell: SpellModel) {
        SpellDetailBottomFragment.newInstance(spell.name, true).show(fragmentManager, spell.name)
    }

    override fun showSpellsFor(character: Character) {
        Search5eActivity.showSpellSearch(activity, character.primary.job.name, character.primary.spellLevel())
    }

    private fun showBottomSheet(model: BaseViewModel, layoutRes: Int) {
        val binding: ViewDataBinding =
                DataBindingUtil.inflate(activity.layoutInflater, layoutRes, null, false)
        binding.apply {
            setVariable(BR.viewModel, model)
            setVariable(BR.parent, this@CharacterSheetFragment)
            setVariable(BR.sheet, sheet)
        }
        sheet.setContentView(binding.root)
        sheet.show()
    }

    private fun showPopupMenu(view: View, model: BaseViewModel) {
        val menuRes = when (model) {
            is HeaderModel -> model.menuRes
            is AvatarModel -> R.menu.menu_character_avatar
            is DeathSaveModel -> R.menu.menu_character_death_saves
            is StatusModel -> R.menu.menu_character_status
            else -> 0
        }
        val isSkillMenu = menuRes == R.menu.menu_character_skills
        val isSortedByAbility = presenter.character.preferences.sortSkillsByAbility

        PopupMenu(activity, view).apply {
            menuInflater.inflate(menuRes, menu)
            setOnMenuItemClickListener { presenter.onModelMenuClick(model, it.itemId); true }
            if (isSkillMenu) {
                menu.findItem(R.id.action_skills_toggle_sort).title =
                        if (isSortedByAbility) "Sort by Name"
                        else "Sort by Ability"
            }
            show()
        }
    }
}
