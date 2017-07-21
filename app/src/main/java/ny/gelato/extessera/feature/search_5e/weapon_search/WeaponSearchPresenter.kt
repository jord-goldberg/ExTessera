package ny.gelato.extessera.feature.search_5e.weapon_search

import android.view.View
import io.realm.Case
import io.realm.RealmResults
import io.realm.Sort
import ny.gelato.extessera.App
import ny.gelato.extessera.base.BasePresenter
import ny.gelato.extessera.data.model.Weapon
import ny.gelato.extessera.feature.search_5e.Search5ePresenter
import rx.Observable
import rx.subjects.PublishSubject
import rx.subscriptions.CompositeSubscription

/**
 * Created by jord.goldberg on 6/14/17.
 */

class WeaponSearchPresenter : Search5ePresenter<WeaponSearchView>() {

    val subscriptions: CompositeSubscription = CompositeSubscription()

    val filters: WeaponSearchFilter = WeaponSearchFilter()

    val publishFilters: PublishSubject<WeaponSearchFilter> = PublishSubject.create<WeaponSearchFilter>()

    val headers = Observable.Transformer<RealmResults<Weapon>, List<Any>> {
        it.filter { it.isLoaded }
                .map {
                    val weapons = mutableListOf<Any>()
                    if (it.isNotEmpty()) {
                        var prevCategory = it.first().category()
                        weapons.add(prevCategory)
                        for (weapon in it) {
                            if (weapon.category() != prevCategory) {
                                weapons.add(0)
                                weapons.add(weapon.category())
                            }
                            weapons.add(weapon)
                            prevCategory = weapon.category()
                        }
                        weapons.add(0)
                    }
                    weapons
                }
    }

    override fun attachView(baseView: WeaponSearchView) {
        super.attachView(baseView)
        val subscription = Observable.combineLatest(
                view.queryText(),
                publishFilters.startWith(filters)) { queryText, filters ->

            var query = App.component.realm()
                    .where(Weapon::class.java)
                    .contains("name", queryText, Case.INSENSITIVE)

            if (filters.simple) query = query.equalTo("isSimple", true)
            else if (filters.martial) query = query.equalTo("isSimple", false)

            if (filters.melee) query = query.equalTo("isRanged", false)
            else if (filters.ranged) query = query.equalTo("isRanged", true)

            if (filters.finesse) query = query.contains("properties", "finesse", Case.INSENSITIVE)
            if (filters.thrown) query = query.contains("properties", "thrown", Case.INSENSITIVE)

            query.findAllAsync()
                    .sort("isSimple", Sort.DESCENDING, "isRanged", Sort.ASCENDING)
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

    fun clearFilters() {
        filters.clear()
        publishFilters.onNext(filters)
    }

    fun restoreFilters(filter: WeaponSearchFilter) {
        filters.restore(filter)
        publishFilters.onNext(filters)
    }

    fun toggleSimple(isChecked: Boolean) {
        filters.toggleSimple(isChecked)
        publishFilters.onNext(filters)
    }

    fun toggleMartial(isChecked: Boolean) {
        filters.toggleMartial(isChecked)
        publishFilters.onNext(filters)
    }

    fun toggleMelee(isChecked: Boolean) {
        filters.toggleMelee(isChecked)
        publishFilters.onNext(filters)
    }

    fun toggleRanged(isChecked: Boolean) {
        filters.toggleRanged(isChecked)
        publishFilters.onNext(filters)
    }

    fun toggleFinesse() {
        filters.toggleFinesse()
        publishFilters.onNext(filters)
    }

    fun toggleThrown() {
        filters.toggleThrown()
        publishFilters.onNext(filters)
    }
}