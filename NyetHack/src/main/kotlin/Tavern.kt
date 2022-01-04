import java.io.File
import kotlin.math.max

private const val TAVERN_MASTER = "Taernyl"
private const val TAVERN_NAME = "$TAVERN_MASTER's Folly"

private val firstNames = setOf("Alex", "Mordoc", "Sophie", "Tariq")
private val lastNames = setOf("Ironfoot", "Fernsworth", "Baggins", "Downstrider")

private val menuData = File("data/tavern-menu-data.txt")
    .readText()
    .split("\n")

private data class MenuItem(
    val type: String,
    val name: String,
    val cost: Double
)

private val menuItems = List(menuData.size) { index ->
    val (type, name, cost) = menuData[index].split(",")
    MenuItem(type, name, cost.toDouble())
}

fun visitTavern() {
    narrate("$heroName enters $TAVERN_NAME")
    narrate("There are several items for sale:")
    narrate(menuItems.joinToString())

    val patrons: MutableSet<String> = mutableSetOf()
    while (patrons.size < 10) {
        patrons += "${firstNames.random()} ${lastNames.random()}"
    }

    narrate("$heroName sees several patrons in the tavern:")
    narrate(patrons.joinToString())

    repeat(3) {
        placeOrder(patrons.random(), menuItems.random().name)
    }

    println()
    printFormattedTavernMenu()
    println()
    printAdvancedFormattedTavernMenu()
}

private fun placeOrder(patronName: String, menuItemName: String) {
    narrate("$patronName speaks with $TAVERN_MASTER to place an order")
    narrate("$TAVERN_MASTER hands $patronName a $menuItemName")
}

private fun printFormattedTavernMenu() {
    val welcomeString = "*** Welcome to $TAVERN_MASTER's Folly ***"
    var maxStringLength = welcomeString.length
    menuItems.forEach {
        val stringLength = it.name.length + it.cost.toString().format("%.2d").length
        maxStringLength = max(stringLength, maxStringLength)
    }

    if (maxStringLength == welcomeString.length) {
        println("$welcomeString\n")
        menuItems.forEach {
            print(it.name)
            repeat(maxStringLength - it.name.length - it.cost.toString().format("%.2d").length) {
                print(".")
            }
            println(it.cost)
        }
    } else {
        val newWelcomeString = if (maxStringLength % 2 == 0) {
            val result: StringBuilder = StringBuilder(welcomeString)
            repeat((maxStringLength - welcomeString.length) / 2) {
                result.insert(0, "*")
                result.append("*")
            }
            result
        } else {
            val result: StringBuilder = StringBuilder(welcomeString)
            repeat((maxStringLength - welcomeString.length) / 2 + 1) {
                result.insert(0, "*")
                result.append("*")
            }
            result
        }
        maxStringLength = max(maxStringLength, newWelcomeString.length)
        println("$newWelcomeString\n")
        menuItems.forEach {
            print(it.name)
            repeat(maxStringLength - it.name.length - it.cost.toString().format("%.2d").length) {
                print(".")
            }
            println(it.cost)
        }
    }
}

private fun printAdvancedFormattedTavernMenu() {
    val welcomeString = "*** Welcome to $TAVERN_MASTER's Folly ***"
    var maxStringLength = welcomeString.length
    menuItems.forEach {
        val stringLength = it.name.length + it.cost.toString().format("%.2d").length
        maxStringLength = max(stringLength, maxStringLength)
    }
    val sortedList = menuItems.sortedBy {
        it.type
    }
    println("$welcomeString\n")
    var type = sortedList.first().type
    repeat(maxStringLength - type.length) {
        print(" ")
    }
    print("~[$type]~\n")
    sortedList.forEach {
        if (it.type != type) {
            type = it.type
            repeat((maxStringLength - type.length) / 2) {
                print(" ")
            }
            print("~[$type]~\n")
        }
        print(it.name)
        repeat(maxStringLength - it.name.length - it.cost.toString().format("%.2d").length) {
            print(".")
        }
        println(it.cost)
    }

}