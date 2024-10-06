package org.example

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


fun listIntNum(length: Int, min: Int, max: Int): ArrayList<Int> {
    val result = arrayListOf<Int>()
    for (i in 1..length) {
        result.add((min..max).random())
    }
    return result
}

fun listIntNum(length: Int): ArrayList<Int> {
    return listIntNum(length, 0, 10)
}

fun listIntNum(): ArrayList<Int> {
    return listIntNum(10)
}

suspend fun main() {

    println(ex1())


}

private suspend fun ex1(): String {
    return flow {
        val number = listIntNum()
        println(number)
        number.forEach {
            delay(10L)
            emit(it)
        }
    }.map { it * it }
        .reduce { sum, element -> sum + element }.toString()
}
