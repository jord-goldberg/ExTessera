package ny.gelato.extessera.feature.player

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.Menu
import kotlinx.android.synthetic.main.activity_player.*
import ny.gelato.extessera.App

import ny.gelato.extessera.R
import ny.gelato.extessera.base.BaseViewModel
import ny.gelato.extessera.feature.edit_character.EditCharacterActivity
import ny.gelato.extessera.feature.navigate.MainActivity
import ny.gelato.extessera.feature.player.view_model.CharacterModel
import ny.gelato.extessera.feature.player.view_model.NewCharacterModel
import ny.gelato.extessera.feature.search_5e.Search5eActivity
import javax.inject.Inject


class PlayerActivity : AppCompatActivity(), PlayerView {

    val component: PlayerComponent by lazy {
        DaggerPlayerComponent.builder()
                .appComponent(App.component)
                .playerModule(PlayerModule())
                .build()
    }

    val swipeToRemoveHelper: ItemTouchHelper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {

        override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?,
                            target: RecyclerView.ViewHolder?): Boolean = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val model = adapter.feed[position]
            when (model) {
                is CharacterModel -> {
                    val snackBar = Snackbar.make(coordinator, "Delete ${model.name.substringBefore(" ")}?", Snackbar.LENGTH_LONG)
                            .setAction("confirm") { _ ->
                                adapter.feed.removeAt(position)
                                adapter.notifyItemRemoved(position)
                                presenter.delete(model)
                            }
                            .addCallback(object : Snackbar.Callback() {
                                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                                    if (event != Snackbar.Callback.DISMISS_EVENT_ACTION)
                                        adapter.notifyItemChanged(position)
                                }
                            })
                    snackBar.show()
                }
            }
        }

        override fun getSwipeDirs(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder): Int {
            val model: BaseViewModel = adapter.feed[viewHolder.adapterPosition]
            if (model is CharacterModel)
                return super.getSwipeDirs(recyclerView, viewHolder)
            return 0
        }
    })

    @Inject lateinit var presenter: PlayerPresenter

    @Inject lateinit var adapter: PlayerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_d20)
        component.inject(this)

        fab.setOnClickListener { showCreateCharacter() }

        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@PlayerActivity)
            adapter = this@PlayerActivity.adapter
        }
        swipeToRemoveHelper.attachToRecyclerView(recycler_view)
        presenter.attachView(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_player_toolbar, menu)
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.apply {
            queryHint = "Spells, weapons, monsters..."
            setIconifiedByDefault(false)
            setOnClickListener { Search5eActivity.showSearchAll(this@PlayerActivity) }
            findViewById(android.support.v7.appcompat.R.id.search_src_text).setOnTouchListener { _, _ ->
                Search5eActivity.showSearchAll(this@PlayerActivity); true
            }
        }
        return true
    }

    override fun onStart() {
        super.onStart()
        presenter.start()
    }

    override fun onStop() {
        super.onStop()
        presenter.stop()
    }

    override fun showPlayer(feed: MutableList<BaseViewModel>) {
        adapter.feed = feed
    }


    override fun showCreateCharacter(newCharacter: NewCharacterModel?) {
        EditCharacterActivity.show(this)
    }

    override fun showCharacter(character: CharacterModel) {
        MainActivity.showCharacter(this, character.id)
    }
}
