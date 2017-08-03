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
            schema.get("Character")
                    // These fields represent the .name of an enum class; they're backing fields and
                    // should be renamed to represent what they really are
                    .renameField("race", "raceName")
                    .renameField("subrace", "subraceName")
                    .renameField("alignment", "alignmentName")

            schema.get("Job")
                    .addIndex("job")
                    .renameField("job", "jobName")

            schema.get("Ability")
                    .addField("scoreModifier", Int::class.java)
                    .addField("saveModifier", Int::class.java)

            schema.get("Skill")
                    .addIndex("type")
                    .renameField("type", "typeName")
                    .renameField("proficiency", "proficiencyName")

            version++
        }

        // > apk v7(1.2-beta)
        if (version == 1L) {

        }
    }

}