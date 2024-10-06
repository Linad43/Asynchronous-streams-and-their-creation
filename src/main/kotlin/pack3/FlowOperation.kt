package org.example.pack3

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

val names = arrayOf(
    "Иван",
    "Петр",
    "Дмитрий",
    "София",
    "Лера",
    "Тимофей",
    "Даниил",
    "Платон",
    "Константин",
    "Глеб",
    "Вероника",
    "Матвей",
    "Александр"
)

private fun listIntNum(length: Int, min: Int, max: Int): ArrayList<Int> {
    val result = arrayListOf<Int>()
    for (i in 1..length) {
        result.add((min..max).random())
    }
    return result
}

private fun listIntNum(length: Int): ArrayList<Int> {
    return listIntNum(length, 0, 10)
}

private fun listIntNum(): ArrayList<Int> {
    return listIntNum(10)
}

private class Person(
    val name: String,
    val age: Int
)
//
//private class PersonEx3(
//    val name: String,
//    val cart: String,
//    val password: String
//)

private fun numCart(): String {
    var result = ""
    for (i in 1..4) {
        for (j in 1..4) {
            result += ('0'..'9').random().toString()
        }
        if (i != 4) {
            result += " "
        }
    }
    return result
}

private fun randPassword(): String {
    var result = ""
    for (i in 1..4) {
        result += ('0'..'9').random().toString()
    }
    return result
}

suspend fun main() {

    ex1()

    ex2()

    ex3()


}

private suspend fun ex3() {
    val addNameFlow = addNameFlow()
    val addCartFlow = addCartFlow()
    val addPasswordFlow = addPasswordFlow()
    addNameFlow.zip(addCartFlow) { name, cart ->
        "name = $name, cart = $cart, "
    }.zip(addPasswordFlow) { nameZipCart, password ->
        "$nameZipCart password = $password"
    }.collect {
        println("Person ($it),")
    }
}

private fun addNameFlow() = flow {
    val namesIn = arrayListOf<String>()
    for (i in 1..10) {
        namesIn.add(names.random())
    }
    namesIn.forEach {
        emit(it)
    }
}

private fun addPasswordFlow() = flow {
    val passwords = arrayListOf<String>()
    for (i in 1..10) {
        passwords.add(randPassword())
    }
    passwords.forEach {
        emit(it)
    }
}

private fun addCartFlow() = flow {
    val carts = arrayListOf<String>()
    for (i in 1..10) {
        carts.add(numCart())
    }
    carts.forEach {
        emit(it)
    }
}

private suspend fun ex1() {
    println(flow {
        val number = listIntNum()
        println(number)
        number.forEach {
            delay(10L)
            emit(it)
        }
    }.map { it * it }
        .reduce { sum, element -> sum + element }.toString())
}

private suspend fun ex2() {
    val persons = arrayListOf<Person>()
    for (i in 1..10) {
        persons.add(Person(names.random(), (1..60).random()))
    }
    println()
    persons.forEach { println("${it.name}, ${it.age}") }
    println()

    println("Первая буква имени: ")
    val firstCharName: Char = readln().first()

    println("Введите возраст: ")
    val agePerson: Int = readlnOrNull()?.toIntOrNull() ?: 18

    getPerson(firstCharName.toString().uppercase(), agePerson, persons).collect {
        println(it.name + " " + it.age)
    }

}

private fun getPerson(first: String, age: Int, persons: ArrayList<Person>) = flow {
    persons.asFlow().filter { person -> person.age > age && person.name.first().toString().uppercase() == first }
        .collect {
            emit(it)
        }
}