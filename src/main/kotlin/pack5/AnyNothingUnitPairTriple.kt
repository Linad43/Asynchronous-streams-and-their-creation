package pack5

import java.lang.Math.pow

val PAIR = arrayListOf(
    Pair("(", ")"),
    Pair("{", "}"),
    Pair("[", "]")
)

enum class TURN { OPEN, CLOSE }

fun main() {
    ex1()
    ex2()
}

fun ex1() {
    val text = arrayListOf(
        "{([])}",
        "{([))}",
        "{{[])}"
    )
    text.forEach {
        println("$it -> ${check(it)}")
    }
}

fun check(input: String, stack: String): Boolean {
    if (input.isEmpty()) {
        return stack.isEmpty()
    } else if (findTurn(input.first()) == TURN.OPEN) {
        return check(input.substring(1), input[0] + stack)
    } else if (findTurn(input.first()) == TURN.CLOSE) {
        return (stack.isNotEmpty())
                && (findIndex(input.first()) == findIndex(stack.first()))
                && (check(input.substring(1), stack.substring(1)))
    } else return check(input.substring(1), stack)
}

fun check(brackes: String): Boolean {
    return check(brackes, "")
}

fun findIndex(brack: Char): Int {
    for (i in PAIR.indices) {
        if (PAIR[i].first == brack.toString()
            || PAIR[i].second == brack.toString()
        ) {
            return i
        }
    }
    return -1
}

fun findTurn(brack: Char): TURN {
    for (pair in PAIR) {
        if (pair.first == brack.toString()) {
            return TURN.OPEN
        }
        if (pair.second == brack.toString()) {
            return TURN.CLOSE
        }
    }
    return TURN.OPEN
}

fun ex2() {
    val array = arrayListOf(1, 2, 3)
    permutations(array)
}

fun factorial(size: Int): Int {
    if (size == 1) {
        return 1
    }
    return size * factorial(size - 1)
}

fun permutations(array: ArrayList<Int>) {
    val size = arrayListOf<Int>()
    val buf = arrayListOf<Int>()
    var bufJ: Int
    var j = 0
    var i = 0
    while (j < factorial(array.size)) {
        size.clear()
        bufJ = i
        for (i in array.indices) {
            size.add(bufJ % array.size)
            bufJ /= array.size
        }
        buf.clear()
        for (i in array.indices) {
            buf.add(array[size.reversed()[i]])
        }
        if (buf.toSet().size == buf.size) {
            println(buf)
            j++
        }
        i++
    }
}