package pack4

import java.io.File
import java.io.FileWriter

fun main() {
    //ex1
    var file = File("./ex1.txt")
    writeFile(file, "что то написано\n", false)
    readFile(file)
    //ex2
    file = File("./ex2.txt")
    writeFile(file, numChet((1..100).random()), false)
    //ex3
    ex3()
}

fun writeFile(file: File, text: String, adding: Boolean) {
    val input = FileWriter(file, adding)
    input.write(text)
    input.close()
}

fun readFile(file: File): String {
    return file.readText()
}

fun numChet(count: Int): String {
    var numbers = ""
    for (i in 1..count) {
        if (numbers.length != 0) {
            numbers += ",${i * 2}"
        } else {
            numbers = "${i * 2}"
        }
    }
    return numbers
}

fun ex3() {
    val file = File("./ex3.txt")
    writeFile(
        file,
        numChet((4..100).random()),
        false
    )
    val buf = file.readText()
    println(buf[0].toString())
    println(buf[1].toString())
    println(buf[buf.lastIndex-1].toString())
    println(buf[buf.lastIndex].toString())
}