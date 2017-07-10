package ny.gelato.extessera.data.model

import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey

/**
 * Created by jord.goldberg on 6/19/17.
 */

open class Armor(
        @PrimaryKey var name: String = "",
        @Index var category: String = Armor.Category.LIGHT.formatted,
        var armor: Int = 10,
        @Index var strRequired: Int = 0,
        @Index var stealthDisadvantage: Boolean = false,
        var type: String = name

) : RealmObject() {

    enum class Category(val formatted: String) {
        LIGHT("Light Armor"),
        MEDIUM("Medium Armor"),
        HEAVY("Heavy Armor"),
        SHIELD("Shields")
    }

    enum class Type(val formatted: String) {
        PADDED("Padded") {
            override fun category(): Category = Category.LIGHT
        },
        LEATHER("Leather") {
            override fun category(): Category = Category.LIGHT
        },
        STUDDED_LEATHER("Studded leather") {
            override fun category(): Category = Category.LIGHT
        },
        HIDE("Hide") {
            override fun category(): Category = Category.MEDIUM
        },
        CHAIN_SHIRT("Chain shirt") {
            override fun category(): Category = Category.MEDIUM
        },
        SCALE_MAIL("Scale mail") {
            override fun category(): Category = Category.MEDIUM
        },
        BREASTPLATE("Breastplate") {
            override fun category(): Category = Category.MEDIUM
        },
        HALF_PLATE("Half plate") {
            override fun category(): Category = Category.MEDIUM
        },
        RING_MAIL("Ring mail") {
            override fun category(): Category = Category.HEAVY
        },
        CHAIN_MAIL("Chain mail") {
            override fun category(): Category = Category.HEAVY
        },
        SPLINT("Splint") {
            override fun category(): Category = Category.HEAVY
        },
        PLATE("Plate") {
            override fun category(): Category = Category.HEAVY
        },
        SHIELD("Shield") {
            override fun category(): Category = Category.SHIELD
        };

        abstract fun category(): Category
    }
}