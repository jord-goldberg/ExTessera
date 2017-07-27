package ny.gelato.extessera.feature.character.equipment

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_character_equipment.*
import ny.gelato.extessera.App
import ny.gelato.extessera.BR
import ny.gelato.extessera.R
import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.data.source.CharacterManager
import ny.gelato.extessera.feature.character.CharacterComponent
import ny.gelato.extessera.feature.character.CharacterModule
import ny.gelato.extessera.feature.character.DaggerCharacterComponent
import ny.gelato.extessera.feature.character.view_model.EquipmentModel
import rx.Observable
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by jord.goldberg on 7/26/17.
 */

class CharacterEquipmentFragment : Fragment(), CharacterEquipmentView {

    companion object {
        fun newInstance(id: String): CharacterEquipmentFragment =
                CharacterEquipmentFragment().apply {
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

    @Inject lateinit var characterManager: CharacterManager

    val subscriptions = CompositeSubscription()

    val equipmentAdapter = CharacterEquipmentAdapter(this)

    val swipeToRemoveHelper: ItemTouchHelper =
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
                override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?,
                                    target: RecyclerView.ViewHolder?): Boolean = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val model = equipmentAdapter.feed[position]
                    val snackBarText = "Remove ${model.name}?"
                    val snackBar = Snackbar.make(coordinator, snackBarText, Snackbar.LENGTH_LONG)
                            .setAction("confirm") { _ ->
                                equipmentAdapter.feed.removeAt(position)
                                equipmentAdapter.notifyItemRemoved(position)
                                characterManager.deleteEquipment(model)
                            }
                            .addCallback(object : Snackbar.Callback() {
                                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                                    if (event != Snackbar.Callback.DISMISS_EVENT_ACTION)
                                        equipmentAdapter.notifyItemChanged(position)
                                }
                            })
                    snackBar.show()
                }

                override fun getSwipeDirs(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder): Int {
                    val model: BaseViewModel = equipmentAdapter.feed[viewHolder.adapterPosition]
                    if (model is EquipmentModel)
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
        return inflater?.inflate(R.layout.fragment_character_equipment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.apply {
            adapter = equipmentAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        swipeToRemoveHelper.attachToRecyclerView(recycler_view)
        fab.setOnClickListener { showCreateEquipment() }
    }

    override fun onStart() {
        super.onStart()
        val subscription = characterManager.getCharacter()
                .flatMap { character ->
                    Observable.from(character.equipment)
                            .map { EquipmentModel(it) }
                            .toList()
                }
                .subscribe({ equipment -> showEquipment(equipment) },
                        { throwable -> Log.e("onSubscribe:", throwable.message, throwable) })

        subscriptions.add(subscription)
    }

    override fun onStop() {
        super.onStop()
        subscriptions.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        sheet.dismiss()
    }

    override fun onClick(v: View, viewModel: BaseViewModel) {
        viewModel as EquipmentModel
        when (viewModel.action) {
            BaseViewModel.Action.CREATE -> characterManager.createEquipment(viewModel)
            BaseViewModel.Action.DELETE -> characterManager.deleteEquipment(viewModel)
        }
    }

    override fun showEquipment(equipment: MutableList<EquipmentModel>) {
        equipmentAdapter.feed = equipment
    }

    override fun showCreateEquipment() {
        showBottomSheet(EquipmentModel(), R.layout.bottom_sheet_character_equipment_create)
    }

    private fun showBottomSheet(model: BaseViewModel, layoutRes: Int) {
        val binding: ViewDataBinding =
                DataBindingUtil.inflate(activity.layoutInflater, layoutRes, null, false)
        binding.apply {
            setVariable(BR.viewModel, model)
            setVariable(BR.parent, this@CharacterEquipmentFragment)
            setVariable(BR.sheet, sheet)
        }
        sheet.setContentView(binding.root)
        sheet.show()
    }
}