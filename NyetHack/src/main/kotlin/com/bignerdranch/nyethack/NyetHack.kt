package com.bignerdranch.nyethack

lateinit var player: Player

fun main() {
    narrate("Welcome to NyetHack!")
    val playerName = promptHeroName()
    player = Player(playerName)
    // changeNarratorMood()
    player.prophesize()

    var currentRoom: Room = Tavern()
    val mortality = if (player.isImmortal) "an immortal" else "a mortal"
    narrate("${player.name} of ${player.homeTown}, ${player.title} is in ${currentRoom.description()}")
    narrate("${player.name}, $mortality, has ${player.healthPoints} health points")
    currentRoom.enterRoom()

    player.castFireball()
    player.prophesize()
}

private fun promptHeroName(): String {
    narrate("A hero enters the town of Kronstadt. What is their name?", ::makeYellow)
    println("Madrigal")
    return "Madrigal"
}

private fun makeYellow(message: String) = "\u001b[33;1m$message\u001b[0m"