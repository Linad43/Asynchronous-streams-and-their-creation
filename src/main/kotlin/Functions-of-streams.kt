package org.example

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis

val SELECT = ('0'..'9').toList() + ('a'..'z').toList()
const val SIZE_LIST_ID = 6

suspend fun main() {
    println("Кол-во пользователей: ")
    val countPerson: Int = readlnOrNull()?.toIntOrNull() ?: 100

    println("Пароли с символа: ")
    val charPassword: Char = readln().first()

    val listPassword = arrayListOf<String>()
    val listId = arrayListOf<String>()

    val time = measureTimeMillis {
        withContext(newSingleThreadContext("my_Thread")) {
            val passwordJob = launch {
                getPasswordFlow(
                    charPassword.toString(),
                    countPerson
                )
                    .collect {
                        println("Add password")
                        listPassword.add(it)
                    }
            }
            val idFlow = launch {
                getIdFlow(countPerson)
                    .collect {
                        println("Add user id[$it]")
                        listId.add(it)
                    }
            }
            passwordJob.join()
            idFlow.join()
        }

        val result = mutableMapOf<String, String>()
        for (i in listId.indices) {
            result[listId[i]] = listPassword[i]
        }
        result.forEach {
            println("${it.key} = ${it.value}")
        }
    }
    println("На создание $countPerson потребовалось $time мс")
}

fun createPassword(): String {
    val listChars = arrayListOf<String>()
    while (listChars.size < 6) {
        listChars.add(SELECT.random().toString())
        if (listChars.size % 2 == 0 && !isNum(listChars.last)) {
            val char = listChars.last
            listChars.remove(listChars.last)
            listChars.add(char.uppercase())
        }
    }
    var result = ""
    for (char in listChars) {
        result += char
    }
    return result
}

fun isNum(char: String): Boolean {
    try {
        char.toInt()
        return true
    } catch (e: Exception) {
        return false
    }
}

fun getListOfPassword(input: String, length: Int): List<String> {
    val result = mutableListOf<String>()
    while (result.size < length) {
        var password = createPassword()
        while (password[0].toString() != input) {
            password = createPassword()
        }
        result.add(password)
    }
    return result
}

fun getListId(length: Int): List<String> {
    val result = mutableListOf<String>()
    var buf = ""
    while (result.size < length) {
        buf = (result.size + 1).toString()
        for (i in 1..SIZE_LIST_ID - (result.size + 1).toString().length) {
            buf = "0$buf"
        }
        result.add(buf)
    }
    return result
}

fun getIdFlow(length: Int) = flow {
    val id = getListId(length)
    id.forEach {
        delay(100L)
        emit(it)
    }
}

fun getPasswordFlow(input: String, length: Int) = flow {
    val password = getListOfPassword(input, length)
    password.forEach {
        delay(100L)
        emit(it)
    }
}