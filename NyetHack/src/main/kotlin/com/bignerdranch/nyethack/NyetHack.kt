package com.bignerdranch.nyethack

lateinit var player: Player

fun main() {
    narrate("Welcome to NyetHack!")
    val playerName = promptHeroName()
    player = Player(playerName)
    // changeNarratorMood()

    Game.play()
}

private fun promptHeroName(): String {
    narrate("A hero enters the town of Kronstadt. What is their name?", ::makeYellow)
    println("Madrigal")
    return "Madrigal"
}

private fun makeYellow(message: String) = "\u001b[33;1m$message\u001b[0m"

object Game {
    private var isPlaying = true

    private val worldMap = listOf(
        listOf(TownSquare(), Tavern(), Room("Back Room")),
        listOf(Room("A Long Corridor"), Room("A Generic Room")),
        listOf(Room("The Dungeon"))
    )

    private var currentRoom: Room = worldMap[0][0]
    private var currentPosition = Coordinate(0, 0)

    init {
        narrate("Welcome, adventurer")
        val mortality = if (player.isImmortal) "an immortal" else "a mortal"
        narrate("${player.name}, $mortality, has ${player.healthPoints} health points")
    }

    fun play() {
        while (isPlaying) {
            narrate("${player.name} of ${player.homeTown}, ${player.title}, is in ${currentRoom.description()}")
            currentRoom.enterRoom()

            print("> Enter your command: ")
            GameInput(readLine()).processCommand()
        }
    }

    fun move(direction: Direction) {
        val newPosition = direction.updateCoordinate(currentPosition)
        val newRoom = worldMap.getOrNull(newPosition.y)?.getOrNull(newPosition.x)

        if (newRoom != null) {
            narrate("The hero moves ${direction.name}")
            currentPosition = newPosition
            currentRoom = newRoom
        } else {
            narrate("You cannot move ${direction.name}")
        }
    }

    private fun stopGame() {
        isPlaying = false
    }

    private class GameInput(arg: String?) {
        private val input = arg ?: ""
        val command = input.split(" ")[0]
        val argument = input.split(" ").getOrElse(1) { "" }

        fun processCommand() = when (command.lowercase()) {
            "move" -> {
                val direction = Direction.values().firstOrNull { it.name.equals(argument, true) }
                if (direction != null) {
                    move(direction)
                } else {
                    narrate("I don't know what direction that is")
                }
            }
            "cast" -> {
                if (argument == "fireball") {
                    player.castFireball()
                } else {
                    narrate("I don't know what you are trying to cast")
                }
            }
            "prophesize" -> {
                player.prophesize()
            }
            "quit" -> {
                narrate("Farewell, adventurer")
                stopGame()
            }
            "map" -> {
                worldMap.forEachIndexed { y, rooms ->
                    rooms.forEachIndexed { x, _ ->
                        if (currentPosition == Coordinate(x, y)) {
                            print("X ")
                        } else {
                            print("O ")
                        }
                    }
                    println()
                }
            }
            "ring" -> {
                if (currentRoom is TownSquare) {
                    (currentRoom as TownSquare).ringBell(argument.toIntOrNull() ?: 1)
                } else {
                    narrate("You are not on town square")
                }
            }
            else -> narrate("I'm not sure what you're trying to do")
        }
    }
}