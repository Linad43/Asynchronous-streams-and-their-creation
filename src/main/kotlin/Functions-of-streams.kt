package org.example

val SELECT = arrayListOf(("0".."9"), ("a".."z"))

fun main() {
    repeat(15) {
        println(createPassword())
    }
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
        char.toString()
        return false
    } catch (e: Exception) {
        return true
    }
}