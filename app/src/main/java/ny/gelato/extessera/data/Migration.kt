package ny.gelato.extessera.data

import io.realm.DynamicRealm
import io.realm.RealmMigration

/**
 * Created by jord.goldberg on 8/2/17.
 */

class Migration : RealmMigration {

    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        val schema = realm.schema
        var version = oldVersion

        if (version == 0L) {
            schema.get("Ability")
                    .addField("miscModifier", Int::class.java)
                    .addField("saveModifier", Int::class.java)
            schema.get("Skill")
                    .addIndex("type")
            version++
        }

        // > apk v7(1.2-beta)
        if (version == 1L) {

        }
    }

}