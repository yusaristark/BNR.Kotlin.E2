package com.bignerdranch.nyethack

import kotlin.random.Random

interface Fightable {
    val name: String
    var healthPoints: Int
    val diceCount: Int
    val diceSides: Int

    fun takeDamage(damage: Int)

    fun attack(opponent: Fightable) {
        val damageRoll = (0 until diceCount).sumOf {
            Random.nextInt(diceSides + 1)
        }
        narrate("$name deals $damageRoll to ${opponent.name}")
        opponent.takeDamage(damageRoll)
    }
}

abstract class Monster(
    override val name: String,
    val description: String,
    override var healthPoints: Int
) : Fightable {
    override fun takeDamage(damage: Int) {
        healthPoints -= damage
    }
}

class Goblin(
    name: String = "Goblin",
    description: String = "A nasty-looking goblin",
    healthPoints: Int = 30
) : Monster(name, description, healthPoints) {
    override val diceCount = 2
    override val diceSides = 8
}

class Draugr(
    name: String = "Draugr",
    description: String = "A scary draugr",
    healthPoints: Int = 50
) : Monster(name, description, healthPoints) {
    override val diceCount = 3
    override val diceSides = 10
}

class Werewolf(
    name: String = "Werewolf",
    description: String = "A sharp-toothed werewolf",
    healthPoints: Int = 75
) : Monster(name, description, healthPoints) {
    override val diceCount = 4
    override val diceSides = 12
}

class Dragon(
    name: String = "Dragon",
    description: String = "A dangerous red dragon",
    healthPoints: Int = 200
) : Monster(name, description, healthPoints) {
    override val diceCount = 7
    override val diceSides = 20
}