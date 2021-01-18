package cinema

import kotlin.math.roundToInt

fun main() {
    var continueRunning = true
    val (rows, seats) = getCinemaSize()
    val cinema = makeCinema(rows, seats)

    while(continueRunning) {
        printMenu()
        println()
        val choice = getMenuChoice()
        println()
        continueRunning = executeChoice(choice, cinema)
    }

}

fun getCinemaSize(): Pair<Int, Int> {
    println("Enter the number of rows:")
    val rows = readLine()!!.toInt()

    println("Enter the number of seats in each row:")
    val seats = readLine()!!.toInt()

    println()
    return Pair(rows, seats)
}

fun makeCinema(rows: Int, seats: Int): Array<Array<String>> {
    val cinema = Array(rows) {Array(seats) {""} }

    for (i in 0 until rows) {
        for (j in 0 until seats) {
            cinema[i][j] = "S"
        }
    }

    return cinema
}

fun printMenu() {
    println("1. Show the seats")
    println("2. Buy a ticket")
    println("3. Statistics")
    println("0. Exit")
}

fun getMenuChoice(): String  {
    while (true) {
        val choice = readLine()!!
        if (choice != "1" && choice != "2" && choice != "0" && choice != "3") {
            println("Please enter a valid choice!")
        } else {
            return choice
        }
    }
}

fun executeChoice(choice: String, cinema: Array<Array<String>>): Boolean {
    when (choice) {
        "1" -> printCinema(cinema)
        "2" -> buyTicket(cinema)
        "3" -> printStatistics(cinema)
        "0" -> return false
    }

    return true
}

fun buyTicket(cinema: Array<Array<String>>) {
    while (true) {
        println("Enter a row number:")
        val rowNum = readLine()!!.toInt() - 1
        println("Enter a seat number in that row:")
        val seatNum = readLine()!!.toInt() - 1

        if (rowNum !in cinema.indices || seatNum !in cinema[0].indices) {
            println("Wrong input!")
            println()
            continue
        }
        val seat = cinema[rowNum][seatNum]

        if (seat == "B") {
            println("That ticket has already been purchased!")
            println()
        } else {

            cinema[rowNum][seatNum] = "B"

            val ticketPrice = getTicketPrice(cinema, rowNum)

            println("Ticket price: $$ticketPrice")
            println()
            break
        }
    }
}

fun getTicketPrice(cinema: Array<Array<String>>, rowNum: Int): Int {
    if (cinema.size * cinema[0].size <= 60) return 10

    return if (cinema.size / 2 >= rowNum + 1) 10 else 8
}

fun printCinema(cinema: Array<Array<String>>) {
    println("Cinema:")

    for (i in 0..cinema[0].size) {
        if (i == 0) print("  ") else print("$i ")
    }

    println()

    for (i in cinema.indices) {
        var rowString = ""

        for (j in 0..cinema[i].size) {
            rowString += if (j == 0) "${i+1} " else cinema[i][j-1] + " "
        }
        println(rowString.trim())
    }

    println()
}

fun printStatistics(cinema: Array<Array<String>>) {
    val ticketsSold = getSoldTicketCount(cinema)
    val soldTicketPercentage = getSoldTicketPercentage(cinema)
    val ticketIncome = getSoldTicketIncome(cinema)
    val totalTicketIncome = getTotalTicketIncome(cinema)

    println("Number of purchased tickets: $ticketsSold")
    println("Percentage: $soldTicketPercentage%")
    println("Current income: $$ticketIncome")
    println("Total income: $$totalTicketIncome")
    println()
}

fun getSoldTicketCount(cinema: Array<Array<String>>): Int {
    var ticketsSold = 0
    for (row in cinema) {
        for (seat in row) {
            if (seat == "B") ticketsSold++
        }
    }
    return ticketsSold
}

fun getSoldTicketPercentage(cinema: Array<Array<String>>): String {
    var ticketsSold = 0
    var totalTickets = 0

    for (row in cinema) {
        for (seat in row) {
            if (seat == "B") ticketsSold++
            totalTickets++
        }
    }
    val percentage = 1.0 * ticketsSold / totalTickets
    val roundedPercentage = (percentage * 10000).roundToInt() / 100.0

    return "%.2f".format(roundedPercentage)
}

fun getSoldTicketIncome(cinema: Array<Array<String>>): Int {
    var ticketIncome = 0

    for (rowNum in cinema.indices) {
        for (seat in cinema[rowNum]) {
            if (seat == "B") ticketIncome += getTicketPrice(cinema, rowNum)
        }
    }

    return ticketIncome
}

fun getTotalTicketIncome(cinema: Array<Array<String>>): Int {
    var ticketIncome = 0

    for (rowNum in cinema.indices) {
        for (seat in cinema[rowNum]) {
            ticketIncome += getTicketPrice(cinema, rowNum)
        }
    }

    return ticketIncome
}