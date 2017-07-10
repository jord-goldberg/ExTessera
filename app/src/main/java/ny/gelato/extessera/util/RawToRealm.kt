package ny.gelato.extessera.util

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ny.gelato.extessera.data.model.JsonSpell
import ny.gelato.extessera.data.model.Spell
import ny.gelato.extessera.data.model.Weapon

/**
 * Created by jord.goldberg on 4/26/17.
 */

fun rawToRealmSpells(json: String): List<Spell> {
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
        add(Weapon(Weapon.Type.CLUB.formatted, true, false, "1d4", Weapon.DamageType.BLUDGEONING, "Light"))
        add(Weapon(Weapon.Type.DAGGER.formatted, true, false, "1d4", Weapon.DamageType.PIERCING, "Finesse, light, thrown (range 20/60)"))
        add(Weapon(Weapon.Type.GREATCLUB.formatted, true, false, "1d8", Weapon.DamageType.BLUDGEONING, "Two-handed"))
        add(Weapon(Weapon.Type.HANDAXE.formatted, true, false, "1d6", Weapon.DamageType.SLASHING, "Light, thrown (range 20/60)"))
        add(Weapon(Weapon.Type.JAVELIN.formatted, true, false, "1d6", Weapon.DamageType.PIERCING, "Thrown (range 30/120)"))
        add(Weapon(Weapon.Type.LIGHT_HAMMER.formatted, true, false, "1d4", Weapon.DamageType.BLUDGEONING, "Light, thrown (range 20/60)"))
        add(Weapon(Weapon.Type.MACE.formatted, true, false, "1d6", Weapon.DamageType.BLUDGEONING, "--"))
        add(Weapon(Weapon.Type.QUARTERSTAFF.formatted, true, false, "1d6", Weapon.DamageType.BLUDGEONING, "Versatile (1d8)"))
        add(Weapon(Weapon.Type.SICKLE.formatted, true, false, "1d4", Weapon.DamageType.SLASHING, "Light"))
        add(Weapon(Weapon.Type.SPEAR.formatted, true, false, "1d6", Weapon.DamageType.PIERCING, "Thrown (range 20/60), versatile (1d8)"))


        add(Weapon(Weapon.Type.CROSSBOW_LIGHT.formatted, true, true, "1d8", Weapon.DamageType.PIERCING, "Ammunition (range 80/320), loading, two-handed"))
        add(Weapon(Weapon.Type.DART.formatted, true, true, "1d4", Weapon.DamageType.PIERCING, "Finesse, thrown (range 20/60)"))
        add(Weapon(Weapon.Type.SHORTBOW.formatted, true, true, "1d6", Weapon.DamageType.PIERCING, "Ammunition (range 80/320), two-handed"))
        add(Weapon(Weapon.Type.SLING.formatted, true, true, "1d4", Weapon.DamageType.BLUDGEONING, "Ammunition (range 30/120)"))

        add(Weapon(Weapon.Type.BATTLEAXE.formatted, false, false, "1d8", Weapon.DamageType.SLASHING, "Versatile (1d10)"))
        add(Weapon(Weapon.Type.FLAIL.formatted, false, false, "1d8", Weapon.DamageType.BLUDGEONING, "--"))
        add(Weapon(Weapon.Type.GLAIVE.formatted, false, false, "1d10", Weapon.DamageType.SLASHING, "Heavy, reach, two-handed"))
        add(Weapon(Weapon.Type.GREATAXE.formatted, false, false, "1d12", Weapon.DamageType.SLASHING, "Heavy, two-handed"))
        add(Weapon(Weapon.Type.GREATSWORD.formatted, false, false, "2d6", Weapon.DamageType.SLASHING, "Heavy, two-handed"))
        add(Weapon(Weapon.Type.HALBERD.formatted, false, false, "1d10", Weapon.DamageType.SLASHING, "Heavy, reach, two-handed"))
        add(Weapon(Weapon.Type.LANCE.formatted, false, false, "1d12", Weapon.DamageType.PIERCING, "Reach, special"))
        add(Weapon(Weapon.Type.LONGSWORD.formatted, false, false, "1d8", Weapon.DamageType.SLASHING, "Versatile (1d10)"))
        add(Weapon(Weapon.Type.MAUL.formatted, false, false, "2d6", Weapon.DamageType.BLUDGEONING, "Heavy, two-handed"))
        add(Weapon(Weapon.Type.MORNINGSTAR.formatted, false, false, "1d8", Weapon.DamageType.PIERCING, "--"))
        add(Weapon(Weapon.Type.PIKE.formatted, false, false, "1d10", Weapon.DamageType.PIERCING, "Heavy, reach, two-handed"))
        add(Weapon(Weapon.Type.RAPIER.formatted, false, false, "1d8", Weapon.DamageType.PIERCING, "Finesse"))
        add(Weapon(Weapon.Type.SCIMITAR.formatted, false, false, "1d6", Weapon.DamageType.SLASHING, "Finesse, light"))
        add(Weapon(Weapon.Type.SHORTSWORD.formatted, false, false, "1d6", Weapon.DamageType.PIERCING, "Finesse, light"))
        add(Weapon(Weapon.Type.TRIDENT.formatted, false, false, "1d6", Weapon.DamageType.PIERCING, "Thrown (range 20/60), versatile (1d8)"))
        add(Weapon(Weapon.Type.WAR_PICK.formatted, false, false, "1d8", Weapon.DamageType.PIERCING, "--"))
        add(Weapon(Weapon.Type.WARHAMMER.formatted, false, false, "1d8", Weapon.DamageType.BLUDGEONING, "Versatile (1d10)"))
        add(Weapon(Weapon.Type.WHIP.formatted, false, false, "1d4", Weapon.DamageType.SLASHING, "Finesse, reach"))

        add(Weapon(Weapon.Type.BLOWGUN.formatted, false, true, "1", Weapon.DamageType.PIERCING, "Ammunition (range 25/100), loading"))
        add(Weapon(Weapon.Type.CROSSBOW_HAND.formatted, false, true, "1d6", Weapon.DamageType.PIERCING, "Ammunition (range 30/120), light, loading"))
        add(Weapon(Weapon.Type.CROSSBOW_HEAVY.formatted, false, true, "1d10", Weapon.DamageType.PIERCING, "Ammunition (range 100/400), heavy, loading, two-handed"))
        add(Weapon(Weapon.Type.LONGBOW.formatted, false, true, "1d8", Weapon.DamageType.PIERCING, "Ammunition (range 150/600), heavy, two-handed"))
        add(Weapon(Weapon.Type.NET.formatted, false, true, "--", "--", "Special, thrown (range 5/15)"))
    }
}
