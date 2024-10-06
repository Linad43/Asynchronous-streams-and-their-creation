package org.example
//
//val SELECT_CHAR = arrayListOf(('a'..'z').toMutableList().toString())
val SELECT = ('0'..'9').toList() + ('a'..'z').toList()


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
        char.toInt()
        return true
    } catch (e: Exception) {
        return false
    }
}

