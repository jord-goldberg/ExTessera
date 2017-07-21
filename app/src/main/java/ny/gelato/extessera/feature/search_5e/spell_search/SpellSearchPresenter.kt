package ny.gelato.extessera.feature.search_5e.spell_search

import android.view.View
import io.realm.*
import ny.gelato.extessera.R
import ny.gelato.extessera.App
import ny.gelato.extessera.base.BasePresenter
import ny.gelato.extessera.data.model.Spell
import ny.gelato.extessera.feature.search_5e.Search5ePresenter
import rx.Observable
import rx.subjects.PublishSubject
import rx.subscriptions.CompositeSubscription

/**
 * Created by jord.goldberg on 5/15/17.
 */

class SpellSearchPresenter() : Search5ePresenter<SpellSearchView>() {

    constructor(job: String, level: Int) : this() {
        filters.jobs.add(job)
        filters.level = level
    }

    val subscriptions: CompositeSubscription = CompositeSubscription()

    val filters: SpellSearchFilter = SpellSearchFilter()

    val publishFilters: PublishSubject<SpellSearchFilter> = PublishSubject.create<SpellSearchFilter>()

    val headers = Observable.Transformer<RealmResults<Spell>, List<Any>> {
        it.filter { it.isLoaded }
                .map {
                    val spells = mutableListOf<Any>()
                    if (it.isNotEmpty() && filters.sortByLevel) {
                        var prevLevel = it.first().level
                        spells.add(if (prevLevel == 0) "Cantrip" else it.first().type.substringBefore(" "))
                        for (spell in it) {
                            if (spell.level != prevLevel) {
                                spells.add(0)
                                spells.add(spell.type.substringBefore(" "))
                            }
                            spells.add(spell)
                            prevLevel = spell.level
                        }
                        spells.add(0)
                    }
                    else if (it.isNotEmpty() && !filters.sortByLevel) {
                        var prevLetter = it.first().name.first().toString()
                        spells.add(prevLetter)
                        for (spell in it) {
                            if (spell.name.first().toString() != prevLetter) {
                                spells.add(0)
                                spells.add(spell.name.first().toString())
                            }
                            spells.add(spell)
                            prevLetter = spell.name.first().toString()
                        }
                        spells.add(0)
                    }
                    spells
                }
    }

    override fun attachView(baseView: SpellSearchView) {
        super.attachView(baseView)
        val subscription = Observable.combineLatest(
                view.queryText(),
                publishFilters.startWith(filters)) { queryText, filters ->

            var query = App.component.realm()
                    .where(Spell::class.java)
                    .contains("name", queryText, Case.INSENSITIVE)
                    .lessThanOrEqualTo("level", filters.level)

            if (filters.jobs.isEmpty().not()) {
                query.beginGroup()
                for (job in filters.jobs.dropLast(1)) query = query.contains("classes", job, Case.INSENSITIVE).or()
                query = query.contains("classes", filters.jobs[filters.jobs.lastIndex], Case.INSENSITIVE)
                query.endGroup()
            }
            if (filters.schools.isEmpty().not()) {
                query.beginGroup()
                for (school in filters.schools.dropLast(1)) query = query.equalTo("school", school, Case.INSENSITIVE).or()
                query = query.equalTo("school", filters.schools[filters.schools.lastIndex], Case.INSENSITIVE)
                query.endGroup()
            }
            if (filters.ritual) query = query.equalTo("isRitual", true)

            query.findAllAsync()
        }
                .flatMap {
                    if (filters.sortByLevel) it.sort("level", Sort.ASCENDING, "name", Sort.ASCENDING).asObservable()
                    else it.sort("name", Sort.ASCENDING, "level", Sort.ASCENDING).asObservable()
                }
                .filter { it.isLoaded }
                .compose(headers)
                .subscribe(
                        {
                            view.showResult(it)
                            view.showFiltering(filters.filtering())
                            if (it.isEmpty()) view.showEmpty()
                        },
                        { t -> t.printStackTrace() })

        subscriptions.add(subscription)
    }

    override fun detachView() {
        super.detachView()
        subscriptions.clear()
    }

    fun toggleJobFilter(job: String) {
        filters.toggleJob(job)
        publishFilters.onNext(filters)
    }

    fun toggleSchoolFilter(school: String) {
        filters.toggleSchool(school)
        publishFilters.onNext(filters)
    }

    fun toggleRitualFilter() {
        filters.toggleRitual()
        publishFilters.onNext(filters)
    }

    fun updateLevelFilter(level: Int) {
        filters.updateLevel(level)
        publishFilters.onNext(filters)
    }

    fun toggleSortSelection() {
        filters.toggleSort()
        publishFilters.onNext(filters)
    }

    fun clearFilters() {
        filters.clear()
        publishFilters.onNext(filters)
    }

    fun restoreFilters(filter: SpellSearchFilter) {
        filters.restore(filter)
        publishFilters.onNext(filters)
    }
}