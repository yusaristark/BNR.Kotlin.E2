import java.io.File
import kotlin.random.Random
import kotlin.random.nextInt

private const val TAVERN_MASTER = "Taernyl"
private const val TAVERN_NAME = "$TAVERN_MASTER's Folly"

private val firstNames = setOf("Alex", "Mordoc", "Sophie", "Tariq")
private val lastNames = setOf("Ironfoot", "Fernsworth", "Baggins", "Downstrider")

private val menuData = File("data/tavern-menu-data.txt")
    .readText()
    .split("\n")

private val menuItems = List(menuData.size) { index ->
    val (_, name, _) = menuData[index].split(",")
    name
}

private val menuItemPrices: Map<String, Double> = List(menuData.size) { index ->
    val (_, name, price) = menuData[index].split(",")
    name to price.toDouble()
}.toMap()

private val menuItemTypes: Map<String, String> = List(menuData.size) { index ->
    val (type, name, _) = menuData[index].split(",")
    name to type
}.toMap()

fun visitTavern() {
    narrate("$heroName enters $TAVERN_NAME")
    narrate("There are several items for sale:")
    narrate(menuItems.joinToString())

    val patrons: MutableSet<String> = mutableSetOf()
    val patronGold = mutableMapOf(
        TAVERN_MASTER to 86.00,
        heroName to 4.50
    )
    while (patrons.size < 5) {
        val patronName = "${firstNames.random()} ${lastNames.random()}"
        patrons += patronName
        patronGold += patronName to 15.0
    }

    narrate("$heroName sees several patrons in the tavern:")
    narrate(patrons.joinToString())

    repeat(3) {
        placeOrder(patrons.random(), generateOrder(), patronGold)
    }

    displayPatronBalances(patronGold)
}

private fun generateOrder(): List<String> {
    val order = mutableListOf<String>()
    repeat(Random.nextInt(1..3)) {
        order.add(menuItems.random())
    }
    return order.toList()
}

private fun placeOrder(
    patronName: String,
    menuItemNames: List<String>,
    patronGold: MutableMap<String, Double>
) {
    narrate("$patronName speaks with $TAVERN_MASTER to place an order")
    val overallPrice = menuItemNames.sumOf { menuItemPrices.getValue(it) }
    if (overallPrice <= patronGold.getOrDefault(patronName, 0.0)) {
        menuItemNames.forEach { menuItemName ->
            val itemPrice = menuItemPrices.getValue(menuItemName)

            val action = when (menuItemTypes[menuItemName]) {
                "shandy", "elixir" -> "pours"
                "meal" -> "serves"
                else -> "hands"
            }
            narrate("$TAVERN_MASTER $action $patronName a $menuItemName")
            patronGold[patronName] = patronGold.getValue(patronName) - itemPrice
            patronGold[TAVERN_MASTER] = patronGold.getValue(TAVERN_MASTER) + itemPrice
        }
        narrate("$patronName pays $TAVERN_MASTER ${overallPrice.toString().format("%.2f")} gold")
    } else {
        narrate("$TAVERN_MASTER says, \"You need more coin for an order\"")
    }
}



private fun displayPatronBalances(patronGold: Map<String, Double>) {
    narrate("$heroName intuitively knows how much money each patron has")
    patronGold.forEach { (patron, balance) ->
        narrate("$patron has ${"%.2f".format(balance)} gold")
    }
}