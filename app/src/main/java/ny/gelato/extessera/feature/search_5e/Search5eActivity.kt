package ny.gelato.extessera.feature.search_5e

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.SearchView
import android.view.Menu
import com.jakewharton.rxbinding.support.v7.widget.RxSearchView
import kotlinx.android.synthetic.main.activity_search_5e.*

import ny.gelato.extessera.R
import ny.gelato.extessera.common.DepthPageTransformer
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.subjects.PublishSubject
import java.util.concurrent.TimeUnit


class Search5eActivity : AppCompatActivity(), Search5eView {

    companion object {
        fun showSearchAll(context: Context) =
                context.startActivity(Intent(context, Search5eActivity::class.java))

        fun showSpellSearch(context: Context, job: String = "", level: Int = 9) {
            val intent = Intent(context, Search5eActivity::class.java)
            intent.apply {
                putExtra("search", "spells")
                putExtra("job", job)
                putExtra("level", level)
            }
            context.startActivity(intent)
        }

        fun showWeaponSearch(context: Context) {
            val intent = Intent(context, Search5eActivity::class.java)
            intent.putExtra("search", "weapons")
            context.startActivity(intent)
        }
    }

    val snackBar: Snackbar by lazy {
        Snackbar.make(coordinator, "", Snackbar.LENGTH_INDEFINITE)
                .setAction("change") { _ -> showFilterOptions() }
    }

    val pagerAdapter = Search5ePagerAdapter(supportFragmentManager)

    val QUERY_KEY = "savedQuery"

    val querySubject: PublishSubject<String> = PublishSubject.create()

    var savedQuery: String = ""

    var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_5e)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        view_pager.apply {
            adapter = pagerAdapter
            setPageTransformer(true, DepthPageTransformer())
        }
        tabs.setupWithViewPager(view_pager)
        image_settings.setOnClickListener { showFilterOptions() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search_toolbar, menu)
        searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView?.apply {
            queryHint = ""
            setIconifiedByDefault(false)
            setQuery(savedQuery, false)
            RxSearchView.queryTextChanges(this)
                    .debounce(200, TimeUnit.MILLISECONDS)
                    .map { it.toString() }
                    .doOnNext { savedQuery = it }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { querySubject.onNext(it) }
        }
        // This is done here because the search fragments are non-null
        // and the scope is open for the searchView anyway
        intent.extras?.let {
            when (it.getString("search")) {
                "spells" -> {
                    pagerAdapter.showSpellsForCharacter(it.getString("job"), it.getInt("level"))
                    view_pager.currentItem = 0
                }
                "weapons" -> view_pager.currentItem = 1
            }
        }
        return true
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(QUERY_KEY, searchView?.query.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState?.let { savedQuery = it.getString(QUERY_KEY) }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun queryText(): Observable<String> = querySubject.startWith(savedQuery)


    override fun showFiltering(filter: String) {
        searchView?.queryHint = "Search$filter..."
        if (filter.contains("all"))
            snackBar.dismiss()
        else {
            snackBar.setText("Filtering$filter")
            snackBar.show()
        }
    }

    override fun showFilterOptions() {
        (pagerAdapter.fragments[view_pager.currentItem] as Search5eView).showFilterOptions()
    }
}
