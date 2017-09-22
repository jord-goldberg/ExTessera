package ny.gelato.extessera.data.model.character

import io.realm.RealmObject

/**
 * Created by jord.goldberg on 5/23/17.
 */

open class SpellSlots(
        var first: Int = 0,
        var second: Int = 0,
        var third: Int = 0,
        var fourth: Int = 0,
        var fifth: Int = 0,
        var sixth: Int = 0,
        var seventh: Int = 0,
        var eighth: Int = 0,
        var ninth: Int = 0

) : RealmObject() {

    fun isEmpty(): Boolean = (first + second + third + fourth + fifth + sixth + seventh + eighth + ninth) == 0

    fun longRest(character: Character) {
        resetSlots()

        val primaryCasterLevel = character.primary.casterLevel()

        if (character.primary.job == Job.Type.WARLOCK) setWarlockSlots(primaryCasterLevel)
        else if (primaryCasterLevel != 0) setCasterSlots(primaryCasterLevel)
    }

    private fun resetSlots() {
        first = 0
        second = 0
        third = 0
        fourth = 0
        fifth = 0
        sixth = 0
        seventh = 0
        eighth = 0
        ninth = 0
    }

    private fun setWarlockSlots(casterLevel: Int) {
        when (casterLevel) {
            1 -> first += 1
            2 -> first += 2
            3, 4 -> second += 2
            5, 6 -> third += 2
            7, 8 -> fourth += 2
            9, 10 -> fifth += 2
            in 11..16 -> {
                fifth += 3
                sixth += 1
                if (casterLevel > 12) seventh += 1
                if (casterLevel > 14) eighth += 1
            }
            in 17..20 -> {
                fifth += 4
                sixth += 1
                seventh += 1
                eighth += 1
                ninth += 1
            }
        }
    }

    private fun setCasterSlots(casterLevel: Int) {
        firstSlots(casterLevel)
        secondSlots(casterLevel)
        thirdSlots(casterLevel)
        fourthSlots(casterLevel)
        fifthSlots(casterLevel)
        sixthSlots(casterLevel)
        seventhSlots(casterLevel)
        eighthSlots(casterLevel)
        ninthSlots(casterLevel)
    }

    private fun firstSlots(casterLevel: Int) {
        first += when (casterLevel) {
            1 -> 2
            2 -> 3
            in 3..20 -> 4
            else -> 0
        }
    }

    private fun secondSlots(casterLevel: Int) {
        second += when (casterLevel) {
            3 -> 2
            in 4..20 -> 4
            else -> 0
        }
    }

    private fun thirdSlots(casterLevel: Int) {
        third += when (casterLevel) {
            5 -> 2
            in 6..20 -> 3
            else -> 0
        }
    }

    private fun fourthSlots(casterLevel: Int) {
        fourth += when (casterLevel) {
            7 -> 1
            8 -> 2
            in 9..20 -> 3
            else -> 0
        }
    }

    private fun fifthSlots(casterLevel: Int) {
        fifth += when (casterLevel) {
            9 -> 1
            in 10..17 -> 2
            in 18..20 -> 3
            else -> 0
        }
    }

    private fun sixthSlots(casterLevel: Int) {
        sixth += when (casterLevel) {
            in 11..18 -> 1
            in 19..20 -> 2
            else -> 0
        }
    }

    private fun seventhSlots(casterLevel: Int) {
        seventh += when (casterLevel) {
            in 13..19 -> 1
            20 -> 2
            else -> 0
        }
    }

    private fun eighthSlots(casterLevel: Int) {
        eighth += when (casterLevel) {
            in 15..20 -> 1
            else -> 0
        }
    }

    private fun ninthSlots(casterLevel: Int) {
        ninth += when (casterLevel) {
            in 17..20 -> 1
            else -> 0
        }
    }
}