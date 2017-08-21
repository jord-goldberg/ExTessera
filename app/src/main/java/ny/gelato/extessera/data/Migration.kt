package ny.gelato.extessera.data

import io.realm.DynamicRealm
import io.realm.FieldAttribute
import io.realm.RealmMigration
import ny.gelato.extessera.data.model.character.Ability
import ny.gelato.extessera.data.model.character.Job

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
                    // These next 2 fields are renamed to more accurately reflect the information stored
                    .renameField("initiative", "initiativeModifier")
                    .renameField("speed", "speedModifier")
                    .transform { it.set("speedModifier", 0) }
                    .removeField("traits")
                    // maxHp() is now a function = baseHp + (conModifier * level)
                    .addField("baseHp", Int::class.java)
                    .transform {
                        val maxHp = it.getInt("maxHp")
                        val conModifier = it.get<Ability>("constitution").modifier()
                        val level = it.get<Job>("primary").level
                        it.set("baseHp", maxHp - (conModifier * level))
                    }
                    .removeField("maxHp")

            // Create a Counter object to keep track of limited use Class features; to be added to Job Schema
            val counterSchema = schema.create("Counter")
                    .addField("name", String::class.java, FieldAttribute.INDEXED)
                    .addField("description", String::class.java)
                    .addField("current", Int::class.java)
                    .addField("max", Int::class.java)
                    .addField("resetOnShortRest", Boolean::class.java, FieldAttribute.INDEXED)

            schema.get("Job")
                    .addIndex("job")
                    .renameField("job", "jobName")
                    .addField("archetypeName", String::class.java, FieldAttribute.INDEXED)
                    .addField("subtypeName", String::class.java, FieldAttribute.INDEXED)
                    .removeField("features")
                    .addRealmListField("counters", counterSchema)

            schema.get("Ability")
                    .addField("scoreModifier", Int::class.java)
                    .addField("saveModifier", Int::class.java)

            schema.get("Skill")
                    .addIndex("type")
                    .renameField("type", "typeName")
                    .renameField("proficiency", "proficiencyName")

            schema.get("Proficiency")
                    .renameField("type", "typeName")
                    .removeField("origin")

            schema.get("Preferences")
                    .addField("editAllSkills", Boolean::class.java)

            schema.remove("Trait")
            schema.remove("Feature")

            version++
        }

        // > apk v7(1.2-beta)
        if (version == 1L) {

        }
    }

}