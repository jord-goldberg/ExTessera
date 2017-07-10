package ny.gelato.extessera;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Component;
import io.realm.Realm;


/**
 * Created by jord.goldberg on 4/30/17.
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(App app);

    Realm realm();
}
