import kotlin.random.Random
import kotlin.random.nextInt

var narrationModifier: (String) -> String = { it }

inline fun narrate(message: String, modifier: (String) -> String = { narrationModifier(it) }) {
    println(modifier(message))
}

fun changeNarratorMood() {
    val mood: String
    val modifier: (String) -> String

    when (Random.nextInt(1..7)) {
        1 -> {
            mood = "loud"
            modifier = { message ->
                val numExclamationPoints = 3
                message.uppercase() + "!".repeat(numExclamationPoints)
            }
        }
        2 -> {
            mood = "tired"
            modifier = { message ->
                message.lowercase().replace(" ", "... ")
            }
        }
        3 -> {
            mood = "unsure"
            modifier = { message ->
                "$message?"
            }
        }
        4 -> {
            var narrationsGiven = 0
            mood = "like sending an itemized bill"
            modifier = { message ->
                narrationsGiven++
                "$message.\n(I have narrated $narrationsGiven things)"
            }
        }
        5 -> {
            mood = "lazy"
            modifier = { message ->
                message.take(message.length / 2)
            }
        }
        6 -> {
            mood = "leet"
            modifier = { message ->
                message.replace(Regex("\\w")) { matchResult ->
                    when (matchResult.value) {
                        "o", "O" -> "0"
                        "l", "L" -> "1"
                        "e", "E" -> "3"
                        "t", "T" -> "7"
                        else -> matchResult.value
                    }
                }
            }
        }
        else -> {
            mood = "professional"
            modifier = { message ->
                "$message."
            }
        }
    }

    narrationModifier = modifier
    narrate("The narrator begins to feel $mood")
}