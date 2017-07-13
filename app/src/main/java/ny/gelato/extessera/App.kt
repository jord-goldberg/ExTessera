package ny.gelato.extessera

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration
import ny.gelato.extessera.util.rawToRealmSpells
import ny.gelato.extessera.util.realmWeapons


/**
 * Created by jord.goldberg on 4/30/17.
 */

class App : Application() {

    companion object {
        @JvmStatic lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initRealm()

        component = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()

        component.inject(this)

        if (component.realm().isEmpty) populateRealm()
    }

    fun initRealm() {
        Realm.init(this)

        val config = RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()

        Realm.setDefaultConfiguration(config)
    }

    // To be removed when a pre-populated Realm is packaged with the apk
    private fun populateRealm() {
        val spells = rawToRealmSpells(this, R.raw.spells)
        val weapons = realmWeapons()
        component.realm().executeTransactionAsync { realm ->
            for (spell in spells) realm.copyToRealmOrUpdate(spell)
            for (weapon in weapons) realm.copyToRealmOrUpdate(weapon)
        }
    }
}
