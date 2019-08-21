package xyz.prpht.distributeamountbetweenslots

import kotlin.math.floor
import kotlin.random.Random

fun main() {
    repeat(0x10) {
        println(distribute(100, 5, 1, Random).joinToString("  ") { "%2d".format(it) })
    }
}

fun distribute(amount: Int, slots: Int, rollsNum: Int, random: Random): List<Int> {
    var leftAmount = amount
    return (slots downTo 1).map { leftSlots ->
        val slotAmount = random.generateSlotAmount(leftAmount, leftSlots, rollsNum)
        leftAmount -= slotAmount
        slotAmount
    }
}

private fun Random.generateSlotAmount(leftAmount: Int, leftSlots: Int, rollsNum: Int): Int {
    check(leftSlots > 0) { "Non-positive slots count: $leftSlots" }
    check(leftAmount > 0) { "Non-positive amount: $leftAmount" }
    check(leftAmount >= leftSlots) { "leftAmount ($leftAmount) is less than leftSlots ($leftSlots)" }

    if (leftSlots <= 1)
        return leftAmount

    if (leftAmount == leftSlots)
        return 1

    /**
     *  Magic formula ensures:
     *  1. Values distributed in `[1..(leftAmount-lestSlots+1)]` to guarantee that next `leftAmount >= next slotsAmount`.
     *  2. Estimation is `leftAmount/leftSlots`.
     *  3. Values are centered: those near `leftAmount/leftSlots` are more probable than values at the boundaries.
     *     Bigger `rollsNum` leads to more centered result.
     */
    val rollsSum = (0 until rollsNum).map { nextInt(1, leftAmount - leftSlots + 2) }.sum()
    return stochasticRound(2.0 * rollsSum / (rollsNum * leftSlots) + (leftSlots - 2.0) / leftSlots)
}

fun Random.stochasticRound(x: Double): Int {
    val floor = floor(x)
    var result = floor
    if (nextFloat() < (x - floor))
        result += 1
    return result.toInt()
}
