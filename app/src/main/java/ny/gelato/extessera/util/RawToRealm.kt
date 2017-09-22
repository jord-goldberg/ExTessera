package ny.gelato.extessera.util

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ny.gelato.extessera.data.model.JsonSpell
import ny.gelato.extessera.data.model.Spell
import ny.gelato.extessera.data.model.Weapon
import java.io.IOException

/**
 * Created by jord.goldberg on 4/26/17.
 */

fun rawToRealmSpells(context: Context, resource: Int): List<Spell> {

    var json: String = ""
    try {
        val input = context.resources.openRawResource(resource)
        val buffer = ByteArray(input.available())
        input.read(buffer)
        input.close()
        json = String(buffer)
    } catch (e: IOException) {
        e.printStackTrace()
    }

    val spellsType = object : TypeToken<List<JsonSpell>>() {}.type
    val response = Gson().fromJson<List<JsonSpell>>(json, spellsType)

    return response.mapTo(ArrayList<Spell>()) {
        Spell(
                name = it.name,
                level = if (it.level.contains(Regex("^[0-9]+$"))) it.level.toInt() else 0,
                classes = it.classes.map { it.capitalize() }.toString().substring(1, it.classes.toString().lastIndex),
                castingTime = it.casting_time,
                components = it.components.raw,
                description = it.description,
                duration = it.duration,
                range = it.range,
                school = it.school,
                type = it.type,
                higherLevels = it.higher_levels,
                isRitual = it.ritual
        )
    }
}

fun realmWeapons(): List<Weapon> {
    return mutableListOf<Weapon>().apply {
        add(Weapon(Weapon.Type.CLUB.formatted, true, false, "1d4", Weapon.DamageType.BLUDGEONING, "Light", Weapon.Type.CLUB.name))
        add(Weapon(Weapon.Type.DAGGER.formatted, true, false, "1d4", Weapon.DamageType.PIERCING, "Finesse, light, thrown (range 20/60)", Weapon.Type.DAGGER.name))
        add(Weapon(Weapon.Type.GREATCLUB.formatted, true, false, "1d8", Weapon.DamageType.BLUDGEONING, "Two-handed", Weapon.Type.GREATCLUB.name))
        add(Weapon(Weapon.Type.HANDAXE.formatted, true, false, "1d6", Weapon.DamageType.SLASHING, "Light, thrown (range 20/60)", Weapon.Type.HANDAXE.name))
        add(Weapon(Weapon.Type.JAVELIN.formatted, true, false, "1d6", Weapon.DamageType.PIERCING, "Thrown (range 30/120)", Weapon.Type.JAVELIN.name))
        add(Weapon(Weapon.Type.LIGHT_HAMMER.formatted, true, false, "1d4", Weapon.DamageType.BLUDGEONING, "Light, thrown (range 20/60)", Weapon.Type.LIGHT_HAMMER.name))
        add(Weapon(Weapon.Type.MACE.formatted, true, false, "1d6", Weapon.DamageType.BLUDGEONING, "--", Weapon.Type.MACE.name))
        add(Weapon(Weapon.Type.QUARTERSTAFF.formatted, true, false, "1d6", Weapon.DamageType.BLUDGEONING, "Versatile (1d8)", Weapon.Type.QUARTERSTAFF.name))
        add(Weapon(Weapon.Type.SICKLE.formatted, true, false, "1d4", Weapon.DamageType.SLASHING, "Light", Weapon.Type.SICKLE.name))
        add(Weapon(Weapon.Type.SPEAR.formatted, true, false, "1d6", Weapon.DamageType.PIERCING, "Thrown (range 20/60), versatile (1d8)", Weapon.Type.SPEAR.name))
        add(Weapon(Weapon.Type.CROSSBOW_LIGHT.formatted, true, true, "1d8", Weapon.DamageType.PIERCING, "Ammunition (range 80/320), loading, two-handed", Weapon.Type.CROSSBOW_LIGHT.name))
        add(Weapon(Weapon.Type.DART.formatted, true, true, "1d4", Weapon.DamageType.PIERCING, "Finesse, thrown (range 20/60)", Weapon.Type.DART.name))
        add(Weapon(Weapon.Type.SHORTBOW.formatted, true, true, "1d6", Weapon.DamageType.PIERCING, "Ammunition (range 80/320), two-handed", Weapon.Type.SHORTBOW.name))
        add(Weapon(Weapon.Type.SLING.formatted, true, true, "1d4", Weapon.DamageType.BLUDGEONING, "Ammunition (range 30/120)", Weapon.Type.SLING.name))
        add(Weapon(Weapon.Type.BATTLEAXE.formatted, false, false, "1d8", Weapon.DamageType.SLASHING, "Versatile (1d10)", Weapon.Type.BATTLEAXE.name))
        add(Weapon(Weapon.Type.FLAIL.formatted, false, false, "1d8", Weapon.DamageType.BLUDGEONING, "--", Weapon.Type.FLAIL.name))
        add(Weapon(Weapon.Type.GLAIVE.formatted, false, false, "1d10", Weapon.DamageType.SLASHING, "Heavy, reach, two-handed", Weapon.Type.GLAIVE.name))
        add(Weapon(Weapon.Type.GREATAXE.formatted, false, false, "1d12", Weapon.DamageType.SLASHING, "Heavy, two-handed", Weapon.Type.GREATAXE.name))
        add(Weapon(Weapon.Type.GREATSWORD.formatted, false, false, "2d6", Weapon.DamageType.SLASHING, "Heavy, two-handed", Weapon.Type.GREATSWORD.name))
        add(Weapon(Weapon.Type.HALBERD.formatted, false, false, "1d10", Weapon.DamageType.SLASHING, "Heavy, reach, two-handed", Weapon.Type.HALBERD.name))
        add(Weapon(Weapon.Type.LANCE.formatted, false, false, "1d12", Weapon.DamageType.PIERCING, "Reach, special", Weapon.Type.LANCE.name))
        add(Weapon(Weapon.Type.LONGSWORD.formatted, false, false, "1d8", Weapon.DamageType.SLASHING, "Versatile (1d10)", Weapon.Type.LONGSWORD.name))
        add(Weapon(Weapon.Type.MAUL.formatted, false, false, "2d6", Weapon.DamageType.BLUDGEONING, "Heavy, two-handed", Weapon.Type.MAUL.name))
        add(Weapon(Weapon.Type.MORNINGSTAR.formatted, false, false, "1d8", Weapon.DamageType.PIERCING, "--", Weapon.Type.MORNINGSTAR.name))
        add(Weapon(Weapon.Type.PIKE.formatted, false, false, "1d10", Weapon.DamageType.PIERCING, "Heavy, reach, two-handed", Weapon.Type.PIKE.name))
        add(Weapon(Weapon.Type.RAPIER.formatted, false, false, "1d8", Weapon.DamageType.PIERCING, "Finesse", Weapon.Type.RAPIER.name))
        add(Weapon(Weapon.Type.SCIMITAR.formatted, false, false, "1d6", Weapon.DamageType.SLASHING, "Finesse, light", Weapon.Type.SCIMITAR.name))
        add(Weapon(Weapon.Type.SHORTSWORD.formatted, false, false, "1d6", Weapon.DamageType.PIERCING, "Finesse, light", Weapon.Type.SHORTSWORD.name))
        add(Weapon(Weapon.Type.TRIDENT.formatted, false, false, "1d6", Weapon.DamageType.PIERCING, "Thrown (range 20/60), versatile (1d8)", Weapon.Type.TRIDENT.name))
        add(Weapon(Weapon.Type.WAR_PICK.formatted, false, false, "1d8", Weapon.DamageType.PIERCING, "--", Weapon.Type.WAR_PICK.name))
        add(Weapon(Weapon.Type.WARHAMMER.formatted, false, false, "1d8", Weapon.DamageType.BLUDGEONING, "Versatile (1d10)", Weapon.Type.WARHAMMER.name))
        add(Weapon(Weapon.Type.WHIP.formatted, false, false, "1d4", Weapon.DamageType.SLASHING, "Finesse, reach", Weapon.Type.WHIP.name))
        add(Weapon(Weapon.Type.BLOWGUN.formatted, false, true, "1", Weapon.DamageType.PIERCING, "Ammunition (range 25/100), loading", Weapon.Type.BLOWGUN.name))
        add(Weapon(Weapon.Type.CROSSBOW_HAND.formatted, false, true, "1d6", Weapon.DamageType.PIERCING, "Ammunition (range 30/120), light, loading", Weapon.Type.CROSSBOW_HAND.name))
        add(Weapon(Weapon.Type.CROSSBOW_HEAVY.formatted, false, true, "1d10", Weapon.DamageType.PIERCING, "Ammunition (range 100/400), heavy, loading, two-handed", Weapon.Type.CROSSBOW_HEAVY.name))
        add(Weapon(Weapon.Type.LONGBOW.formatted, false, true, "1d8", Weapon.DamageType.PIERCING, "Ammunition (range 150/600), heavy, two-handed", Weapon.Type.LONGBOW.name))
        add(Weapon(Weapon.Type.NET.formatted, false, true, "--", "--", "Special, thrown (range 5/15)", Weapon.Type.NET.name))
    }
}
