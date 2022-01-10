package com.bignerdranch.nyethack

import kotlin.random.Random

open class Room(val name: String) {

    protected open val status = "Calm"

    open fun description() = "$name \n(Currently: $status)"

    open fun enterRoom() {
        narrate("There is nothing to do here")
    }
}

open class MonsterRoom(
    name: String,
    var monster: Monster? = Goblin()
) : Room(name) {
    init {
        generateMonster()
    }

    override fun description(): String = super.description() + " (Creature: ${monster?.description ?: "None"})"

    private fun generateMonster() {
        when (Random.nextInt(100)) {
            in 0..40 -> monster = Goblin()
            in 41..70 -> monster = Draugr()
            in 71..90 -> monster = Werewolf()
            in 91..100 -> monster = Dragon()
        }
    }

    override fun enterRoom() {
        if (monster == null) {
            super.enterRoom()
        } else {
            narrate("Danger is lurking in this room")
        }
    }
}