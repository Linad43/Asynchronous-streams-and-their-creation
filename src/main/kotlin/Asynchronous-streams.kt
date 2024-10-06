package org.example

import kotlin.system.measureTimeMillis
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

const val SIZE_NUM = 12
val persons = arrayListOf<Person>()
var phoneNums = arrayListOf<String>()

suspend fun main() {
    val names = arrayOf("Иван", "Петр", "Дмитрий", "София")
    val roles = arrayOf("developer", "engineer", "doctor", "military")

    for (name in names) {
        persons.add(
            Person(
                name, roles.random()
            )
        )
    }
    var phoneNums = arrayListOf<String>()
    val time = measureTimeMillis {
        withContext(newSingleThreadContext("my_Thread")) {
            val personFlow = launch {
                getPersonFlow().collect { i ->
                    println("Get data: $i")
                }
            }
            val phoneFlow = launch {
                getPhoneFlow(persons.size).collect {
                    phoneNums.add(it)
                }
                //phoneNums.add(getPhoneFlow(persons.size).toString())

            }
            personFlow.join()
            phoneFlow.join()
        }
    }

    val personInfo = persons.zip(phoneNums)
    personInfo.forEach { println(it) }

    println("time: $time")
}

fun getPhoneFlow(size: Int) = flow {
    for (i in 1..size) {
        var num = "+7917"
        while (num.length < SIZE_NUM) {
            num += (0..9).random().toString()
        }
        delay(100L)
        println("Add numPhone: $num")
        phoneNums.add(num)
        emit(num)
    }
}

fun getPersonFlow() = persons.asFlow().onEach { delay(100L) }