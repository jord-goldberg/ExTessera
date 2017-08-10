package ny.gelato.extessera.feature.edit_character.edit_proficiencies

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_edit_character_proficiencies.*
import ny.gelato.extessera.App
import ny.gelato.extessera.R
import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.data.model.character.Character
import ny.gelato.extessera.data.model.character.Proficiency
import ny.gelato.extessera.feature.edit_character.EditCharacterView

/**
 * Created by jord.goldberg on 6/26/17.
 */

class EditProficienciesFragment : Fragment(), EditCharacterView {

    companion object {
        fun newInstance(id: String?): EditProficienciesFragment = EditProficienciesFragment().apply {
            arguments = Bundle().apply { putString("id", id) }
        }
    }

    val id: String by lazy(LazyThreadSafetyMode.NONE) { arguments.getString("id") }

    val character: Character by lazy {
        App.component.realm()
                .where(Character::class.java)
                .equalTo("id", id)
                .findFirst()
    }

    val proficiencies: MutableList<EditProficiencyModel> = mutableListOf<EditProficiencyModel>().apply {
        addAll(Proficiency.Tool.values().map { EditProficiencyModel(it) })
        addAll(Proficiency.Language.values().map { EditProficiencyModel(it) })
    }

    lateinit var editProficienciesAdapter: EditProficienciesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_edit_character_proficiencies, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        character.proficiencies
                .filter { it.type == Proficiency.Type.TOOL || it.type == Proficiency.Type.LANGUAGE }
                .forEach { proficiency ->
                    proficiencies.find { it.proficiency == proficiency.name }
                            ?.toggleIsChecked()
                }

        editProficienciesAdapter = EditProficienciesAdapter(this)
                .apply { feed = proficienciesWithHeaders() }

        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = editProficienciesAdapter
        }

    }

    override fun onResume() {
        super.onResume()
        activity.title = "Tool & Language Proficiencies"
    }

    override fun onClick(v: View, viewModel: BaseViewModel) {
        if (viewModel is EditProficiencyModel)
            App.component.realm().executeTransaction { realm ->
                val character = realm.where(Character::class.java)
                        .equalTo("id", id)
                        .findFirst()

                if (viewModel.isChecked)
                    character.proficiencies.add(Proficiency(viewModel.type.name, viewModel.proficiency))
                else character.proficiencies.where().equalTo("name", viewModel.proficiency)
                        .findFirst().deleteFromRealm()
            }
    }

    override fun showNext() {
        Handler().postDelayed({ activity.finish() }, 200)
    }

    private fun proficienciesWithHeaders(): List<Any> = mutableListOf<Any>().apply {
        var prevHeader = ""
        for (i in 0 until proficiencies.size) {
            val header = header(proficiencies[i])
            if (header != prevHeader) {
                add(header)
                prevHeader = header
            }
            add(proficiencies[i])
        }
    }

    private fun header(model: EditProficiencyModel): String = when (model.type) {
        Proficiency.Type.TOOL -> when (Proficiency.Tool.valueOf(model.name).type) {
            Proficiency.Tool.Type.ARTISAN -> "Artisan's Tools"
            Proficiency.Tool.Type.GAMING -> "Gaming Sets"
            Proficiency.Tool.Type.MUSIC -> "Musical Instruments"
            Proficiency.Tool.Type.OTHER -> "Other Tools"
        }
        Proficiency.Type.LANGUAGE ->
            if (Proficiency.Language.valueOf(model.name).isCommon) "Standard Languages"
            else "Exotic Languages"
        else -> "Other Proficiencies"
    }
}