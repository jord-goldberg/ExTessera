package ny.gelato.extessera

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

import dagger.Module
import dagger.Provides
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Singleton


/**
 * Created by jord.goldberg on 4/30/17.
 */

@Module
class AppModule(private val app: App) {

    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    fun provideRealm(): Realm = Realm.getDefaultInstance()

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context):
            SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
}
