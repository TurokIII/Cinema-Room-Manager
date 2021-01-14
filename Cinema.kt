package cinema

fun main() {
    println("Enter the number of rows:")
    val rows = readLine()!!.toInt()

    println("Enter the number of seats in each row:")
    val seats = readLine()!!.toInt()

    val income = calculateIncome(rows, seats)

    println("Total income:")
    println("$$income")




}

fun calculateIncome(rows: Int, seats: Int): Int {
    var income = 0
    val size = rows * seats

    income = when {
        size <= 60 -> size * 10
        else -> calculateLargeIncome(rows, seats)
    }

    return income
}

fun calculateLargeIncome(rows: Int, seats: Int): Int {
    val frontRows = rows / 2
    val backRows = rows - frontRows

    return frontRows * seats * 10 + backRows * seats * 8
}

fun printCinema() {
    val rows = 7
    val seatsPerRow = 8

    println("Cinema:")

    for (i in 0..seatsPerRow) {
        if (i == 0) print("  ") else print("$i ")
    }

    println()

    for (i in 1..rows) {
        var rowString = ""
        for (j in 0..seatsPerRow) {
            if (j == 0) rowString += "$i " else rowString += "S "
        }
        println("${rowString.trim()}")
    }
}