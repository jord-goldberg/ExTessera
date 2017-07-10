    package ny.gelato.extessera

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration


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
    }

    fun initRealm() {
        Realm.init(this)

        val config = RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()

        Realm.setDefaultConfiguration(config)
    }
}
